package dev.ohner.shoppy.ui.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;
import dev.ohner.shoppy.backend.service.RecipeService;
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

    private final RecipeForm recipeForm;

    public RecipesView(RecipeService recipeService, RecipeForm recipeForm) {
        this.recipeService = recipeService;
        this.recipeForm = recipeForm;

        newRecipeButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        // TODO configure a fixed size for the toggle button

        this.recipeForm.addListener(RecipeForm.SaveEvent.class, this::saveRecipe);
        this.recipeForm.addListener(RecipeForm.CloseEvent.class, this::cancel);
        this.recipeForm.addListener(RecipeForm.DeleteEvent.class, this::deleteRecipe);

        updateVisibility();
        setSizeFull();

        toggleShowRecipes.addClickListener(e -> updateVisibility());

        configureRecipeGrid();
        updateList();

        add(new HorizontalLayout(toggleShowRecipes, newRecipeButton), this.recipeForm, recipeGrid);
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

    private void cancel(RecipeForm.CloseEvent event) {
        // TODO implement cancel functionality
    }

    private void deleteRecipe(RecipeForm.DeleteEvent event) {
        recipeService.delete(event.getRecipe());
    }

}
