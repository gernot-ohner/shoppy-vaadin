package dev.ohner.shoppy.ui.form;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;
import dev.ohner.shoppy.backend.persistence.model.Recipe;

public class RecipeForm extends FormLayout {

    Recipe recipe;
    Button save = new Button("Confirm");
//    private final Button cancel = new Button("Cancel");

    TextField name = new TextField("Name");
    NumberField preparationTimeInMinutes = new NumberField("Preparation Time in Minutes");
    NumberField cookingTimeInMinutes = new NumberField("Cooking Time in Minutes");
    // TODO add an ingredient field

    private Binder<Recipe> binder = new BeanValidationBinder<>(Recipe.class);

    public RecipeForm() {

        binder.bindInstanceFields(this);
        binder.addStatusChangeListener(event -> save.setEnabled(binder.isValid()));

        add(name, preparationTimeInMinutes, cookingTimeInMinutes, createButtonsLayout());
    }

    private HorizontalLayout createButtonsLayout() {

        recipe = new Recipe();
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.addClickListener(event -> validateAndSave());

//        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

//        confirm.addClickShortcut(Key.ENTER);
//        cancel.addClickShortcut(Key.ESCAPE);

        // TODO add click handlers for the buttons

//        return new HorizontalLayout(confirm, cancel);
        return new HorizontalLayout(save);
    }

    private void validateAndSave() {
        try {
            binder.writeBean(recipe);
            fireEvent(new SaveEvent(this, recipe));

        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    private void setRecipe(Recipe recipe) {
        this.recipe = recipe;
        binder.readBean(recipe);
    }

    public static class SaveEvent extends ComponentEvent<RecipeForm> {

        private Recipe recipe;

        public SaveEvent(RecipeForm source, Recipe recipe) {
            super(source, false);
            this.recipe = recipe;
        }

        public Recipe getRecipe() {
            return recipe;
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

//    public static abstract class RecipeFormEvent extends ComponentEvent<RecipeForm> {
//        private Recipe recipe;
//
//        protected RecipeFormEvent(RecipeForm source, Recipe recipe) {
//            super(source, false);
//            this.recipe = recipe;
//        }
//
//        public Recipe getContact() {
//            return recipe;
//        }
//    }
//
//    public static class SaveEvent extends RecipeFormEvent {
//        SaveEvent(RecipeForm source, Recipe contact) {
//            super(source, contact);
//        }
//    }
//
//    public static class DeleteEvent extends RecipeFormEvent {
//        DeleteEvent(RecipeForm source, Recipe contact) {
//            super(source, contact);
//        }
//
//    }
//
//    public static class CloseEvent extends RecipeFormEvent {
//        CloseEvent(RecipeForm source) {
//            super(source, null);
//        }
//    }
//
//    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
//                                                                  ComponentEventListener<T> listener) {
//        return getEventBus().addListener(eventType, listener);
//    }
}
