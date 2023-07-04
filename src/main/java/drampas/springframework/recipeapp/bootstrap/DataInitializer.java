package drampas.springframework.recipeapp.bootstrap;

import drampas.springframework.recipeapp.model.*;
import drampas.springframework.recipeapp.repositories.CategoryRepository;
import drampas.springframework.recipeapp.repositories.RecipeRepository;
import drampas.springframework.recipeapp.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Slf4j
@Component
public class DataInitializer implements ApplicationListener<ContextRefreshedEvent> {

    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final RecipeRepository recipeRepository;
    private final CategoryRepository categoryRepository;

    public DataInitializer(UnitOfMeasureRepository unitOfMeasureRepository, RecipeRepository recipeRepository, CategoryRepository categoryRepository) {
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.recipeRepository = recipeRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        unitOfMeasureRepository.deleteAll();
        loadUom();
        categoryRepository.deleteAll();
        loadCategories();
        recipeRepository.deleteAll();
        recipeRepository.saveAll(getRecipes());
        log.debug("loading Bootstrap data");
    }

    private void loadCategories(){
        Category cat1 = new Category();
        cat1.setDescription("American");
        categoryRepository.save(cat1);

        Category cat2 = new Category();
        cat2.setDescription("Italian");
        categoryRepository.save(cat2);

        Category cat3 = new Category();
        cat3.setDescription("Mexican");
        categoryRepository.save(cat3);

        Category cat4 = new Category();
        cat4.setDescription("Fast Food");
        categoryRepository.save(cat4);
    }
    private void loadUom(){
        UnitOfMeasure uom1 = new UnitOfMeasure();
        uom1.setDescription("Teaspoon");
        unitOfMeasureRepository.save(uom1);

        UnitOfMeasure uom2 = new UnitOfMeasure();
        uom2.setDescription("Tablespoon");
        unitOfMeasureRepository.save(uom2);

        UnitOfMeasure uom3 = new UnitOfMeasure();
        uom3.setDescription("Cup");
        unitOfMeasureRepository.save(uom3);

        UnitOfMeasure uom4 = new UnitOfMeasure();
        uom4.setDescription("Pinch");
        unitOfMeasureRepository.save(uom4);

        UnitOfMeasure uom5 = new UnitOfMeasure();
        uom5.setDescription("Ounce");
        unitOfMeasureRepository.save(uom5);

        UnitOfMeasure uom6 = new UnitOfMeasure();
        uom6.setDescription("Each");
        unitOfMeasureRepository.save(uom6);

        UnitOfMeasure uom7 = new UnitOfMeasure();
        uom7.setDescription("Pint");
        unitOfMeasureRepository.save(uom7);

        UnitOfMeasure uom8 = new UnitOfMeasure();
        uom8.setDescription("Dash");
        unitOfMeasureRepository.save(uom8);
    }
    private List<Recipe> getRecipes() {

        List<Recipe> recipeList=new ArrayList<>();

        //getting units of measure and categories that where added by the "data.sql"

        Optional<UnitOfMeasure> optionalTeaspoon=unitOfMeasureRepository.findByDescription("Teaspoon");
        if(optionalTeaspoon.isEmpty()){
            throw new RuntimeException("Unit of measure not found");
        }
        Optional<UnitOfMeasure> optionalTablespoon=unitOfMeasureRepository.findByDescription("Tablespoon");
        if(optionalTablespoon.isEmpty()){
            throw new RuntimeException("Unit of measure not found");
        }
        Optional<UnitOfMeasure> optionalPinch=unitOfMeasureRepository.findByDescription("Pinch");
        if(optionalPinch.isEmpty()){
            throw new RuntimeException("Unit of measure not found");
        }
        Optional<UnitOfMeasure> optionalEach=unitOfMeasureRepository.findByDescription("Each");
        if(optionalEach.isEmpty()){
            throw new RuntimeException("Unit of measure not found");
        }
        Optional<UnitOfMeasure> optionalCups=unitOfMeasureRepository.findByDescription("Cup");
        if(optionalCups.isEmpty()){
            throw new RuntimeException("Unit of measure not found");
        }
        Optional<UnitOfMeasure> optionalPint=unitOfMeasureRepository.findByDescription("Pint");
        if(optionalPint.isEmpty()){
            throw new RuntimeException("Unit of measure not found");
        }

        UnitOfMeasure teaSpoon=optionalTeaspoon.get();
        UnitOfMeasure tableSpoon=optionalTablespoon.get();
        UnitOfMeasure pinch=optionalPinch.get();
        UnitOfMeasure each=optionalEach.get();
        UnitOfMeasure cup=optionalCups.get();
        UnitOfMeasure pint=optionalPint.get();

        Optional<Category> optionalAmerican=categoryRepository.findByDescription("American");
        if(optionalAmerican.isEmpty()){
            throw new RuntimeException("Category not found");
        }
        Optional<Category> optionalMexican=categoryRepository.findByDescription("Mexican");
        if(optionalMexican.isEmpty()){
            throw new RuntimeException("Category not found");
        }

        Category american=optionalAmerican.get();
        Category mexican=optionalMexican.get();

        //adding the guacamole recipe
        Recipe guacamole=new Recipe();
        guacamole.setDescription("How to Make the Best Guacamole");
        guacamole.setPrepTime(10);
        guacamole.setCookTime(0);
        guacamole.setServing(4);
        guacamole.setDifficulty(Difficulty.EASY);
        guacamole.setSource("Simply recipes");
        guacamole.setUrl("https://www.simplyrecipes.com/recipes/perfect_guacamole/");
        guacamole.setDirections("1. Cut the avocados:" +
                "\n" +
                "Cut the avocados in half. Remove the pit. Score the inside of the avocado with a blunt knife and scoop" +
                " out the flesh with a spoon. (See How to Cut and Peel an Avocado.) Place in a bowl.\n2. Mash the avocado flesh:" +
                "\n" +
                "Using a fork, roughly mash the avocado. (Don't overdo it! The guacamole should be a little chunky.)\n 3. Add the remaining ingredients to taste:" +
                "\n" +
                "Sprinkle with salt and lime (or lemon) juice. The acid in the lime juice will provide some balance to the richness of the avocado and will help delay the avocados from turning brown." +
                "\n" +
                "Add the chopped onion, cilantro, black pepper, and chilis. Chili peppers vary individually in their spiciness. So, start with a half of one chili pepper and add more to the guacamole to your desired degree of heat." +
                "\n" +
                "Remember that much of this is done to taste because of the variability in the fresh ingredients. Start with this recipe and adjust to your taste.\n4.  Serve immediately:" +
                "\n" +
                "If making a few hours ahead, place plastic wrap on the surface of the guacamole and press down to cover it to prevent air reaching it. (The oxygen in the air causes oxidation which will turn the guacamole brown.)" +
                "\n" +
                "Garnish with slices of red radish or jigama strips. Serve with your choice of store-bought tortilla chips or make your own homemade tortilla chips." +
                "\n" +
                "Refrigerate leftover guacamole up to 3 days. ");

        Notes guacamoleNotes=new Notes();
        guacamoleNotes.setRecipeNotes("Chilling tomatoes hurts their flavor. So, if you want to add chopped tomato to your guacamole, add it just before serving");
        //guacamoleNotes.setRecipe(guacamole);
        guacamole.setNotes(guacamoleNotes);

        guacamole.getIngredients().add(new Ingredient(guacamole,each,"ripe avocados",new BigDecimal(2)));
        guacamole.getIngredients().add(new Ingredient(guacamole,teaSpoon,"kosher salt",new BigDecimal("0.25")));
        guacamole.getIngredients().add(new Ingredient(guacamole,tableSpoon,"fresh lime",new BigDecimal(1)));
        guacamole.getIngredients().add(new Ingredient(guacamole,tableSpoon,"minced red onion",new BigDecimal(4)));
        guacamole.getIngredients().add(new Ingredient(guacamole,each,"serrano chillis",new BigDecimal(2)));
        guacamole.getIngredients().add(new Ingredient(guacamole,tableSpoon,"cilantro",new BigDecimal(2)));
        guacamole.getIngredients().add(new Ingredient(guacamole,pinch,"freshly ground black pepper",new BigDecimal(1)));
        guacamole.getIngredients().add(new Ingredient(guacamole,each,"ripe tomato",new BigDecimal("0.5")));

        guacamole.getCategories().add(american);
        guacamole.getCategories().add(mexican);

        //adding the chicken-tacos recipe
        Recipe chickenTacos=new Recipe();
        chickenTacos.setDescription("Spicy Grilled Chicken Tacos");
        chickenTacos.setPrepTime(20);
        chickenTacos.setCookTime(15);
        chickenTacos.setServing(6);
        chickenTacos.setDifficulty(Difficulty.MODERATE);
        chickenTacos.setSource("Simply recipes");
        chickenTacos.setUrl("https://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/");
        chickenTacos.setDirections(
                "1.Prepare the grill:" +
                "\n" +
                "Prepare either a gas or charcoal grill for medium-high, direct heat.\n" +
                "2.Make the marinade and coat the chicken:" +
                "\n" +
                "In a large bowl, stir together the chili powder, oregano, cumin, sugar, salt, garlic and orange zest. Stir in the orange juice and olive oil to make a loose paste. Add the chicken to the bowl and toss to coat all over." +
                "\n" +
                "Set aside to marinate while the grill heats and you prepare the rest of the toppings.\n" + "3.Grill the chicken:" + "\n" +
                        "Grill the chicken for 3 to 4 minutes per side, or until a thermometer inserted into the thickest part of the meat registers 165°F. Transfer to a plate and rest for 5 minutes.\n" +
                        "4.Thin the sour cream with milk:" +
                        "\n" +
                        "Stir together the sour cream and milk to thin out the sour cream to make it easy to drizzle.\n" +
                        "5.Assemble the tacos:" +
                        "\n" +
                        "Slice the chicken into strips. On each tortilla, place a small handful of arugula. Top with chicken slices, sliced avocado, radishes, tomatoes, and onion slices. Drizzle with the thinned sour cream. Serve with lime wedges." +
                        "6.Warm the tortillas:" +
                        "\n" +
                        "Place each tortilla on the grill or on a hot, dry skillet over medium-high heat. As soon as you see pockets of the air start to puff up in the tortilla, turn it with tongs and heat for a few seconds on the other side." +
                        "\n" +
                        "Wrap warmed tortillas in a tea towel to keep them warm until serving. ");

        Notes chickenTacosNotes=new Notes();
        chickenTacosNotes.setRecipeNotes("Look for ancho chile powder with the Mexican ingredients at your grocery store, on buy it online. (If you can't find ancho chili powder, you replace the ancho chili, the oregano, and the cumin with 2 1/2 tablespoons regular chili powder, though the flavor won't be quite the same.)");
        //chickenTacosNotes.setRecipe(chickenTacos);
        chickenTacos.setNotes(chickenTacosNotes);

        chickenTacos.getIngredients().add(new Ingredient(chickenTacos,tableSpoon,"ancho chilli powder",new BigDecimal(2)));
        chickenTacos.getIngredients().add(new Ingredient(chickenTacos,teaSpoon,"dried oregano",new BigDecimal(1)));
        chickenTacos.getIngredients().add(new Ingredient(chickenTacos,teaSpoon,"dried cumin",new BigDecimal(1)));
        chickenTacos.getIngredients().add(new Ingredient(chickenTacos,teaSpoon,"sugar",new BigDecimal(1)));
        chickenTacos.getIngredients().add(new Ingredient(chickenTacos,teaSpoon,"kosher salt",new BigDecimal("0.5")));
        chickenTacos.getIngredients().add(new Ingredient(chickenTacos,each,"clove garlic, finely chopped",new BigDecimal(1)));
        chickenTacos.getIngredients().add(new Ingredient(chickenTacos,tableSpoon,"finely grated orange zest",new BigDecimal(1)));
        chickenTacos.getIngredients().add(new Ingredient(chickenTacos,tableSpoon,"fresh-squeezed orange juice",new BigDecimal(3)));
        chickenTacos.getIngredients().add(new Ingredient(chickenTacos,tableSpoon,"olive oil",new BigDecimal(2)));
        chickenTacos.getIngredients().add(new Ingredient(chickenTacos,each,"skinless, boneless chicken thighs",new BigDecimal(6)));
        chickenTacos.getIngredients().add(new Ingredient(chickenTacos,each,"small corn tortillas",new BigDecimal(8)));
        chickenTacos.getIngredients().add(new Ingredient(chickenTacos,cup,"packed baby arugula",new BigDecimal(3)));
        chickenTacos.getIngredients().add(new Ingredient(chickenTacos,each,"medium ripe avocados, sliced",new BigDecimal(2)));
        chickenTacos.getIngredients().add(new Ingredient(chickenTacos,each,"radishes, thinly sliced",new BigDecimal(4)));
        chickenTacos.getIngredients().add(new Ingredient(chickenTacos,pint,"cherry tomatoes, halved",new BigDecimal("0.5")));
        chickenTacos.getIngredients().add(new Ingredient(chickenTacos,each,"red onion, thinly sliced",new BigDecimal("0.25")));
        chickenTacos.getIngredients().add(new Ingredient(chickenTacos,each,"Roughly chopped cilantro",new BigDecimal(1)));
        chickenTacos.getIngredients().add(new Ingredient(chickenTacos,cup,"sour cream",new BigDecimal("0.5")));
        chickenTacos.getIngredients().add(new Ingredient(chickenTacos,cup,"milk",new BigDecimal("0.25")));
        chickenTacos.getIngredients().add(new Ingredient(chickenTacos,each,"lime, cut into wedges",new BigDecimal(1)));

        chickenTacos.getCategories().add(american);
        chickenTacos.getCategories().add(mexican);

        //adding all recipes to the returned list
        recipeList.add(guacamole);
        recipeList.add(chickenTacos);

        return recipeList;
    }

}
