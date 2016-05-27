package com.prodyna.pac.persistence.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Option {

    @Id
    private String id;
    private String value;
    private long counter;

    // necessary for automatic mapping
    public Option() {
        super();
    }

    public Option(String value, long counter) {
        this.value = value;
        this.counter = counter;
    }

    public Option(String id, String value, long counter) {
        this.id = id;
        this.value = value;
        this.counter = counter;
    }

    public String getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    public long getCounter() {
        return counter;
    }

    @Override
    public String toString() {
        return "Option [id=" + id + ", value=" + value + ", counter=" + counter + "]";
    }

}
