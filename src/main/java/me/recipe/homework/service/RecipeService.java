package me.recipe.homework.service;

import me.recipe.homework.model.Recipe;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service

public interface RecipeService {

    Collection<Recipe> getAll();

    Recipe addRecipe(Recipe recipe);

    Recipe getRecipe(int id);

    Path createRicepe(Recipe recipe) throws IOException;

    Recipe editRecipe(long id, Recipe recipe);

    Recipe updateRecipe(int id, Recipe recipe);

    Recipe removeRecipe(int id);

    boolean deleteRecipe(long id);

    void deleteAllRecipe();


}





