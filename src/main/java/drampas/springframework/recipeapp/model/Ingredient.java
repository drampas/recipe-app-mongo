package drampas.springframework.recipeapp.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
@Data
@Entity
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Recipe recipe;
    @OneToOne(fetch = FetchType.EAGER) //should be EAGER by default,just a reminder
    private UnitOfMeasure unitOfMeasure;
    private String description;
    private BigDecimal amount;

    public Ingredient() {
    }

    public Ingredient(Recipe recipe, UnitOfMeasure unitOfMeasure, String description, BigDecimal amount) {
        this.recipe = recipe;
        this.unitOfMeasure = unitOfMeasure;
        this.description = description;
        this.amount = amount;
    }

}
