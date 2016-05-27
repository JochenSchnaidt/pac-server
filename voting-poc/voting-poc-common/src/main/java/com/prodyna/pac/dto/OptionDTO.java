package com.prodyna.pac.dto;

public class OptionDTO {

    private String id;
    private String value;
    private long counter;

    // explicit default constructor for JSON mapping
    public OptionDTO() {
        super();
    }

    public OptionDTO(String id, String value, long counter) {
        this.id = id;
        this.value = value;
        this.counter = counter;
    }

    public String getValue() {
        return value;
    }

    public long getCounter() {
        return counter;
    }

    public void incrementCounter() {
        ++counter;
    }

    public void decrementCounter() {
        --counter;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "OptionDTO [id=" + id + ", value=" + value + ", counter=" + counter + "]";
    }

}
