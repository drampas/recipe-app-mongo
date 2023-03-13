package drampas.springframework.recipeapp.controllers;

import drampas.springframework.recipeapp.model.Category;
import drampas.springframework.recipeapp.model.UnitOfMeasure;
import drampas.springframework.recipeapp.repositories.CategoryRepository;
import drampas.springframework.recipeapp.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
public class IndexController {

    private final CategoryRepository categoryRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public IndexController(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @RequestMapping({"","/","/index","/index.html"})
    public String getIndexPage(){

        return "index";
    }
}
