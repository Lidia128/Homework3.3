package me.recipe.homework.impl;

import me.recipe.homework.model.Ingredient;
import me.recipe.homework.service.IngredientService;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

public class IngredientServiceImpl implements IngredientService {
    private static Map<Integer, Ingredient> ingredients = new TreeMap<>();
    private static int id = 0;
    @Override
    public Collection<Ingredient> getAll() {
        return ingredients.values();
    }
    @Override
    public Ingredient addNewIngredient(Ingredient ingredient) {
        if (ingredients.containsKey(id)) {
            throw new RuntimeException("Не может добавить ингредиент с таким же id");
        } else {
            ingredients.put(id++, ingredient);
        }
        return ingredient;
    }
    @Override
    public Ingredient getIngredientById(int id) {
        if (ingredients.containsKey(id)) {
            return ingredients.get(id);
        } else {
            throw new RuntimeException("Нет такого ингредиента");
        }
    }
}
