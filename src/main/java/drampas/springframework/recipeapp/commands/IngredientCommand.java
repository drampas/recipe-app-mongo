package drampas.springframework.recipeapp.commands;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class IngredientCommand {
    private String id;
    private String recipeId;
    private UnitOfMeasureCommand unitOfMeasure;
    @NotBlank
    private String description;

    private BigDecimal amount;
}
