package drampas.springframework.recipeapp.repositories.reactive;

import drampas.springframework.recipeapp.model.Recipe;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface RecipeReactiveRepository extends ReactiveMongoRepository<Recipe,String> {
}
