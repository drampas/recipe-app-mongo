package drampas.springframework.recipeapp.controllers;

import drampas.springframework.recipeapp.model.Recipe;
import drampas.springframework.recipeapp.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RecipeControllerTest {

    RecipeController recipeController;
    @Mock
    RecipeService recipeService;
    @Mock
    Model model;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        recipeController=new RecipeController(recipeService);
    }
    @Test
    void testMockMVC() throws Exception {
        InternalResourceViewResolver viewResolver =new InternalResourceViewResolver();
        viewResolver.setPrefix("src/main/resources/templates/");
        viewResolver.setSuffix(".html");
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(recipeController).setViewResolvers(viewResolver).build();
        //adding a view resolver to avoid "Circular view path" error
        mockMvc.perform(MockMvcRequestBuilders.get("/recipes"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("recipes"));
    }

    @Test
    void getRecipesPage() {
        //given
        Set<Recipe> recipeSet = new HashSet<>();
        recipeSet.add(new Recipe());
        Recipe recipe2=new Recipe();
        recipe2.setId(1L);
        recipeSet.add(recipe2);
        when(recipeService.getRecipes()).thenReturn(recipeSet);
        ArgumentCaptor<Set<Recipe>> captor = ArgumentCaptor.forClass(Set.class);

        //when
        String recipesPage=recipeController.getRecipesPage(model);

        //then
        assertEquals("recipes",recipesPage);
        verify(recipeService,times(1)).getRecipes();
        verify(model,times(1)).addAttribute(eq("recipes"), captor.capture());
        Set<Recipe> setInController = captor.getValue();
        assertEquals(2,setInController.size());
    }
}