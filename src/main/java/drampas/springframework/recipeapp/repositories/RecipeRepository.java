package drampas.springframework.recipeapp.repositories;

import drampas.springframework.recipeapp.model.Recipe;
import org.springframework.data.repository.CrudRepository;

public interface RecipeRepository extends CrudRepository<Recipe,String> {
}
