package dev.ohner.shoppy.ui.form;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;
import dev.ohner.shoppy.backend.persistence.model.Ingredient;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

@Service
@RequestScope
public class IngredientForm extends FormLayout {

    Ingredient ingredient;

    private final Button save = new Button("Confirm");
    private final Button cancel = new Button("Cancel");
    private final Button delete = new Button("Delete");

    private final TextField name = new TextField("Name");
//    private final NumberField carbs = new NumberField("Carbohydrates per 100g");
//    private final NumberField fats = new NumberField("Fats per 100g");
//    private final NumberField protein = new NumberField("Protein per 100g");


    private Binder<Ingredient> binder = new BeanValidationBinder<>(Ingredient.class);

    public IngredientForm() {

        binder.bindInstanceFields(this);
        binder.addStatusChangeListener(event -> save.setEnabled(binder.isValid()));

        final var horizontalLayout = new HorizontalLayout(name, createButtonsLayout());
        horizontalLayout.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.END);
        add(horizontalLayout);
    }


    private HorizontalLayout createButtonsLayout() {

        ingredient = new Ingredient();
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.addClickShortcut(Key.ENTER);
        save.addClickListener(event -> validateAndSave());

        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        cancel.addClickShortcut(Key.ESCAPE);
        cancel.addClickListener(event -> setIngredient(null));

        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        delete.addClickShortcut(Key.DELETE);
        delete.addClickListener(event -> deleteIngredient());

        return new HorizontalLayout(save, cancel, delete);
    }

    private void validateAndSave() {
        try {
            binder.writeBean(ingredient);
            fireEvent(new SaveEvent(this, ingredient));
            ingredient = new Ingredient();
            binder.readBean(ingredient);
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    private void deleteIngredient() {
        fireEvent(new DeleteEvent(this, ingredient));
    }

    private void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
        binder.readBean(ingredient);
    }

    public static abstract class IngredientFormEvent extends ComponentEvent<IngredientForm> {
        private Ingredient ingredient;

        protected IngredientFormEvent(IngredientForm source, Ingredient ingredient) {
            super(source, false);
            this.ingredient = ingredient;
        }

        public Ingredient getIngredient() {
            return ingredient;
        }
    }

    public static class SaveEvent extends IngredientFormEvent {
        SaveEvent(IngredientForm source, Ingredient ingredient) {
            super(source, ingredient);
        }
    }

    public static class DeleteEvent extends IngredientFormEvent {
        DeleteEvent(IngredientForm source, Ingredient ingredient) {
            super(source, ingredient);
        }

    }

    public static class CloseEvent extends IngredientFormEvent {
        CloseEvent(IngredientForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
