package drampas.springframework.recipeapp.services;

import drampas.springframework.recipeapp.commands.RecipeCommand;
import drampas.springframework.recipeapp.converters.RecipeCommandToRecipe;
import drampas.springframework.recipeapp.converters.RecipeToRecipeCommand;
import drampas.springframework.recipeapp.exceptions.NotFoundException;
import drampas.springframework.recipeapp.model.Recipe;
import drampas.springframework.recipeapp.repositories.reactive.RecipeReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeReactiveRepository recipeReactiveRepository;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    public RecipeServiceImpl(RecipeReactiveRepository recipeReactiveRepository, RecipeCommandToRecipe recipeCommandToRecipe, RecipeToRecipeCommand recipeToRecipeCommand) {
        this.recipeReactiveRepository = recipeReactiveRepository;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
    }

    @Override
    public Flux<Recipe> getRecipes() {
        log.debug("inside the recipe service");
        return recipeReactiveRepository.findAll();
    }

    @Override
    public Mono<Recipe> findById(String id) {
        return recipeReactiveRepository.findById(id);
    }
    @Override
    public Mono<RecipeCommand> saveRecipeCommand(RecipeCommand command) {
        Recipe recipe=recipeCommandToRecipe.convert(command);
        Mono<RecipeCommand> savedRecipe=recipeReactiveRepository.save(recipe).map(recipeToRecipeCommand::convert);
        return savedRecipe;
    }
    @Override
    public Mono<RecipeCommand> findCommandById(String id) {
        return recipeReactiveRepository.findById(id).map(recipe -> recipeToRecipeCommand.convert(recipe));
    }

    @Override
    public Mono<Void> deleteById(String id) {
        recipeReactiveRepository.deleteById(id).block();
        return Mono.empty();
    }
}
