package me.recipe.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.recipe.homework.model.Ingredient;
import me.recipe.homework.model.Recipe;
import me.recipe.homework.service.RecipeService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;

@RestController
@RequestMapping("/recipe")
@Tag(name = "Рецепты", description = "Список рецептов для приготовления")
public class RecipeController {
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @Operation(
            summary = "Поиск рецепта по id"
    )
    @Parameters(
            value = {
                    @Parameter(name = "id", example = "1")
            }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Рецепт найден",
                    content = {
                            @Content(
                                    mediaType = "aplication/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Recipe.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Такой рецепта нет"
            )
    })

    @GetMapping
    public Collection<Object> getAllRecipe() {
        try {
           Path path = RecipeService.createRecipeReport(recipe);
            if (Files.size(path) == 0)){
                return ResponseEntity.noContent().build();
            }
                InputStreamResource resource = new InputStreamResource(new FileInputStream(path.toFile()));
                return ResponseEntity.ok()
                        .contentType(MediaType.TEXT_PLAIN)
                        .contentLength(Files.size(path))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachmet; filename=\"" + recipe + "-report.txt\"")
                        .body(resource);
                  }catch (IOException e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().build(e.toString());
       }
    }
    @Operation(
            summary = "Все рецепты"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Рецепт найден",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Recipe.class))
                            )
                    }
            )
    }
    )

    @PostMapping
    public Recipe addRecipe(@RequestBody Recipe recipe) {
        return this.recipeService.addNewRecipe(recipe);
    }
    @Operation(
            summary = "Редактирование рецепта по id"
    )
    @Parameters(
            value = {
                    @Parameter(name = "id", example = "1")
            }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Рецет найден"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Рецепта по id нет"
            )
    }
    )

    @PutMapping("/{id}")
    public ResponseEntity<Recipe> editRecipe(@PathVariable long id, @RequestBody Recipe recipe) {
        Recipe updatedRecipe = recipeService.editRecipe(id, recipe);
       if (recipe == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedRecipe);
    }
    @Operation(
            summary = "Удаление рецепта по id"
    )
    @Parameters(
            value = {
                    @Parameter(name = "id", example = "1")
            }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Рецепт удалён"
            )
    }
    )
    @DeleteMapping ("/{id}")
    public ResponseEntity<Void> deleteRecipe (@PathVariable long id){
    if(recipeService.deleteRecipe(id)){
        return ResponseEntity.ok().build();
    }
    return ResponseEntity.notFound().build();
    }
    @DeleteMapping
    public ResponseEntity<Void> deleteAllRecipe (){
        recipeService.deleteAllRecipe();
        return ResponseEntity.ok().build();
    }
}
