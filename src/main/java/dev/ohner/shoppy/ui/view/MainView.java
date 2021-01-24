package dev.ohner.shoppy.ui.view;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import dev.ohner.shoppy.backend.persistence.model.Ingredient;
import dev.ohner.shoppy.backend.service.IngredientService;
import dev.ohner.shoppy.ui.MainLayout;

@Route(value = "", layout = MainLayout.class)
public class MainView extends VerticalLayout {

    private final IngredientService ingredientService;
    private final Grid<Ingredient> grid = new Grid<>(Ingredient.class);


    public MainView(IngredientService ingredientService) {
        this.ingredientService = ingredientService;

        setSizeFull();
        configureGrid();

        final var headline = new H1("Shoppy");
        add(headline, grid);
        updateList();
    }

    private void configureGrid() {
        grid.setColumns("id", "name");
        grid.setWidth("50%");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

    private void updateList() {
        final var ingredients = ingredientService.findAll();
        grid.setItems(ingredients);
    }

}
