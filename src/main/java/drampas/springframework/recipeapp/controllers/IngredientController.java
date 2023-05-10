package drampas.springframework.recipeapp.controllers;

import drampas.springframework.recipeapp.services.IngredientService;
import drampas.springframework.recipeapp.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class IngredientController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
    }
    @RequestMapping("/recipe/{id}/ingredients")
    public String getIngredientList(@PathVariable String id, Model model){
        log.debug("Getting ingredient list for recipe id:"+id);
        model.addAttribute("recipe",recipeService.findCommandById(Long.valueOf(id)));
        return "/recipe/ingredient/list";
    }
    @RequestMapping("/recipe/{recipeId}/ingredient/{id}/show")
    public String showRecipeIngredient(@PathVariable String recipeId,@PathVariable String id,Model model){
        log.debug("Getting ingredient id:"+id+"for recipe id:"+recipeId);
        model.addAttribute("ingredient",ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId),Long.valueOf(id)));
        return "/recipe/ingredient/show";
    }
}
