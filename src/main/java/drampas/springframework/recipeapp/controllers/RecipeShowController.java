package drampas.springframework.recipeapp.controllers;

import drampas.springframework.recipeapp.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RecipeShowController {
    private final RecipeService recipeService;

    public RecipeShowController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }
    @RequestMapping({"/recipe/show/{id}"})
    public String getRecipeShowPage(@PathVariable String id,Model model){
        model.addAttribute("recipe",recipeService.findById(Long.valueOf(id)));
        return "recipe/show";
    }
}
