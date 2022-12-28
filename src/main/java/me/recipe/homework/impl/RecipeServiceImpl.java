package me.recipe.homework.impl;

import me.recipe.homework.model.Recipe;
import me.recipe.homework.service.RecipeService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
@Service
public class RecipeServiceImpl implements RecipeService {
    private static Map<Integer, Recipe> recipes = new TreeMap<>();
    private static int id = 0;
    public Collection<Recipe> getAll() {
        return recipes.values();
    }
    public Recipe addNewRecipe(Recipe recipe) {
        if (recipes.containsKey(id)) {
            throw new RuntimeException("Не может добавить рецепт с таким же id");
        } else {
            recipes.put(id++, recipe);
        }
        return recipe;
    }
    public Recipe getRecipeById(int id) {
        if (recipes.containsKey(id)) {
            return recipes.get(id);
        } else {
            throw new RuntimeException("Нет такого рецепта");
        }
    }
    public Recipe editRecipe(long id, Recipe recipe) {
        if (recipes.containsKey(id)) {
            recipes.put((int) id, recipe);
            return recipe;
        }
        return null;
        }
    public boolean deleteRecipe(long id){
        if (recipes.containsKey(id)) {
            recipes.remove(id);
            return true;
        }
        return false;
    }
    public void deleteAllRecipe(){
      recipes = new TreeMap<>();
        }
}
