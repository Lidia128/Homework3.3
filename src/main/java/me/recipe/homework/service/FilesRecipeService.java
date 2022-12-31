package me.recipe.homework.service;

public interface FilesRecipeService {
    boolean saveToFile(String json);

    String readFromFile();
}
