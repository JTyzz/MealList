package com.earthdefensesystem.meallist.models;

import java.util.List;

public class Dish {
    private int id;
    private String name;
    private String dateItemAdded;
    private String ingredients;

    public Dish(){

    }

    public Dish(int id, String name, String dateItemAdded, String ingredients) {
        this.id = id;
        this.name = name;
        this.dateItemAdded = dateItemAdded;
        this.ingredients = ingredients;
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

    public String getDateItemAdded() {
        return dateItemAdded;
    }

    public void setDateItemAdded(String dateItemAdded) {
        this.dateItemAdded = dateItemAdded;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }
}
