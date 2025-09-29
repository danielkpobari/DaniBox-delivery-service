package com.polaris.polaris_digitech.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Box {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(length = 20, unique = true, nullable = false)
    private String txref;
    
    @Column(nullable = false)
    private Integer weightLimit;
    
    @Column(nullable = false)
    private Integer batteryCapacity;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BoxState state = BoxState.IDLE;
    
    @OneToMany(mappedBy = "box", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Item> items = new ArrayList<>();

    public Box() {}

    public Box(String txref, Integer weightLimit, Integer batteryCapacity) {
        this.txref = txref;
        this.weightLimit = weightLimit;
        this.batteryCapacity = batteryCapacity;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTxref() { return txref; }
    public void setTxref(String txref) { this.txref = txref; }

    public Integer getWeightLimit() { return weightLimit; }
    public void setWeightLimit(Integer weightLimit) { this.weightLimit = weightLimit; }

    public Integer getBatteryCapacity() { return batteryCapacity; }
    public void setBatteryCapacity(Integer batteryCapacity) { this.batteryCapacity = batteryCapacity; }

    public BoxState getState() { return state; }
    public void setState(BoxState state) { this.state = state; }

    public List<Item> getItems() { return items; }
    public void setItems(List<Item> items) { this.items = items; }

    public int getCurrentWeight() {
        return items.stream().mapToInt(Item::getWeight).sum();
    }
}