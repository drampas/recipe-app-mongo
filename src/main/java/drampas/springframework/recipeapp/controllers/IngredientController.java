package drampas.springframework.recipeapp.controllers;

import drampas.springframework.recipeapp.commands.IngredientCommand;
import drampas.springframework.recipeapp.commands.RecipeCommand;
import drampas.springframework.recipeapp.commands.UnitOfMeasureCommand;
import drampas.springframework.recipeapp.services.IngredientService;
import drampas.springframework.recipeapp.services.RecipeService;
import drampas.springframework.recipeapp.services.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class IngredientController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UnitOfMeasureService unitOfMeasureService;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService, UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }
    @RequestMapping("/recipe/{id}/ingredients")
    public String getIngredientList(@PathVariable String id, Model model){
        log.debug("Getting ingredient list for recipe id:"+id);
        model.addAttribute("recipe",recipeService.findCommandById(id));
        return "/recipe/ingredient/list";
    }
    @RequestMapping("/recipe/{recipeId}/ingredient/{id}/show")
    public String showRecipeIngredient(@PathVariable String recipeId,@PathVariable String id,Model model){
        log.debug("Getting ingredient id:"+id+"for recipe id:"+recipeId);
        model.addAttribute("ingredient",ingredientService.findByRecipeIdAndIngredientId(recipeId,id).block());
        return "/recipe/ingredient/show";
    }
    @RequestMapping("/recipe/{recipeId}/ingredient/{id}/update")
    public String updateRecipeIngredient(@PathVariable String recipeId,@PathVariable String id,Model model){
        model.addAttribute("ingredient",ingredientService.findByRecipeIdAndIngredientId(recipeId,id).block());
        model.addAttribute("uomList",unitOfMeasureService.listAllUom().collectList().block());
        return "/recipe/ingredient/ingredientform";
    }
    @PostMapping("/recipe/{recipeId}/ingredient")
    public String saveOrUpdate(@ModelAttribute IngredientCommand ingredientCommand){
        IngredientCommand savedCommand=ingredientService.saveIngredientCommand(ingredientCommand).block();
        log.debug("saved recipe id:"+savedCommand.getRecipeId());
        log.debug("saved ingredient id:"+savedCommand.getId());
        return "redirect:/recipe/"+savedCommand.getRecipeId()+"/ingredient/"+savedCommand.getId()+"/show";
    }
    @RequestMapping("/recipe/{recipeId}/ingredient/new")
    public String newIngredient(@PathVariable String recipeId,Model model){
        RecipeCommand recipeCommand=recipeService.findCommandById(recipeId).block();
        IngredientCommand ingredientCommand=new IngredientCommand();
        ingredientCommand.setRecipeId(recipeCommand.getId());
        model.addAttribute("ingredient",ingredientCommand);

        ingredientCommand.setUnitOfMeasure(new UnitOfMeasureCommand());
        model.addAttribute("uomList",unitOfMeasureService.listAllUom().collectList().block());
        return "/recipe/ingredient/ingredientform";
    }
    @RequestMapping("/recipe/{recipeId}/ingredient/{id}/delete")
    public String deleteIngredient(@PathVariable String recipeId,@PathVariable String id){
        log.debug("deleting ingredient: "+recipeId+":"+id);
        ingredientService.deleteById(recipeId,id).block();
        return "redirect:/recipe/"+recipeId+"/ingredients";
    }
}
