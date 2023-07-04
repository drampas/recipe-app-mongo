package drampas.springframework.recipeapp.repositories;

import drampas.springframework.recipeapp.model.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category,String> {

    Optional<Category> findByDescription(String description);
}
