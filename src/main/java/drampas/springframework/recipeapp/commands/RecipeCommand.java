package drampas.springframework.recipeapp.commands;

import drampas.springframework.recipeapp.model.Difficulty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class RecipeCommand {
    private String id;
    @NotBlank
    @Size(min = 3,max = 255)
    private String description;
    @Min(1)
    @Max(999)
    private Integer cookTime;
    @Min(1)
    @Max(999)
    private Integer prepTime;
    @Min(1)
    @Max(100)
    private Integer serving;
    private String source;
    @URL
    private String url;
    @NotBlank
    private String directions;
    private NotesCommand notes;
    private Difficulty difficulty;
    private Byte[] image;
    private Set<IngredientCommand> ingredients=new HashSet<>();
    private Set<CategoryCommand> categories=new HashSet<>();
}
