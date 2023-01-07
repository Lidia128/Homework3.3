package me.recipe.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import me.recipe.homework.service.FilesRecipeService;
import me.recipe.homework.service.RecipeService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Path;
import java.io.*;
import java.nio.file.Files;

@RestController
@RequestMapping("/files")
public class FilesRecipeController {
    private final FilesRecipeService filesRecipeService;
    private final RecipeService recipeService;

    public FilesRecipeController(FilesRecipeService filesRecipeService, RecipeService recipeService) {
        this.filesRecipeService = filesRecipeService;
        this.recipeService = recipeService;
    }
    @Operation(
            summary = "Выведение файла с рецептами",
            description = "в формате json"
    )
    @GetMapping(value = "/export", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<InputStreamResource> dowloadDataFile() throws FileNotFoundException {
        File file = filesRecipeService.getDataFile();
        if (file.exists()) {
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .contentLength(file.length())
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachmet; filename=\"RecipesLog.json\"")
                    .body(resource);
        } else {
            return ResponseEntity.noContent().build();
        }
    }
    @Operation(
            summary = "Загрузить свой файл с рецептами",
            description = "файл любого формата"
    )

    @PostMapping(value = "/recipe", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadRecipeDataFile(@RequestParam MultipartFile file) {
        filesRecipeService.cleanDateFile();
        File dataFile = filesRecipeService.getDataFile();

        try (FileOutputStream fos = new FileOutputStream(dataFile)) {
            IOUtils.copy(file.getInputStream(), fos);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
    @Operation(
            summary = "Скачать все рецепты",
            description = "в файле формата .txt"
    )
    @GetMapping("/recipesInFile")
    public ResponseEntity<InputStreamResource> getRecipesInTextFile() {
        try {
            Path path = (Path) recipeService.createRecipesFile();
            if (Files.size (path) == 0) {
                return ResponseEntity.noContent().build();
            }
            InputStreamResource resource = new InputStreamResource(new FileInputStream(path.toFile()));
            return ResponseEntity.ok()
                    .contentType(MediaType.TEXT_PLAIN)
                    .contentLength(Files.size(path))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"RecipesFile.txt\"")
                    .body(resource);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }

    }
}
