package me.recipe.homework.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.recipe.homework.model.Ingredient;
import me.recipe.homework.model.Recipe;
import me.recipe.homework.service.FilesRecipeService;
import me.recipe.homework.service.RecipeService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;


@SuppressWarnings("ALL")
@Service
public class RecipeServiceImpl implements RecipeService {

    private final FilesRecipeService filesRecipeService;
    private static int id = 0;
    private Map<Integer, Recipe> recipes;
    private final Path pathToTextTemplate;

    public RecipeServiceImpl(FilesRecipeService filesRecipeService, Path pathToTextTemplate) {
        this.filesRecipeService = filesRecipeService;
        this.pathToTextTemplate = pathToTextTemplate;
    }
    @PostConstruct
    private void init() {
//        readFromFile();
        recipes = new TreeMap<>();
    }

    public Collection<Recipe> getAll() {
        return recipes.values();
    }

    public Recipe addRecipe(Recipe recipe) {
        if (recipes.containsValue(recipe)) {
            throw new RuntimeException("Не может добавить рецепт, уже есть");
        }
        recipes.put(++id, recipe);
        return recipe;
    }

    @Override
    public Recipe getRecipe(Integer id) {
        Recipe recipe = recipes.get(id);
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
        }
        return recipe;
    }

    @Override
    public Recipe updateRecipe(int id, Recipe recipe){
        Recipe serviceRecipe = recipes.get(id);
        serviceRecipe.setName(recipe.getName());
        serviceRecipe.setTime(recipe.getTime());
        serviceRecipe.setIngredients(recipe.getIngredients());
        serviceRecipe.setSteps(recipe.getSteps());
        saveToFile();
        return serviceRecipe;
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
        return null;
    }

    @Override
    public File getDataFile() {
        return null;
    }

    @Override
    public boolean cleanDateFile() {
        return false;
    }

    @Override
    public byte[] exportTxt() {
        return new byte[0];
    }


    private void saveToFile() {
        try {
            String json = new ObjectMapper().writeValueAsString(recipes);
            filesRecipeService.saveToFile(json);
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
