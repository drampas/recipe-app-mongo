package drampas.springframework.recipeapp.services;

import drampas.springframework.recipeapp.commands.RecipeCommand;
import drampas.springframework.recipeapp.converters.RecipeCommandToRecipe;
import drampas.springframework.recipeapp.converters.RecipeToRecipeCommand;
import drampas.springframework.recipeapp.model.Recipe;
import drampas.springframework.recipeapp.repositories.reactive.RecipeReactiveRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;


class RecipeServiceImplTest {

    RecipeServiceImpl recipeService;
    @Mock
    RecipeReactiveRepository recipeReactiveRepository;
    @Mock
    RecipeToRecipeCommand recipeToRecipeCommand;
    @Mock
    RecipeCommandToRecipe recipeCommandToRecipe;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        recipeService=new RecipeServiceImpl(recipeReactiveRepository, recipeCommandToRecipe, recipeToRecipeCommand);
    }

    @Test
    void getRecipes() {

        Recipe recipe=new Recipe();
        HashSet<Recipe> recipeSet=new HashSet<>();
        recipeSet.add(recipe);
        when(recipeService.getRecipes()).thenReturn(Flux.fromIterable(recipeSet));

        List<Recipe> recipeList=recipeService.getRecipes().collectList().block();
        assertEquals(recipeList.size(),1);
        verify(recipeReactiveRepository,times(1)).findAll();

    }

    @Test
    void findById() {
        Recipe recipe=new Recipe();
        recipe.setId("1");

        when(recipeReactiveRepository.findById(anyString())).thenReturn(Mono.just(recipe));
        Recipe recipeReturned=recipeService.findById("1").block();

        assertNotNull(recipeReturned,"Null recipe returned");
        verify(recipeReactiveRepository,times(1)).findById(anyString());
        verify(recipeReactiveRepository,never()).findAll();
    }

    @Test
    void saveRecipeCommandTest() {
        // Given
        RecipeCommand command = new RecipeCommand();
        command.setId("1");
        Recipe detachedRecipe = new Recipe();
        detachedRecipe.setId("1");
        Recipe savedRecipe = new Recipe();
        savedRecipe.setId("1");

        when(recipeCommandToRecipe.convert(any())).thenReturn(detachedRecipe);
        when(recipeReactiveRepository.save(any())).thenReturn(Mono.just(savedRecipe));
        when(recipeToRecipeCommand.convert(any())).thenReturn(command);

        // When
        RecipeCommand savedCommand = recipeService.saveRecipeCommand(command).block();

        // Then
        verify(recipeCommandToRecipe, times(1)).convert(command);
        verify(recipeReactiveRepository, times(1)).save(detachedRecipe);
        verify(recipeToRecipeCommand, times(1)).convert(savedRecipe);
        assertEquals("1", savedCommand.getId());
    }

}