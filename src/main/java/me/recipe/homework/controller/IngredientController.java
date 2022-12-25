package me.recipe.homework.controller;

import me.recipe.homework.model.Ingredient;
import me.recipe.homework.model.Recipe;
import me.recipe.homework.service.IngredientService;
import org.apache.coyote.Request;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/ingredient")

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
}
