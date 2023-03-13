package drampas.springframework.recipeapp.services;

import drampas.springframework.recipeapp.model.Recipe;

import java.util.Set;

public interface RecipeService {

    Set<Recipe> getRecipes();
}
