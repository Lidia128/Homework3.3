package me.recipe.homework.impl;

import me.recipe.homework.model.Recipe;
import me.recipe.homework.service.RecipeService;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

public class RecipeServiceImpl implements RecipeService {
    private static Map<Integer, Recipe> recipes = new TreeMap<>();
    private static int id = 0;

    @Override
    public Collection<Recipe> getAll() {
        return recipes.values();
    }

    @Override
    public Recipe addNewRecipe(Recipe recipe) {
        if (recipes.containsKey(id)) {
            throw new RuntimeException("Не может добавить рецепт с таким же id");
        } else {
            recipes.put(id++, recipe);
        }
        return recipe;
    }

    @Override
    public Recipe getRecipeById(int id) {
        if (recipes.containsKey(id)) {
            return recipes.get(id);
        } else {
            throw new RuntimeException("Нет такого рецепта");
        }
    }
    @Override
    public Recipe editRecipe(long id, Recipe recipe) {
        if (recipes.containsKey(id)) {
            recipes.put((int) id, recipe);
            return recipe;
        }
        return null;
        }
    @Override
    public boolean deleteRecipe(long id){
        if (recipes.containsKey(id)) {
            recipes.remove(id);
            return true;
        }
        return false;
    }
    @Override
    public void deleteAllRecipe(){
      recipes = new TreeMap<>();
        }
}
