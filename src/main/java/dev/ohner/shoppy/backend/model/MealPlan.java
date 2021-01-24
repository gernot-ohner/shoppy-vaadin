package dev.ohner.shoppy.backend.model;

import dev.ohner.shoppy.backend.persistence.model.Ingredient;
import dev.ohner.shoppy.backend.persistence.model.Recipe;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class MealPlan {

    private final UUID id;
    private final List<Recipe> recipes;
    private List<Ingredient> ingredients;

    public MealPlan() {
        this.id = UUID.randomUUID();
        recipes = new ArrayList<>();
    }
}
