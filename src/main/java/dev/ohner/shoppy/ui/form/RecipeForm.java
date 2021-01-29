package dev.ohner.shoppy.ui.form;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.listbox.MultiSelectListBox;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;
import dev.ohner.shoppy.backend.persistence.model.Ingredient;
import dev.ohner.shoppy.backend.persistence.model.Recipe;
import dev.ohner.shoppy.backend.service.IngredientService;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

@Service
@RequestScope
public class RecipeForm extends FormLayout {

    private Recipe recipe;

    private final IngredientService ingredientService;

    private final Button save = new Button("Confirm");
    private final Button cancel = new Button("Cancel");
    private final Button delete = new Button("Delete");
//    private final Button updateIngredientsButton = new Button("Update Ingredients");

    private final TextField name = new TextField("Name");
    private final NumberField preparationTimeInMinutes = new NumberField("Preparation Time in Minutes");
    private final NumberField cookingTimeInMinutes = new NumberField("Cooking Time in Minutes");
    private final MultiSelectListBox<Ingredient> ingredients = new MultiSelectListBox<>();
    private final Label ingredientLabel = new Label("Ingredients");

    private Binder<Recipe> binder = new BeanValidationBinder<>(Recipe.class);

    public RecipeForm(IngredientService ingredientService) {
        this.ingredientService = ingredientService;

        name.setWidth("200px");
        name.setMaxWidth("200px");
        preparationTimeInMinutes.setMaxWidth("200px");
        cookingTimeInMinutes.setMaxWidth("200px");
        binder.bindInstanceFields(this);
        binder.addStatusChangeListener(event -> save.setEnabled(binder.isValid()));
        ingredients.setId("ingredientsMultiSelectListBox");
        ingredientLabel.setFor(ingredients);

        updateIngredientList();
        binder.bind(ingredients, "ingredients");

//        updateIngredientsButton.addClickListener(event -> updateIngredientList());
        add(name, preparationTimeInMinutes, cookingTimeInMinutes);
//        add(updateIngredientsButton, new VerticalLayout(ingredientLabel, ingredients));
        add(new VerticalLayout(ingredientLabel, ingredients));
        add(new Div(createButtonsLayout()));
    }

    private void updateIngredientList() {
        ingredients.setItems(ingredientService.findAll());
    }


    private HorizontalLayout createButtonsLayout() {

        recipe = new Recipe();
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.addClickListener(event -> validateAndSave());
        save.addClickShortcut(Key.ENTER);

        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        cancel.addClickShortcut(Key.ESCAPE);

        // TODO add click handlers for the buttons
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        delete.addClickListener(event -> delete());

        final var horizontalLayout = new HorizontalLayout(save, cancel, delete);
        horizontalLayout.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.END);
        return horizontalLayout;
    }

    private void validateAndSave() {
        try {
            binder.writeBean(recipe);
            fireEvent(new SaveEvent(this, recipe));
            setRecipe(null);
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    private void delete() {
        fireEvent(new RecipeForm.DeleteEvent(this, recipe));
    }

    private void setRecipe(Recipe recipe) {
        this.recipe = recipe;
        binder.readBean(recipe);
    }

    public static abstract class RecipeFormEvent extends ComponentEvent<RecipeForm> {
        private Recipe recipe;

        protected RecipeFormEvent(RecipeForm source, Recipe recipe) {
            super(source, false);
            this.recipe = recipe;
        }

        public Recipe getRecipe() {
            return recipe;
        }
    }

    public static class SaveEvent extends RecipeFormEvent {
        SaveEvent(RecipeForm source, Recipe recipe) {
            super(source, recipe);
        }
    }

    public static class DeleteEvent extends RecipeFormEvent {
        DeleteEvent(RecipeForm source, Recipe recipe) {
            super(source, recipe);
        }

    }

    public static class CloseEvent extends RecipeFormEvent {
        CloseEvent(RecipeForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
