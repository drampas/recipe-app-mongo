package drampas.springframework.recipeapp.services;

import drampas.springframework.recipeapp.commands.UnitOfMeasureCommand;

import java.util.Set;

public interface UnitOfMeasureService {
    Set<UnitOfMeasureCommand> listAllUom();
}
