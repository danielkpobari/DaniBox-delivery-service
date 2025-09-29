package com.polaris.polaris_digitech.repository;

import com.polaris.polaris_digitech.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}