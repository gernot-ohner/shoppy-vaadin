package dev.ohner.shoppy.backend.service;

import dev.ohner.shoppy.backend.model.MealPlan;
import dev.ohner.shoppy.backend.persistence.IngredientRepository;
import dev.ohner.shoppy.backend.persistence.RecipeRepository;
import dev.ohner.shoppy.backend.persistence.model.Recipe;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class MealPlanGeneratorService {

    private static final Integer NUMBER_OF_MEALS = 2;

    private final RecipeRepository recipeRepository;

    public MealPlanGeneratorService(RecipeRepository recipeRepository, IngredientRepository ingredientRepository) {
        this.recipeRepository = recipeRepository;
    }

    public MealPlan generate() {

        MealPlan mealPlan = new MealPlan();
        List<Recipe> allRecipes = recipeRepository.findAll();
        Random randy = new Random();

        List<Recipe> recipes = mealPlan.getRecipes();

        for (int i = 0; i < NUMBER_OF_MEALS; i++) {
            recipes.add(allRecipes.remove(randy.nextInt(allRecipes.size())));
        }

        var ingredients = recipes.stream().flatMap(recipe ->
                recipe.getIngredients().stream())
                .distinct()
                .collect(Collectors.toList());

        mealPlan.setIngredients(ingredients);

        return mealPlan;
    }


}
