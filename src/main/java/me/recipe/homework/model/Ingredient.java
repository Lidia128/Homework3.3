package me.recipe.homework.model;

import javax.swing.*;

public class Ingredient {
    private String id;
    private String name;
    private int quantityIngredient;
    private String unit;

    public Ingredient(String id, String name, int quantityIngredient, String unit) {
        this.id = id;
        this.name = name;
        this.quantityIngredient = quantityIngredient;
        this.unit = unit;
    }

    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantityIngredient() {
        return quantityIngredient;
    }

    public void setQuantityIngredient(int quantityIngredient) {
        this.quantityIngredient = quantityIngredient;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
