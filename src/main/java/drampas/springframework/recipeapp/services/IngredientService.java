package drampas.springframework.recipeapp.services;

import drampas.springframework.recipeapp.commands.IngredientCommand;

public interface IngredientService {
    IngredientCommand findByRecipeIdAndIngredientId(Long recipeId,Long ingredientId);
}
