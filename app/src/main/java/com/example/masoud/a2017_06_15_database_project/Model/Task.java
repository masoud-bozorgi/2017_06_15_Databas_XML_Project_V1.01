package com.example.masoud.a2017_06_15_database_project.Model;

/**
 * Created by seyedamirhosseinhashemi on 2017-06-25.
 */

public class Task {

    private int id;
    private String name;

    public Task(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
