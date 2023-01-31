package me.recipe.homework.service;

import java.io.File;
import java.nio.file.Path;

public interface FilesRecipeService {
    boolean saveToFile(String json);

    byte[] exportTxt();

    String readFromFile();

    File getDataFile();

    File getDataFileTxt();

    Path createTempFile(String suffix);

    boolean cleanDateFile();
}
