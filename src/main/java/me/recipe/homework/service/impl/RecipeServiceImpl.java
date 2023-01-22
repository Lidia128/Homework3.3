package me.recipe.homework.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.recipe.homework.model.Recipe;
import me.recipe.homework.service.RecipeService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

import static me.recipe.homework.service.impl.FilesRecipeServiceImpl.recipes;

@Service
public class RecipeServiceImpl implements RecipeService {
    @PostConstruct
    private void init() {
        readFromFile();
    }

    public Collection<Recipe> getAll() {
        return recipes.values();
    }


    public Recipe addRecipe(Recipe recipe) {
        return recipe;
    }

    @Override
    public Recipe getRecipe(int id) {
        return id;
    }

    public Recipe addNewRecipe(Recipe recipe) {
        int id = 0;
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

    @Override
    public void createRicepe(Recipe recipe) throws IOException {
        return path;
    }

    public Recipe editRecipe(long id, Recipe recipe) {
        if (recipes.containsKey(id)) {
            recipes.put((int) id, recipe);
            saveToFile();
        }
        return recipe;
    }

    @Override
    public Recipe updateRecipe(int id, Recipe recipe) {
        return null;
    }

    @Override
    public Recipe removeRecipe(int id) {
        return null;
    }

    public boolean deleteRecipe(long id) {
        if (recipes.containsKey(id)) {
            recipes.remove(id);
            return true;
        }
        return false;
    }

    public void deleteAllRecipe() {
        recipes = new TreeMap<>();
    }

    @Override
    public Path createRecipesFile() {
        return (Path) recipes;
    }

    @Override
    public byte[] exportTxt() {
        return new byte[0];
    }

    private void saveToFile() {
        try {
            String json = new ObjectMapper().writeValueAsString(recipes);
            RecipeService.saveToFile(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    private void readFromFile() {
        String json = RecipeService.readFromFile();
        try {
            recipes = new ObjectMapper().readValue(json, new TypeReference<TreeMap<Integer, Recipe>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
