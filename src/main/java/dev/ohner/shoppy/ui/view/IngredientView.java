package dev.ohner.shoppy.ui.view;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;
import dev.ohner.shoppy.backend.persistence.model.Ingredient;
import dev.ohner.shoppy.backend.service.IngredientService;
import dev.ohner.shoppy.ui.MainLayout;
import dev.ohner.shoppy.ui.form.IngredientForm;

@Route(value = "ingredients", layout = MainLayout.class)
public class IngredientView extends Div {

    private final IngredientService ingredientService;
    private final Grid<Ingredient> ingredientGrid = new Grid<>(Ingredient.class);
    private final IngredientForm ingredientForm;

    public IngredientView(IngredientService ingredientService, IngredientForm ingredientForm) {
        this.ingredientService = ingredientService;
        this.ingredientForm = ingredientForm;

        this.ingredientForm.addListener(IngredientForm.SaveEvent.class, this::saveRecipe);
        setSizeFull();
        configureRecipeGrid();
        updateList();

        final var horizontalLayout = new HorizontalLayout(this.ingredientForm);
        horizontalLayout.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.END);

        add(horizontalLayout, ingredientGrid);
    }

    private void configureRecipeGrid() {
        ingredientGrid.setVisible(true);
        ingredientGrid.setColumns("name");
        ingredientGrid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

    private void updateList() {
        ingredientGrid.setItems(ingredientService.findAll());
    }

    private void saveRecipe(IngredientForm.SaveEvent event) {
        ingredientService.save(event.getIngredient());
        updateList();
    }

}
