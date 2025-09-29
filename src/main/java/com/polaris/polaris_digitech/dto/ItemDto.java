package com.polaris.polaris_digitech.dto;

public class ItemDto {
    private String name;
    private Integer weight;
    private String code;

    public ItemDto() {}

    public ItemDto(String name, Integer weight, String code) {
        this.name = name;
        this.weight = weight;
        this.code = code;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Integer getWeight() { return weight; }
    public void setWeight(Integer weight) { this.weight = weight; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
}