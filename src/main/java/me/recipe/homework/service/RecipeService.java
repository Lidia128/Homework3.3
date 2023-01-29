package me.recipe.homework.service;

import me.recipe.homework.model.Recipe;

import java.io.File;
import java.nio.file.Path;
import java.util.Collection;

public interface RecipeService {

    static String readFromFile() {
        return readFromFile();
    }
    Collection<Recipe> getAll();

    Recipe addRecipe(Recipe recipe);

    Recipe getRecipe(Integer id);

    Recipe editRecipe(long id, Recipe recipe);

    Recipe updateRecipe(int id, Recipe recipe);

    Recipe removeRecipe(int id);

    boolean deleteRecipe(long id);

    void deleteAllRecipe();

    Path createRecipesFile();

    File getDataFile();

    boolean cleanDateFile();

    static boolean saveToFile(String json);

    boolean saveToFile(String json);

    byte[] exportTxt();
}





