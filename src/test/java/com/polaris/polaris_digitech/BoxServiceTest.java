package com.polaris.polaris_digitech;

import com.polaris.polaris_digitech.dto.BoxDto;
import com.polaris.polaris_digitech.dto.ItemDto;
import com.polaris.polaris_digitech.exception.WeightLimitExceededException;
import com.polaris.polaris_digitech.exception.InvalidBoxStateException;
import com.polaris.polaris_digitech.model.BoxState;
import com.polaris.polaris_digitech.service.BoxService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class BoxServiceTest {

    @Autowired
    private BoxService boxService;

    @Test
    public void testCreateBox() {
        BoxDto box = new BoxDto("TEST001", 300, 80, BoxState.IDLE);
        BoxDto created = boxService.createBox(box);
        assertEquals("TEST001", created.getTxref());
        assertEquals(300, created.getWeightLimit());
    }

    @Test
    public void testCreateBoxExceedsWeightLimit() {
        BoxDto box = new BoxDto("TEST002", 600, 80, BoxState.IDLE);
        assertThrows(WeightLimitExceededException.class, () -> boxService.createBox(box));
    }

    @Test
    public void testLoadBoxLowBattery() {
        BoxDto box = new BoxDto("TEST003", 300, 20, BoxState.IDLE);
        boxService.createBox(box);
        
        ItemDto item = new ItemDto("test-item", 100, "TEST_ITEM");
        assertThrows(InvalidBoxStateException.class, () -> 
            boxService.loadBox("TEST003", List.of(item)));
    }

    @Test
    public void testGetAvailableBoxes() {
        List<BoxDto> available = boxService.getAvailableBoxes();
        assertTrue(available.stream().allMatch(box -> 
            box.getState() == BoxState.IDLE && box.getBatteryCapacity() >= 25));
    }
}