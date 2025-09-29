package com.polaris.polaris_digitech.model;

import jakarta.persistence.*;

@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private Integer weight;
    
    @Column(nullable = false)
    private String code;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "box_id")
    private Box box;

    public Item() {}

    public Item(String name, Integer weight, String code) {
        this.name = name;
        this.weight = weight;
        this.code = code;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Integer getWeight() { return weight; }
    public void setWeight(Integer weight) { this.weight = weight; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public Box getBox() { return box; }
    public void setBox(Box box) { this.box = box; }
}