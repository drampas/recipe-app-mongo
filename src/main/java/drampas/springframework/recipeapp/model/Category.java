package drampas.springframework.recipeapp.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    @ManyToMany(mappedBy = "categories")
    private Set<Recipe> recipes=new HashSet<>();

    public Category() {
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Category;
    }

}
