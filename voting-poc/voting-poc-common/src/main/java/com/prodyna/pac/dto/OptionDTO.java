package com.prodyna.pac.dto;

/**
 * System wide DTO for an option of a vote.
 */
public class OptionDTO {

    private String id;
    private String name;
    private long counter;

    // explicit default constructor for JSON mapping
    public OptionDTO() {
        super();
    }

    public OptionDTO(String id, String name, long counter) {
        this.id = id;
        this.name = name;
        this.counter = counter;
    }

    public String getName() {
        return name;
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
        return "OptionDTO [id=" + id + ", name=" + name + ", counter=" + counter + "]";
    }

}
