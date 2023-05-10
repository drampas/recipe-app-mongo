package drampas.springframework.recipeapp.services;

import drampas.springframework.recipeapp.commands.IngredientCommand;
import drampas.springframework.recipeapp.converters.IngredientToIngredientCommand;
import drampas.springframework.recipeapp.model.Ingredient;
import drampas.springframework.recipeapp.model.Recipe;
import drampas.springframework.recipeapp.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService{
    private final RecipeRepository recipeRepository;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;

    public IngredientServiceImpl(RecipeRepository recipeRepository, IngredientToIngredientCommand ingredientToIngredientCommand) {
        this.recipeRepository = recipeRepository;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
    }

    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId,Long ingredientId) {
        Optional<Recipe> recipeOptional=recipeRepository.findById(recipeId);
        if(recipeOptional.isEmpty()){
            // TODO: 9/5/2023 add error handling
            log.debug("recipe id not found,id:"+recipeId);
        }
        Recipe recipe=recipeOptional.get();
        Optional<Ingredient> ingredientOptional=recipe.getIngredients().stream()
                .filter(ingredient->ingredient.getId().equals(ingredientId)).findFirst();
        if(ingredientOptional.isEmpty()){
            log.debug("ingredient id not found,id:"+ingredientId);
        }
        IngredientCommand ingredientCommand=ingredientToIngredientCommand.convert(ingredientOptional.get());
        return ingredientCommand;
    }
}
