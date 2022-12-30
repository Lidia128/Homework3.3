package me.recipe.homework.impl;

import me.recipe.homework.model.Ingredient;
import me.recipe.homework.model.Recipe;
import me.recipe.homework.service.IngredientService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
@Service
public class IngredientServiceImpl implements IngredientService {
    private static Map<Integer, Ingredient> ingredients = new TreeMap<>();
    private static int id = 0;
    public Collection<Ingredient> getAll() {
        return ingredients.values();
    }
    public Ingredient addNewIngredient(Ingredient ingredient) {
        if (ingredients.containsKey(id)) {
            throw new RuntimeException("Не может добавить ингредиент с таким же id");
        } else {
            ingredients.put(id++, ingredient);
        }
        return ingredient;
    }
    public Ingredient getIngredientById(int id) {
        if (ingredients.containsKey(id)) {
            return ingredients.get(id);
        } else {
            throw new RuntimeException("Нет такого ингредиента");
        }
    }
    public Ingredient editIngredient(long id, Ingredient ingredient) {
        if (ingredients.containsKey(id)) {
            ingredients.put((int) id, ingredient);
            return ingredient;
        }
        return null;
    }
     public boolean deleteIngredient(long id){
        if (ingredients.containsKey(id)) {
            ingredients.remove(id);
            return true;
        }
        return false;
    }
     public void deleteAllIngredient(){
        ingredients = new TreeMap<>();
    }
}
