package me.recipe.homework.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.recipe.homework.model.Ingredient;
import me.recipe.homework.model.Recipe;
import me.recipe.homework.service.RecipeService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


@SuppressWarnings("ALL")
@Service
public class RecipeServiceImpl implements RecipeService {
    private static int id = 0;
    private Map<Integer, Recipe> recipes;

    private final Path pathToTextTemplate;
    @Value("${path.to.recipe.data.file}")
    private String dataFilePath;
    @Value("${name.of.recipe.data.fale}")
    private String dataFileName;

    public RecipeServiceImpl(Path pathToTextTemplate) throws URISyntaxException {
        this.pathToTextTemplate = Paths.get(RecipeServiceImpl.class.getResource("recipesTemple.txt").toURI());
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
    public File getDataFile() {
        return null;
    }

    @Override
    public boolean cleanDateFile() {
        return false;
    }

    @Override
    public boolean saveToFile(String json) {
        try {
            cleanDateFile();
            Files.writeString(Path.of(dataFilePath, dataFileName), json);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
    @Override
    public byte[] exportTxt() {
        try {
            String template = Files.readString(Path.of(dataFilePath, dataFileName), StandardCharsets.UTF_8);
            StringBuilder stringBuilder = new StringBuilder();
            for (Recipe recipe : recipes.values()) {
                StringBuilder ingredients = new StringBuilder();
                StringBuilder steps = new StringBuilder();
                for (Ingredient ingredient : recipe.getIngredients()) {
                    ingredients.append(" - ").append(ingredient).append("\n");
                }
                int stepCounter = 1;
                for (String step : recipe.getSteps()) {
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
