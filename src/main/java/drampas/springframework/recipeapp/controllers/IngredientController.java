package drampas.springframework.recipeapp.controllers;

import drampas.springframework.recipeapp.commands.IngredientCommand;
import drampas.springframework.recipeapp.commands.RecipeCommand;
import drampas.springframework.recipeapp.commands.UnitOfMeasureCommand;
import drampas.springframework.recipeapp.services.IngredientService;
import drampas.springframework.recipeapp.services.RecipeService;
import drampas.springframework.recipeapp.services.UnitOfMeasureService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
public class IngredientController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UnitOfMeasureService unitOfMeasureService;
    private WebDataBinder binder;
    public IngredientController(RecipeService recipeService, IngredientService ingredientService, UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }
    @InitBinder("ingredient")
    public void initBinder(WebDataBinder binder) {
        this.binder=binder;
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
        model.addAttribute("ingredient",ingredientService.findByRecipeIdAndIngredientId(recipeId,id));
        return "/recipe/ingredient/show";
    }
    @RequestMapping("/recipe/{recipeId}/ingredient/{id}/update")
    public String updateRecipeIngredient(@PathVariable String recipeId,@PathVariable String id,Model model){
        model.addAttribute("ingredient",ingredientService.findByRecipeIdAndIngredientId(recipeId,id));
        model.addAttribute("uomList",unitOfMeasureService.listAllUom());
        return "/recipe/ingredient/ingredientform";
    }
    @PostMapping("/recipe/{recipeId}/ingredient")
    public String saveOrUpdate(@Valid @ModelAttribute("ingredient") IngredientCommand ingredientCommand, @PathVariable String recipeId
            , Model model){
        binder.validate();
        BindingResult result=binder.getBindingResult();
        if(result.hasErrors()){
            result.getAllErrors().forEach(objectError -> {
                log.debug(objectError.toString());
            });
            model.addAttribute("uomList",unitOfMeasureService.listAllUom());
            return "/recipe/ingredient/ingredientform";
        }
        IngredientCommand savedCommand=ingredientService.saveIngredientCommand(ingredientCommand).block();
        log.debug("saved recipe id:"+savedCommand.getRecipeId());
        log.debug("saved ingredient id:"+savedCommand.getId());
        return "redirect:/recipe/"+recipeId+"/ingredient/"+savedCommand.getId()+"/show";
    }
    @RequestMapping("/recipe/{recipeId}/ingredient/new")
    public String newIngredient(@PathVariable String recipeId,Model model){
        RecipeCommand recipeCommand=recipeService.findCommandById(recipeId).block();
        IngredientCommand ingredientCommand=new IngredientCommand();
        ingredientCommand.setRecipeId(recipeCommand.getId());
        model.addAttribute("ingredient",ingredientCommand);

        ingredientCommand.setUnitOfMeasure(new UnitOfMeasureCommand());
        model.addAttribute("uomList",unitOfMeasureService.listAllUom());
        return "/recipe/ingredient/ingredientform";
    }
    @RequestMapping("/recipe/{recipeId}/ingredient/{id}/delete")
    public String deleteIngredient(@PathVariable String recipeId,@PathVariable String id){
        log.debug("deleting ingredient: "+recipeId+":"+id);
        ingredientService.deleteById(recipeId,id).block();
        return "redirect:/recipe/"+recipeId+"/ingredients";
    }
}
