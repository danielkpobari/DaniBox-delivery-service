package com.polaris.polaris_digitech.repository;

import com.polaris.polaris_digitech.model.Box;
import com.polaris.polaris_digitech.model.BoxState;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface BoxRepository extends JpaRepository<Box, Long> {
    Optional<Box> findByTxref(String txref);
    List<Box> findByStateAndBatteryCapacityGreaterThanEqual(BoxState state, Integer batteryCapacity);
}