package me.recipe.homework.service;

import me.recipe.homework.model.Ingredient;
import me.recipe.homework.model.Recipe;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.nio.file.Path;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Service

public interface IngredientService {
    Collection<Ingredient>getAll();
        Ingredient addNewIngredient(Ingredient ingredient);
        Ingredient getIngredientById (int idIngredient);

    Path createIngredient(Ingredient ingredient);

    Ingredient editIngredient(long id, Ingredient ingredient);
        boolean deleteIngredient(long id);
        void deleteAllIngredient();
    }

