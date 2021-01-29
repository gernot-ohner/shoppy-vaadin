package dev.ohner.shoppy.ui.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.router.Route;
import dev.ohner.shoppy.backend.model.MealPlan;
import dev.ohner.shoppy.backend.persistence.model.Ingredient;
import dev.ohner.shoppy.backend.persistence.model.Recipe;
import dev.ohner.shoppy.backend.service.MailService;
import dev.ohner.shoppy.backend.service.MealPlanGeneratorService;
import dev.ohner.shoppy.ui.MainLayout;

@Route(value = "", layout = MainLayout.class)
public class MainView extends VerticalLayout {

    private final MailService mailService;
    private final MealPlanGeneratorService mealPlanGeneratorService;

    private final EmailField emailField = new EmailField();
    private final Button sendPerMailButton = new Button("Send me the shopping list for this meal plan by mail");
    private final HorizontalLayout emailLayout = new HorizontalLayout(emailField, sendPerMailButton);

    private final Button generateMealPlanButton = new Button("Generate a new Meal Plan!");
    private MealPlan mealPlan;

    private final H2 recipeHeader = new H2("Recipes");
    private final Grid<Recipe> recipeGrid = new Grid<>(Recipe.class);
    private final VerticalLayout recipeLayout = new VerticalLayout(recipeHeader, recipeGrid);

    private final H2 ingredientHeader = new H2("Ingredients");
    private final Grid<Ingredient> ingredientGrid = new Grid<>(Ingredient.class);
    private final VerticalLayout ingredientLayout = new VerticalLayout(ingredientHeader, ingredientGrid);

    public MainView(MailService mailService, MealPlanGeneratorService mealPlanGeneratorService) {
        this.mailService = mailService;
        this.mealPlanGeneratorService = mealPlanGeneratorService;

        sendPerMailButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        sendPerMailButton.addClickListener(e -> sendMail());
        sendPerMailButton.setEnabled(false);

        emailField.setPlaceholder("email@example.com");
        emailField.setEnabled(false);

        generateMealPlanButton.addThemeVariants(ButtonVariant.LUMO_ICON);
        generateMealPlanButton.addClickListener(event -> showNewMealPlan());

        recipeLayout.setVisible(false);
        ingredientLayout.setVisible(false);

        add(generateMealPlanButton);
        add(recipeLayout);
        add(ingredientLayout);
        add(emailLayout);
    }

    public void sendMail() {
        if (mealPlan == null) {
            add(new H2("No meal plan was generated yet!"));
            return;
        }

        final var mailAddress = emailField.getValue();
        final StringBuilder mailText = new StringBuilder();
        mailText.append("This mail would have been sent to: ")
                .append(mailAddress)
                .append("\nFor your meal plan, you should buy the following ingredients:\n");

        mealPlan.getIngredients().forEach(ingredient -> mailText.append("[ ] ")
                .append(ingredient)
                .append("\n"));

        mailText.append("From Shoppy with <3");

        mailService.sendMail("gernot.ohner@gmail.com", "Shoppy Meal Plan", mailText.toString());

        remove(emailLayout);
        add(new H2("Sent!"));

    }

    public void showNewMealPlan() {
        mealPlan = mealPlanGeneratorService.generate();
        emailField.setEnabled(true);
        sendPerMailButton.setEnabled(true);
        configureMealPlanDisplay(mealPlan);
    }

    private void configureMealPlanDisplay(MealPlan mealPlan) {
        recipeGrid.setColumns("id", "name");
        recipeGrid.setItems(mealPlan.getRecipes());
        recipeGrid.setHeightByRows(true);
        recipeLayout.setVisible(true);

        ingredientGrid.setColumns("id", "name");
        ingredientGrid.setItems(mealPlan.getIngredients());
        ingredientGrid.setHeightByRows(true);
        ingredientLayout.setVisible(true);
    }

}
