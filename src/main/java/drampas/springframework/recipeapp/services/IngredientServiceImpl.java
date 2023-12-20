package drampas.springframework.recipeapp.services;

import drampas.springframework.recipeapp.commands.IngredientCommand;
import drampas.springframework.recipeapp.converters.IngredientCommandToIngredient;
import drampas.springframework.recipeapp.converters.IngredientToIngredientCommand;
import drampas.springframework.recipeapp.model.Ingredient;
import drampas.springframework.recipeapp.model.Recipe;
import drampas.springframework.recipeapp.repositories.RecipeRepository;
import drampas.springframework.recipeapp.repositories.UnitOfMeasureRepository;
import drampas.springframework.recipeapp.repositories.reactive.RecipeReactiveRepository;
import drampas.springframework.recipeapp.repositories.reactive.UnitOfMeasureReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Optional;
@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService{
    private final RecipeReactiveRepository recipeReactiveRepository;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;

    public IngredientServiceImpl(RecipeReactiveRepository recipeReactiveRepository, IngredientToIngredientCommand ingredientToIngredientCommand,
                                 IngredientCommandToIngredient ingredientCommandToIngredient, UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository) {
        this.recipeReactiveRepository = recipeReactiveRepository;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.unitOfMeasureReactiveRepository = unitOfMeasureReactiveRepository;
    }

    @Override
    public Mono<IngredientCommand> findByRecipeIdAndIngredientId(String recipeId, String ingredientId) {
       return recipeReactiveRepository.findById(recipeId)
                .map(recipe -> recipe.getIngredients().stream()
                        .filter(ingredient -> ingredient.getId().equals(ingredientId))
                        .findFirst())
                .filter(Optional::isPresent)
                .map(ingredient -> {
                    IngredientCommand ingredientCommand=ingredientToIngredientCommand.convert(ingredient.get());
                    return ingredientCommand;
                });
    }

    @Override
    public Mono<IngredientCommand> saveIngredientCommand(IngredientCommand ingredientCommand) {
        Recipe recipe=recipeReactiveRepository.findById(ingredientCommand.getRecipeId()).block();
        if(recipe == null){
            //throw error
            log.debug("Recipe not found,id:"+ingredientCommand.getRecipeId());
            return Mono.just(new IngredientCommand());
        }else {
            Optional<Ingredient> ingredientOptional=recipe.getIngredients().stream()
                    .filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
                    .findFirst();
            if(ingredientOptional.isPresent()){
                Ingredient ingredient=ingredientOptional.get();
                ingredient.setAmount(ingredientCommand.getAmount());
                ingredient.setDescription(ingredientCommand.getDescription());
                ingredient.setUnitOfMeasure(unitOfMeasureReactiveRepository
                        .findById(ingredientCommand.getUnitOfMeasure().getId()).block());
                if(ingredient.getUnitOfMeasure()==null){
                    throw new RuntimeException("Unit of measure not found");
                }
            }else{
                //adding NEW ingredient
                Ingredient ingredient=ingredientCommandToIngredient.convert(ingredientCommand);
                recipe.addIngredient(ingredient);
                //ingredient.setRecipe(recipe);
            }
            Recipe savedRecipe= recipeReactiveRepository.save(recipe).block();
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
            IngredientCommand savedIngredientCommand=ingredientToIngredientCommand.convert(savedIngredientOptional.get());
            savedIngredientCommand.setRecipeId(recipe.getId());
            return Mono.just(savedIngredientCommand);
        }

    }

    @Override
    public Mono<Void> deleteById(String recipeId, String ingredientId) {
        Recipe recipe= recipeReactiveRepository.findById(recipeId).block();
        if(recipe != null){
            Optional<Ingredient> ingredientOptional=recipe.getIngredients().stream()
                    .filter(ingredient -> ingredient.getId().equals(ingredientId))
                    .findFirst();
            if(ingredientOptional.isPresent()){
                Ingredient ingredient=ingredientOptional.get();

                recipe.getIngredients().remove(ingredient);
                recipeReactiveRepository.save(recipe);
            }
        }else {
            log.debug("Recipe not found, id:"+recipeId);
        }
        return Mono.empty();
    }
}
