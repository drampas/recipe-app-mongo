package drampas.springframework.recipeapp.repositories.reactive;

import drampas.springframework.recipeapp.model.Category;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;


public interface CategoryReactiveRepository extends ReactiveMongoRepository<Category,String> {
    Mono<Category> findByDescription(String description);
}
