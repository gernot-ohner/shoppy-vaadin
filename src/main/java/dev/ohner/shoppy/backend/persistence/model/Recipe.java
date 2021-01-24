package dev.ohner.shoppy.backend.persistence.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC, force = true)
@Entity
public class Recipe implements Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private final long id;
    private String name;

    private Double preparationTimeInMinutes;
    private Double cookingTimeInMinutes;

    @ManyToMany(targetEntity = Ingredient.class, fetch = FetchType.EAGER)
    private final Set<Ingredient> ingredients;

}
