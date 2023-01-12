package me.recipe.homework.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.recipe.homework.model.Ingredient;
import me.recipe.homework.model.Recipe;
import me.recipe.homework.service.FilesRecipeService;
import me.recipe.homework.service.RecipeService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class RecipeServiceImpl implements RecipeService {
    final private FilesRecipeService filesRecipeService;
    private static Map<Integer, Recipe> recipes = new TreeMap<>();
    private static int id = 0;
    private final Path pathToTxtTemplate;

    public RecipeServiceImpl(@Value("./recipeData.json") FilesRecipeService filesRecipeService) {
        try {
            this.filesRecipeService = filesRecipeService;
            this.pathToTxtTemplate = Paths.get(RecipeServiceImpl.class.getResource("recipeTemplate.txt").toURI());
        } catch (InvalidPathException e) {
            e.printStackTrace();
            throw e;
        } catch (URISyntaxException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @PostConstruct
    private void init() {
        readFromFile();
    }

    public Collection<Recipe> getAll() {
        return recipes.values();
    }

    @Override
    public Recipe addRecipe(Recipe recipe) {
        return null;
    }

    @Override
    public Recipe getRecipe(int id) {
        return null;
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

    @Override
    public Path createRicepe(Recipe recipe) throws IOException {
        return null;
    }

    public Recipe editRecipe(long id, Recipe recipe) {
        if (recipes.containsKey(id)) {
            recipes.put((int) id, recipe);
            saveToFile();
            return recipe;
        }
        return null;
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
        return null;
    }

    @Override
    public byte[] exportTxt() {
        try {
            String template = Files.readString(pathToTxtTemplate, StandardCharsets.UTF_8);
            StringBuilder stringBuilder = new StringBuilder();
            for (Recipe recipe : recipes.values()) {
                StringBuilder ingredients = new StringBuilder();
                StringBuilder steps = new StringBuilder();
                for (Ingredient ingredient : recipe.getIngredients()) {
                    ingredients.append(" - ").append(ingredient).append("\n");
                }
                int stepCounter = 1;
                for (String step : recipe.getSteps()){
                    steps.append(stepCounter++).append(". ").append(step).append("\n");
                }
                String recipeData = template.replace("%name%", recipe.getName())
                        .replace("%time%", String.valueOf(recipe.getTime())
                        .replace("%ingredients%", ingredients.toString())
                        .replace("%steps%", steps.toString()));
                        stringBuilder.append(recipeData).append("\n\n\n");
            }
            return stringBuilder.toString().getBytes(StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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
        String json = filesRecipeService.readFromFile();
        try {
            recipes = new ObjectMapper().readValue(json, new TypeReference<TreeMap<Integer, Recipe>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
