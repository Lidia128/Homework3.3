package me.recipe.homework.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import me.recipe.homework.model.Ingredient;
import me.recipe.homework.model.Recipe;
import me.recipe.homework.service.IngredientService;
import org.apache.coyote.Request;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/ingredient")
@Tag(name = "Ингредиенты", description = "необходимые для приготовления блюда")
public class IngredientController {
    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }
    @GetMapping
    public Collection <Ingredient>getAllIngredient (){
        return ingredientService.getAll();
    }
    @PostMapping
    public Ingredient addIngredient (@RequestBody Ingredient ingredient){
        return this.ingredientService.addNewIngredient(ingredient);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Ingredient> editIngredirnt(@PathVariable long id, @RequestBody Ingredient ingredient) {
        Ingredient updatedIngredient = ingredientService.editIngredient(id, ingredient);
        if (ingredient == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedIngredient);
    }
    @DeleteMapping ("/{id}")
    public ResponseEntity<Void> deleteIngredient (@PathVariable long id){
        if(ingredientService.deleteIngredient(id)){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
    @DeleteMapping
    public ResponseEntity<Void> deleteAllIngredient (){
        ingredientService.deleteAllIngredient();
        return ResponseEntity.ok().build();
    }
}
