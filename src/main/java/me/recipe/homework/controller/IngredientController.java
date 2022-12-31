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
import me.recipe.homework.service.IngredientService;
import me.recipe.homework.service.RecipeService;
import org.apache.coyote.Request;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;

@RestController
@RequestMapping("/ingredient")
@Tag(name = "Ингредиенты", description = "необходимые для приготовления блюда")
public class IngredientController {
    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @Operation(
            summary = "Поиск ингредиента по id"
    )
    @Parameters(
            value = {
                    @Parameter(name = "id", example = "1")
            }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ингредиент найден",
                    content = {
                            @Content(
                                    mediaType = "aplication/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Ingredient.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Такого ингредиент нет"
            )
    })

    @GetMapping()
    public Collection<Object> getAllIngredient() {
        try {
            Path path = IngredientService.createIngredientReport(ingredient);
            if (Files.size(path) == 0)){
                return ResponseEntity.noContent().build();
            }
            InputStreamResource resource = new InputStreamResource(new FileInputStream(path.toFile()));
            return ResponseEntity.ok()
                    .contentType(MediaType.TEXT_PLAIN)
                    .contentLength(Files.size(path))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachmet; filename=\"" + ingredient + "-report.txt\"")
                    .body(resource);
        }catch (IOException e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().build(e.toString());
        }
    }
    @Operation(
            summary = "Все ингредиенты"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ингредиент найден",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Ingredient.class))
                            )
                    }
            )
    }
    )

    @PostMapping
    public Ingredient addIngredient(@RequestBody Ingredient ingredient) {
        return this.ingredientService.addNewIngredient(ingredient);
    }

    @Operation(
            summary = "Редактирование ингредиента по id"
    )
    @Parameters(
            value = {
                    @Parameter(name = "id", example = "1")
            }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ингредиент найден"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Ингредиента по id нет"
            )
    }
    )
    @PutMapping("/{id}")
    public ResponseEntity<Ingredient> editIngredirnt(@PathVariable long id, @RequestBody Ingredient ingredient) {
        Ingredient updatedIngredient = ingredientService.editIngredient(id, ingredient);
        if (ingredient == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedIngredient);
    }

    @Operation(
            summary = "Удаление ингредиента по id"
    )
    @Parameters(
            value = {
                    @Parameter(name = "id", example = "1")
            }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ингредиент удалён"
            )
    }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIngredient(@PathVariable long id) {
        if (ingredientService.deleteIngredient(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllIngredient() {
        ingredientService.deleteAllIngredient();
        return ResponseEntity.ok().build();
    }
}
