package com.polaris.polaris_digitech.controller;

import com.polaris.polaris_digitech.dto.AppResponse;
import com.polaris.polaris_digitech.dto.BoxDto;
import com.polaris.polaris_digitech.dto.ItemDto;
import com.polaris.polaris_digitech.service.BoxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/boxes")
public class BoxController {

    @Autowired
    private BoxService boxService;

    @PostMapping
    public ResponseEntity<AppResponse<BoxDto>> createBox(@RequestBody BoxDto box) {
        BoxDto createdBox = boxService.createBox(box);
        String message = "Box created successfully" ;
        return ResponseEntity.ok(AppResponse.success(message, createdBox));
    }

    @PostMapping("/{txref}/load")
    public ResponseEntity<BoxDto> loadBox(@PathVariable String txref, @RequestBody List<ItemDto> items) {
        BoxDto loadedBox = boxService.loadBox(txref, items);
        return ResponseEntity.ok(loadedBox);
    }

    @GetMapping("/{txref}/items")
    public ResponseEntity<List<ItemDto>> getBoxItems(@PathVariable String txref) {
        List<ItemDto> items = boxService.getBoxItems(txref);
        return ResponseEntity.ok(items);
    }

    @GetMapping("/available")
    public ResponseEntity<List<BoxDto>> getAvailableBoxes() {
        List<BoxDto> boxes = boxService.getAvailableBoxes();
        return ResponseEntity.ok(boxes);
    }

    @GetMapping("/{txref}/battery")
    public ResponseEntity<Integer> getBatteryLevel(@PathVariable String txref) {
        Integer batteryLevel = boxService.getBatteryLevel(txref);
        return ResponseEntity.ok(batteryLevel);
    }
}