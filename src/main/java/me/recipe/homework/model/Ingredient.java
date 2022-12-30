package me.recipe.homework.model;

import jdk.jfr.DataAmount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.swing.*;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ingredient {
    private String name;
    private int quantityIngredient;
    private String unit;
}
