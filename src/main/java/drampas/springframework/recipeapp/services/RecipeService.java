package drampas.springframework.recipeapp.services;

import drampas.springframework.recipeapp.commands.RecipeCommand;
import drampas.springframework.recipeapp.model.Recipe;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface RecipeService {

    Flux<Recipe> getRecipes();
    Mono<Recipe> findById(String id);
    Mono<RecipeCommand> saveRecipeCommand(RecipeCommand command);
    Mono<RecipeCommand> findCommandById(String id);
    Mono<Void> deleteById(String id);
}
