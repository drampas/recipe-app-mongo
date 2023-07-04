package drampas.springframework.recipeapp.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.math.BigDecimal;
@Getter
@Setter
public class Ingredient {

    @Id
    private String id;
    //@DBRef
    //private Recipe recipe;
    @DBRef
    private UnitOfMeasure unitOfMeasure;
    private String description;
    private BigDecimal amount;

    public Ingredient() {
    }

    public Ingredient(Recipe recipe, UnitOfMeasure unitOfMeasure, String description, BigDecimal amount) {
        //this.recipe = recipe;
        this.unitOfMeasure = unitOfMeasure;
        this.description = description;
        this.amount = amount;
    }

}
