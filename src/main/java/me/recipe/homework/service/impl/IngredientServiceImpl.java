package me.recipe.homework.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.recipe.homework.model.Ingredient;
import me.recipe.homework.service.FilesIngredientService;
import me.recipe.homework.service.IngredientService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

@Service
public class IngredientServiceImpl implements IngredientService {
    final private FilesIngredientService filesIngredientService;
    private static Map<Integer, Ingredient> ingredients = new TreeMap<>();
    private static int id = 0;
    public IngredientServiceImpl(FilesIngredientService filesIngredientService) {
        this.filesIngredientService = filesIngredientService;
    }
    public Collection<Ingredient> getAll() {
        return ingredients.values();
    }
    public Ingredient addNewIngredient(Ingredient ingredient) {
        if (ingredients.containsKey(id)) {
            throw new RuntimeException("Не может добавить ингредиент с таким же id");
        } else {
            ingredients.put(id++, ingredient);
        }
        saveToFile();
        return ingredient;
    }

    public Ingredient getIngredientById(int id) {
        if (ingredients.containsKey(id)) {
            return ingredients.get(id);
        } else {
            throw new RuntimeException("Нет такого ингредиента");
        }
    }

    @Override
    public Path createIngredient(Ingredient ingredient) {
        return null;
    }

    public Ingredient editIngredient(long id, Ingredient ingredient) {
        if (ingredients.containsKey(id)) {
            ingredients.put((int) id, ingredient);
            return ingredient;
        }
        saveToFile();
        return null;
    }

    public boolean deleteIngredient(long id) {
        if (ingredients.containsKey(id)) {
            ingredients.remove(id);
            return true;
        }
        return false;
    }

    public void deleteAllIngredient() {
        ingredients = new TreeMap<>();
    }

    private void saveToFile() {
        try {
            String json = new ObjectMapper().writeValueAsString(ingredients);
            filesIngredientService.saveToFile(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void readFromFile() {
        String json = filesIngredientService.readFromFile();
        try {
            ingredients = new ObjectMapper().readValue(json, new TypeReference<TreeMap<Integer, Ingredient>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
