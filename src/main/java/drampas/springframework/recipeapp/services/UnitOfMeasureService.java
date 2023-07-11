package drampas.springframework.recipeapp.services;

import drampas.springframework.recipeapp.commands.UnitOfMeasureCommand;
import reactor.core.publisher.Flux;

import java.util.Set;

public interface UnitOfMeasureService {
    Flux<UnitOfMeasureCommand> listAllUom();
}
