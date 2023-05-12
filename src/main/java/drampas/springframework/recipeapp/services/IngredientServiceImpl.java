package drampas.springframework.recipeapp.services;

import drampas.springframework.recipeapp.commands.IngredientCommand;
import drampas.springframework.recipeapp.converters.IngredientCommandToIngredient;
import drampas.springframework.recipeapp.converters.IngredientToIngredientCommand;
import drampas.springframework.recipeapp.model.Ingredient;
import drampas.springframework.recipeapp.model.Recipe;
import drampas.springframework.recipeapp.repositories.RecipeRepository;
import drampas.springframework.recipeapp.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService{
    private final RecipeRepository recipeRepository;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public IngredientServiceImpl(RecipeRepository recipeRepository, IngredientToIngredientCommand ingredientToIngredientCommand,
                                 IngredientCommandToIngredient ingredientCommandToIngredient, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.recipeRepository = recipeRepository;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
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

    @Override
    @Transactional
    public IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand) {
        Optional<Recipe> recipeOptional=recipeRepository.findById(ingredientCommand.getRecipeId());
        if(recipeOptional.isEmpty()){
            //throw error
            log.debug("Recipe not found,id:"+ingredientCommand.getRecipeId());
            return new IngredientCommand();
        }else {
            Recipe recipe=recipeOptional.get();
            Optional<Ingredient> ingredientOptional=recipe.getIngredients().stream()
                    .filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
                    .findFirst();
            if(ingredientOptional.isPresent()){
                Ingredient ingredient=ingredientOptional.get();
                ingredient.setAmount(ingredientCommand.getAmount());
                ingredient.setDescription(ingredientCommand.getDescription());
                ingredient.setUnitOfMeasure(unitOfMeasureRepository
                        .findById(ingredientCommand.getUnitOfMeasure().getId())
                        .orElseThrow(()->new RuntimeException("Unit of measure not found")));
            }else{
                //adding NEW ingredient
                Ingredient ingredient=ingredientCommandToIngredient.convert(ingredientCommand);
                recipe.addIngredient(ingredient);
                ingredient.setRecipe(recipe);
            }
            Recipe savedRecipe=recipeRepository.save(recipe);
            Optional<Ingredient> savedIngredientOptional=savedRecipe.getIngredients().stream()
                    .filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
                    .findFirst();

            if(savedIngredientOptional.isEmpty()){
                savedIngredientOptional=savedRecipe.getIngredients().stream()
                        .filter(ingredient -> ingredient.getDescription().equals(ingredientCommand.getDescription()))
                        .filter(ingredient -> ingredient.getAmount().equals(ingredientCommand.getAmount()))
                        .filter(ingredient -> ingredient.getUnitOfMeasure().getId().equals(ingredientCommand.getUnitOfMeasure().getId()))
                        .findFirst();
            }
            return ingredientToIngredientCommand.convert(savedIngredientOptional.get());
        }

    }

    @Override
    public void deleteById(Long recipeId, Long ingredientId) {
        Optional<Recipe> recipeOptional=recipeRepository.findById(recipeId);
        if(recipeOptional.isPresent()){
            Recipe recipe=recipeOptional.get();
            Optional<Ingredient> ingredientOptional=recipe.getIngredients().stream()
                    .filter(ingredient -> ingredient.getId().equals(ingredientId))
                    .findFirst();
            if(ingredientOptional.isPresent()){
                Ingredient ingredient=ingredientOptional.get();
                ingredient.setRecipe(null);
                recipe.getIngredients().remove(ingredient);
                recipeRepository.save(recipe);
            }
        }else {
            log.debug("Recipe not found, id:"+recipeId);
        }
    }
}
