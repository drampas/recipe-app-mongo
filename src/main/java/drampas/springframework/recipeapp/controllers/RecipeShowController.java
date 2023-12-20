package drampas.springframework.recipeapp.controllers;

import drampas.springframework.recipeapp.commands.RecipeCommand;
import drampas.springframework.recipeapp.exceptions.NotFoundException;
import drampas.springframework.recipeapp.services.RecipeService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.ModelAndView

@Slf4j
@Controller
public class RecipeShowController {
    private final RecipeService recipeService;

    public RecipeShowController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }
    @RequestMapping("/recipe/show/{id}")
    public String getRecipeShowPage(@PathVariable String id,Model model){
        model.addAttribute("recipe",recipeService.findById(id));
        return "recipe/show";
    }
    @RequestMapping("/recipe/new")
    public String newRecipe(Model model){
        model.addAttribute("recipe",new RecipeCommand());
        return "recipe/recipeform";
    }
    @RequestMapping("recipe/update/{id}")
    public String updateRecipe(@PathVariable String id,Model model){
        model.addAttribute("recipe",recipeService.findCommandById(id));
        return "recipe/recipeform";
    }
    //"recipe" or "/recipe" not working???
    @PostMapping("/recipe/")
    public String saveOrUpdate(@Valid @ModelAttribute("recipe") RecipeCommand recipeCommand, BindingResult result){
        if(result.hasErrors()){
            result.getAllErrors().forEach(objectError -> {
                log.debug(objectError.toString());
            });
            return "recipe/recipeform";
        }
        RecipeCommand savedCommand=recipeService.saveRecipeCommand(recipeCommand).block();
        return "redirect:/recipe/show/"+savedCommand.getId();
    }
    @RequestMapping("/recipe/delete/{id}")
    public String deleteRecipe(@PathVariable String id){
        log.debug("deleting id:"+id);
        recipeService.deleteById(id);
        return "redirect:/recipes";
    }
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    @ExceptionHandler(NotFoundException.class)
//    public ModelAndView handleNotFound(Exception exception){
//        log.error("Not found exception");
//        log.error(exception.getMessage());
//        ModelAndView modelAndView=new ModelAndView();
//        modelAndView.setViewName("404error");
//        modelAndView.addObject("exception",exception);
//        return modelAndView;
//    }

}
