package drampas.springframework.recipeapp.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Document
public class Recipe {

    @Id
    private String id;
    private String description;
    private Integer cookTime;
    private Integer prepTime;
    private Integer serving;
    private String source;
    private String url;
    private String directions;
    private Notes notes;
    private Difficulty difficulty;
    private Set<Ingredient> ingredients=new HashSet<>();
    private Byte[] image;
    private Set<Category> categories=new HashSet<>();

    public void setNotes(Notes notes) {
        if (notes != null) {
            this.notes = notes;
            //notes.setRecipe(this);
        }
    }

    public Recipe addIngredient(Ingredient ingredient){
        //ingredient.setRecipe(this);
        this.ingredients.add(ingredient);
        return this;
    }

}
