package drampas.springframework.recipeapp.controllers;


import drampas.springframework.recipeapp.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }
    @RequestMapping({"/recipes","/recipes.html"})
    public String getRecipesPage(Model model){
        model.addAttribute("recipes",recipeService.getRecipes());
        return "recipes";
    }
}
