package me.recipe.homework.service;

import me.recipe.homework.model.Recipe;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service

public interface RecipeService {
    Collection<Recipe> getAll();

    Recipe addNewRecipe(Recipe recipe);

    Recipe getRecipeById(int idRecipe);
}




