package drampas.springframework.recipeapp.converters;

import drampas.springframework.recipeapp.commands.IngredientCommand;
import drampas.springframework.recipeapp.model.Ingredient;
import drampas.springframework.recipeapp.model.Recipe;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class IngredientCommandToIngredient implements Converter<IngredientCommand, Ingredient> {
    private final UnitOfMeasureCommandToUnitOfMeasure uomConverter;

    public IngredientCommandToIngredient(UnitOfMeasureCommandToUnitOfMeasure uomConverter) {
        this.uomConverter = uomConverter;
    }

    @Nullable
    @Override
    //@Synchronized
    //@synchronized causing error!?
    public Ingredient convert(IngredientCommand source) {
        if(source == null){
            return null;
        }
        final Ingredient ingredient=new Ingredient();

        if(source.getRecipeId() != null){
            Recipe recipe = new Recipe();
            recipe.setId(source.getRecipeId());
            recipe.addIngredient(ingredient);
        }

        ingredient.setUnitOfMeasure(uomConverter.convert(source.getUnitOfMeasure()));
        ingredient.setDescription(source.getDescription());
        ingredient.setAmount(source.getAmount());
        return ingredient;
    }
}
