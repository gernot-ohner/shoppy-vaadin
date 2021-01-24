package dev.ohner.shoppy.ui.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;
import dev.ohner.shoppy.backend.RecipeService;
import dev.ohner.shoppy.backend.persistence.model.Recipe;
import dev.ohner.shoppy.ui.MainLayout;
import dev.ohner.shoppy.ui.form.RecipeForm;

import java.util.stream.Collectors;

@Route(value = "recipes", layout = MainLayout.class)
public class RecipesView extends Div {

    private final String SHOW_RECIPES = "Show Recipes";
    private final String HIDE_RECIPES = "Hide Recipes";

    private final Button toggleShowRecipes = new Button();

    private final Button newRecipeButton = new Button("Add Recipe");
    private final RecipeService recipeService;
    private final Grid<Recipe> recipeGrid = new Grid<>(Recipe.class);
    private boolean showRecipes = true;

    private final RecipeForm recipeForm = new RecipeForm();


    public RecipesView(RecipeService recipeService) {
        this.recipeService = recipeService;

        newRecipeButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        // TODO configure a fixed size for the toggle button

        recipeForm.addListener(RecipeForm.SaveEvent.class, this::saveRecipe);

        updateVisibility();
        setSizeFull();

        toggleShowRecipes.addClickListener(e -> updateVisibility());

        configureRecipeGrid();
        updateList();

        add(new HorizontalLayout(toggleShowRecipes, newRecipeButton), recipeForm, recipeGrid);
    }

    private void updateVisibility() {
        showRecipes = !showRecipes;

        if (showRecipes) {
            toggleShowRecipes.setText(HIDE_RECIPES);
        } else {
            toggleShowRecipes.setText(SHOW_RECIPES);
        }

        recipeGrid.setVisible(showRecipes);
    }

    private void configureRecipeGrid() {
        recipeGrid.setVisible(showRecipes);
        recipeGrid.setColumns("name");
        recipeGrid.addColumn(Recipe::getPreparationTimeInMinutes).setHeader("Prep Time");
        recipeGrid.addColumn(Recipe::getCookingTimeInMinutes).setHeader("Cooking Time");
        recipeGrid.addColumn(recipe -> recipe.getIngredients().stream()
                .map(Object::toString)
                .collect(Collectors.joining(", ")))
                .setHeader("Ingredients");

        recipeGrid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

    private void updateList() {
        recipeGrid.setItems(recipeService.findAll());
    }

    private void saveRecipe(RecipeForm.SaveEvent event) {
        recipeService.save(event.getRecipe());
        updateList();
    }

}
