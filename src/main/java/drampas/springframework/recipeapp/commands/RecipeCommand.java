package drampas.springframework.recipeapp.commands;

import drampas.springframework.recipeapp.model.Difficulty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class RecipeCommand {
    private Long id;
    private String description;
    private Integer cookTime;
    private Integer prepTime;
    private Integer serving;
    private String source;
    private String url;
    private String directions;
    private NotesCommand notes;
    private Difficulty difficulty;
    private Byte[] image;
    private Set<IngredientCommand> ingredients=new HashSet<>();
    private Set<CategoryCommand> categories=new HashSet<>();
}
