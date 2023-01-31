package me.recipe.homework.service.impl;

import me.recipe.homework.model.Ingredient;
import me.recipe.homework.model.Recipe;
import me.recipe.homework.service.FilesRecipeService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class FilesRecipeServiceImpl implements FilesRecipeService {
    @Value("${path.to.recipe.data.file}")
    private String dataFilePath;
    @Value("${name.of.recipe.data.file}")
    private String dataFileName;
    @Value("${path.to.recipe.txt.file}")
    private String dataFilePathTxt;
    @Value("${name.of.recipe.txt.file}")
    private String dataFileNameTxt;

    @Override
    public String readFromFile() {
        try {
            return Files.readString(Path.of(dataFilePath, dataFileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public File getDataFile() {
        return new File(dataFilePath + "/" + dataFileName);}

    @Override
    public File getDataFileTxt() {
        return new File(dataFilePathTxt + "/" + dataFileNameTxt);}


    @Override
    public Path createTempFile(String suffix) {
        try {
            return Files.createTempFile(Path.of(dataFilePath), "tempFile", suffix);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
        return new byte[0];
    }

}

