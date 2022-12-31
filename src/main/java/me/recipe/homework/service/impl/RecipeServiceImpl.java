package me.recipe.homework.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.recipe.homework.model.Recipe;
import me.recipe.homework.service.FilesRecipeService;
import me.recipe.homework.service.RecipeService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
@Service
public class RecipeServiceImpl implements RecipeService {
    final private FilesRecipeService filesRecipeService;
    private static Map<Integer, Recipe> recipes = new TreeMap<>();
    private static int id = 0;
    public RecipeServiceImpl(FilesRecipeService filesRecipeService) {
        this.filesRecipeService = filesRecipeService;
    }
    @PostConstruct
    private void init (){
        readFromFile();
    }

    public Collection<Recipe> getAll() {
        return recipes.values();
    }
    public Recipe addNewRecipe(Recipe recipe) {
        if (recipes.containsKey(id)) {
            throw new RuntimeException("Не может добавить рецепт с таким же id");
        } else {
            recipes.put(id++, recipe);
        }
        saveToFile();
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
            saveToFile();
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
    private void saveToFile (){
        try {
            String json = new ObjectMapper().writeValueAsString(recipes);
            filesRecipeService.saveToFile(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    private void readFromFile(){
        String json = filesRecipeService.readFromFile();
        try {
           recipes = new ObjectMapper().readValue(json, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
