package me.recipe.homework.service;

import java.io.File;
import java.nio.file.Path;

public interface FilesIngredientService {
    boolean saveToFile(String json);

    String readFromFile();

    File getDataFile();

    Path createTempFile(String suffix);

    boolean cleanDateFile();
}
