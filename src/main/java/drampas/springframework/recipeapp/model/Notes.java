package drampas.springframework.recipeapp.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
public class Notes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Lob
    private String notes;
    @OneToOne
    private Recipe recipe;

}
