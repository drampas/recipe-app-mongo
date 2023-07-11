package drampas.springframework.recipeapp.services;

import drampas.springframework.recipeapp.commands.UnitOfMeasureCommand;
import drampas.springframework.recipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import drampas.springframework.recipeapp.repositories.reactive.UnitOfMeasureReactiveRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;


@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService{

    private final UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;
    private final UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

    public UnitOfMeasureServiceImpl(UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository, UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand) {
        this.unitOfMeasureReactiveRepository = unitOfMeasureReactiveRepository;
        this.unitOfMeasureToUnitOfMeasureCommand = unitOfMeasureToUnitOfMeasureCommand;
    }


    @Override
    public Flux<UnitOfMeasureCommand> listAllUom() {
        return unitOfMeasureReactiveRepository.findAll()
                .map(unitOfMeasureToUnitOfMeasureCommand::convert);
    }
}
