package com.polaris.polaris_digitech.service;

import com.polaris.polaris_digitech.dto.BoxDto;
import com.polaris.polaris_digitech.dto.ItemDto;
import com.polaris.polaris_digitech.exception.BoxNotFoundException;
import com.polaris.polaris_digitech.exception.InvalidBoxStateException;
import com.polaris.polaris_digitech.exception.WeightLimitExceededException;
import com.polaris.polaris_digitech.model.Box;
import com.polaris.polaris_digitech.model.BoxState;
import com.polaris.polaris_digitech.model.Item;
import com.polaris.polaris_digitech.repository.BoxRepository;
import com.polaris.polaris_digitech.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class BoxService {
    
    @Autowired
    private BoxRepository boxRepository;
    
    @Autowired
    private ItemRepository itemRepository;
    
    private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Z0-9_-]+$");
    private static final Pattern CODE_PATTERN = Pattern.compile("^[A-Z0-9_]+$");

    public BoxDto createBox(BoxDto boxDto) {
        if (boxDto.getWeightLimit() > 500) {
            throw new WeightLimitExceededException("Weight limit cannot exceed 500g");
        }
        Box box = new Box(boxDto.getTxref(), boxDto.getWeightLimit(), boxDto.getBatteryCapacity());
        Box saved = boxRepository.save(box);
        return toBoxDto(saved);
    }

    public BoxDto loadBox(String txref, List<ItemDto> itemDtos) {
        Box box = boxRepository.findByTxref(txref)
            .orElseThrow(() -> new BoxNotFoundException("Box not found: " + txref));
        
        if (box.getBatteryCapacity() < 25) {
            throw new InvalidBoxStateException("Cannot load box with battery below 25%");
        }
        
        List<Item> items = itemDtos.stream().map(this::toItem).collect(Collectors.toList());
        for (Item item : items) {
            validateItem(item);
        }
        
        int totalWeight = box.getCurrentWeight() + items.stream().mapToInt(Item::getWeight).sum();
        if (totalWeight > box.getWeightLimit()) {
            throw new WeightLimitExceededException("Total weight exceeds box limit");
        }
        
        box.setState(BoxState.LOADING);
        items.forEach(item -> {
            item.setBox(box);
            box.getItems().add(item);
        });
        
        box.setState(BoxState.LOADED);
        return toBoxDto(boxRepository.save(box));
    }

    public List<ItemDto> getBoxItems(String txref) {
        Box box = boxRepository.findByTxref(txref)
            .orElseThrow(() -> new BoxNotFoundException("Box not found: " + txref));
        return box.getItems().stream().map(this::toItemDto).collect(Collectors.toList());
    }

    public List<BoxDto> getAvailableBoxes() {
        return boxRepository.findByStateAndBatteryCapacityGreaterThanEqual(BoxState.IDLE, 25)
            .stream().map(this::toBoxDto).collect(Collectors.toList());
    }

    public Integer getBatteryLevel(String txref) {
        Box box = boxRepository.findByTxref(txref)
            .orElseThrow(() -> new BoxNotFoundException("Box not found: " + txref));
        return box.getBatteryCapacity();
    }

    private void validateItem(Item item) {
        if (!NAME_PATTERN.matcher(item.getName()).matches()) {
            throw new IllegalArgumentException("Item name can only contain letters, numbers, hyphen, and underscore");
        }
        if (!CODE_PATTERN.matcher(item.getCode()).matches()) {
            throw new IllegalArgumentException("Item code can only contain uppercase letters, numbers, and underscore");
        }
    }

    private BoxDto toBoxDto(Box box) {
        return new BoxDto(box.getTxref(), box.getWeightLimit(), box.getBatteryCapacity(), box.getState());
    }

    private ItemDto toItemDto(Item item) {
        return new ItemDto(item.getName(), item.getWeight(), item.getCode());
    }

    private Item toItem(ItemDto dto) {
        return new Item(dto.getName(), dto.getWeight(), dto.getCode());
    }
}