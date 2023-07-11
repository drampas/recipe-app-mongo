package drampas.springframework.recipeapp.model;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class Ingredient {

    private String id= UUID.randomUUID().toString();
    //@DBRef
    //private Recipe recipe;
    private UnitOfMeasure unitOfMeasure;
    private String description;
    private BigDecimal amount;

    public Ingredient() {
    }

    public Ingredient(String id, UnitOfMeasure unitOfMeasure, String description, BigDecimal amount) {
        this.id = id;
        this.unitOfMeasure = unitOfMeasure;
        this.description = description;
        this.amount = amount;
    }

    public Ingredient(Recipe recipe, UnitOfMeasure unitOfMeasure, String description, BigDecimal amount) {
        //this.recipe = recipe;
        this.unitOfMeasure = unitOfMeasure;
        this.description = description;
        this.amount = amount;
    }

}
