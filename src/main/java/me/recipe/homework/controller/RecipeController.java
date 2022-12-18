package me.recipe.homework.controller;

import me.recipe.homework.model.Ingredient;
import me.recipe.homework.model.Recipe;
import me.recipe.homework.service.RecipeService;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/recipe")

public class RecipeController {
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }
    @GetMapping
    public Collection<Recipe> getAllRecipe (){
        return recipeService.getAll();
    }
    @PostMapping
    public Recipe addRecipe (@RequestBody Recipe recipe){
        return this.recipeService.addNewRecipe(recipe);
    }
}
