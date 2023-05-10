package drampas.springframework.recipeapp.converters;

import drampas.springframework.recipeapp.commands.IngredientCommand;
import drampas.springframework.recipeapp.model.Ingredient;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class IngredientToIngredientCommand implements Converter<Ingredient, IngredientCommand> {

    private final UnitOfMeasureToUnitOfMeasureCommand uomConverter;

    public IngredientToIngredientCommand(UnitOfMeasureToUnitOfMeasureCommand uomConverter) {
        this.uomConverter = uomConverter;
    }
    @Nullable
    @Synchronized
    @Override
    public IngredientCommand convert(Ingredient source) {
        if(source == null){
            return null;
        }
        final IngredientCommand ingredientCommand=new IngredientCommand();
        ingredientCommand.setId(source.getId());
        if(source.getRecipe()!=null){
            ingredientCommand.setRecipeId(source.getRecipe().getId());
        }
        ingredientCommand.setUnitOfMeasure(uomConverter.convert(source.getUnitOfMeasure()));
        ingredientCommand.setDescription(source.getDescription());
        ingredientCommand.setAmount(source.getAmount());
        return ingredientCommand;
    }
}
