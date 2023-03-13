package drampas.springframework.recipeapp.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public UnitOfMeasure getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public void setUnitOfMeasure(UnitOfMeasure unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
