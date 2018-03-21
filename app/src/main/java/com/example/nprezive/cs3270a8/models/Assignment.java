package com.example.nprezive.cs3270a8.models;


/**
 * Created by nprezive on 3/19/18.
 */

public class Assignment {
    String id;
    String name;
    String description;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Assignment{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
