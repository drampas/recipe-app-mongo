package drampas.springframework.recipeapp.controllers;

import drampas.springframework.recipeapp.model.Recipe;
import drampas.springframework.recipeapp.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class RecipeShowControllerTest {
    RecipeShowController recipeShowController;
    @Mock
    RecipeService recipeService;
    //would I need the setUp if I used @InjectMocks at the controller??
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        recipeShowController= new RecipeShowController(recipeService);
    }

    @Test
    void getRecipeShowPage() throws Exception {
        Recipe recipe=new Recipe();
        recipe.setId(1L);
        MockMvc mockMvc= MockMvcBuilders.standaloneSetup(recipeShowController).build();
        when(recipeService.findById(anyLong())).thenReturn(recipe);
        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/show/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("recipe/show"));
    }
}