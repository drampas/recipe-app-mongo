package drampas.springframework.recipeapp.commands;

import drampas.springframework.recipeapp.model.Recipe;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NotesCommand {

    private Long id;
    private String recipeNotes;
    //private Recipe recipe; //do I need that?
}
