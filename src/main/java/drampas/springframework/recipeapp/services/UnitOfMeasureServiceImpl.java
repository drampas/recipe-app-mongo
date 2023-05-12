package drampas.springframework.recipeapp.services;

import drampas.springframework.recipeapp.commands.UnitOfMeasureCommand;
import drampas.springframework.recipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import drampas.springframework.recipeapp.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService{

    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

    public UnitOfMeasureServiceImpl(UnitOfMeasureRepository unitOfMeasureRepository, UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand) {
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.unitOfMeasureToUnitOfMeasureCommand = unitOfMeasureToUnitOfMeasureCommand;
    }

    @Override
    public Set<UnitOfMeasureCommand> listAllUom() {
        Set<UnitOfMeasureCommand> unitOfMeasureCommands=new HashSet<>();
        unitOfMeasureRepository.findAll().forEach(unitOfMeasure->
            unitOfMeasureCommands.add(unitOfMeasureToUnitOfMeasureCommand.convert(unitOfMeasure)));
        return unitOfMeasureCommands;
    }
}
