package drampas.springframework.recipeapp.controllers;

import drampas.springframework.recipeapp.commands.RecipeCommand;
import drampas.springframework.recipeapp.exceptions.NotFoundException;
import drampas.springframework.recipeapp.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
public class RecipeShowController {
    private final RecipeService recipeService;

    public RecipeShowController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }
    @RequestMapping("/recipe/show/{id}")
    public String getRecipeShowPage(@PathVariable String id,Model model){
        model.addAttribute("recipe",recipeService.findById(Long.valueOf(id)));
        return "recipe/show";
    }
    @RequestMapping("/recipe/new")
    public String newRecipe(Model model){
        model.addAttribute("recipe",new RecipeCommand());
        return "recipe/recipeform";
    }
    @RequestMapping("recipe/update/{id}")
    public String updateRecipe(@PathVariable String id,Model model){
        model.addAttribute("recipe",recipeService.findCommandById(Long.valueOf(id)));
        return "recipe/recipeform";
    }
    @PostMapping("/recipe/") //"recipe" or "/recipe" not working???
    public String saveOrUpdate(@ModelAttribute RecipeCommand recipeCommand){
        RecipeCommand savedCommand=recipeService.saveRecipeCommand(recipeCommand);
        return "redirect:/recipe/show/"+savedCommand.getId();
    }
    @RequestMapping("/recipe/delete/{id}")
    public String deleteRecipe(@PathVariable String id){
        log.debug("deleting id:"+id);
        recipeService.deleteById(Long.valueOf(id));
        return "redirect:/recipes";
    }
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFound(Exception exception){
        log.error("Not found exception");
        log.error(exception.getMessage());
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("404error");
        modelAndView.addObject("exception",exception);
        return modelAndView;
    }

}
