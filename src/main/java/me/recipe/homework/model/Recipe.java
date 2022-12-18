package me.recipe.homework.model;

import javax.swing.*;

public class Recipe {
    private String id;
    private final String name;
    private final int time;
    private String [] ingredientLine;
    private String [] step;

    public Recipe(String id, String name, int time, String[] ingredientLine, String[] step) {
        this.id = id;
        this.name = name;
        this.time = time;
        this.ingredientLine = ingredientLine;
        this.step = step;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getTime() {
        return time;
    }

    public String[] getIngredientLine() {
        return ingredientLine;
    }

    public void setIngredientLine(String[] ingredientLine) {
        this.ingredientLine = ingredientLine;
    }

    public String[] getStep() {
        return step;
    }

    public void setStep(String[] step) {
        this.step = step;
    }
}
