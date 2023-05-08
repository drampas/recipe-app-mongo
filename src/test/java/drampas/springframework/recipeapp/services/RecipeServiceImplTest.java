package drampas.springframework.recipeapp.services;

import drampas.springframework.recipeapp.commands.RecipeCommand;
import drampas.springframework.recipeapp.converters.RecipeCommandToRecipe;
import drampas.springframework.recipeapp.converters.RecipeToRecipeCommand;
import drampas.springframework.recipeapp.model.Recipe;
import drampas.springframework.recipeapp.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;


class RecipeServiceImplTest {

    RecipeServiceImpl recipeService;
    @Mock
    RecipeRepository recipeRepository;
    @Mock
    RecipeToRecipeCommand recipeToRecipeCommand;
    @Mock
    RecipeCommandToRecipe recipeCommandToRecipe;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        recipeService=new RecipeServiceImpl(recipeRepository, recipeCommandToRecipe, recipeToRecipeCommand);
    }

    @Test
    void getRecipes() {

        Recipe recipe=new Recipe();
        HashSet<Recipe> recipeSet=new HashSet<>();
        recipeSet.add(recipe);
        when(recipeRepository.findAll()).thenReturn(recipeSet);

        Set<Recipe> recipes=recipeService.getRecipes();
        assertEquals(recipes.size(),1);
        verify(recipeRepository,times(1)).findAll();

    }

    @Test
    void findById() {
        Recipe recipe=new Recipe();
        recipe.setId(1L);
        Optional<Recipe> optionalRecipe=Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(optionalRecipe);
        Recipe recipeReturned=recipeService.findById(1L);
        assertNotNull(recipeReturned,"Null recipe returned");
        verify(recipeRepository,times(1)).findById(anyLong());
        verify(recipeRepository,never()).findAll();
    }

    @Test
    void saveRecipeCommandTest() {
        // Given
        RecipeCommand command = new RecipeCommand();
        command.setId(1L);
        Recipe detachedRecipe = new Recipe();
        detachedRecipe.setId(1L);
        Recipe savedRecipe = new Recipe();
        savedRecipe.setId(1L);

        when(recipeCommandToRecipe.convert(any())).thenReturn(detachedRecipe);
        when(recipeRepository.save(any())).thenReturn(savedRecipe);
        when(recipeToRecipeCommand.convert(any())).thenReturn(command);

        // When
        RecipeCommand savedCommand = recipeService.saveRecipeCommand(command);

        // Then
        verify(recipeCommandToRecipe, times(1)).convert(command);
        verify(recipeRepository, times(1)).save(detachedRecipe);
        verify(recipeToRecipeCommand, times(1)).convert(savedRecipe);
        assertEquals(1L, savedCommand.getId());
    }

}