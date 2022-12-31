package me.recipe.homework.controller;

import me.recipe.homework.service.FilesIngredientService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@RestController
@RequestMapping("/files")
public class FilesIngredientController {
    private final FilesIngredientService filesIngredientService;

    public FilesIngredientController(FilesIngredientService filesIngredientService) {
        this.filesIngredientService = filesIngredientService;
    }
    @GetMapping(value = "/export")
    public ResponseEntity<InputStreamResource> dowloadDataFile() throws FileNotFoundException {
        File file = filesIngredientService.getDataFile();

        if (file.exists()) {
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .contentLength(file.length())
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachmet; filename=\"IngredientsLog.json\"")
                    .body(resource);
        } else {
            return ResponseEntity.noContent().build();
        }
    }
    @PostMapping(value = "/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadDataFile (@RequestParam MultipartFile file){
        filesIngredientService.cleanDateFile();
        File dataFile = filesIngredientService.getDataFile();

        try (FileOutputStream fos = new FileOutputStream(dataFile)){
            IOUtils.copy(file.getInputStream(), fos);
            return ResponseEntity.ok().build();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}


