package me.recipe.homework.service;

public interface FilesIngredientService {
    boolean saveToFile(String json);

    String readFromFile();
}
