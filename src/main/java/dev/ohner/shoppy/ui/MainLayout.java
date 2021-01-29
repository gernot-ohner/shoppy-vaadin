package dev.ohner.shoppy.ui;


import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.PWA;
import dev.ohner.shoppy.ui.view.IngredientView;
import dev.ohner.shoppy.ui.view.MainView;
import dev.ohner.shoppy.ui.view.RecipesView;

@CssImport("./styles/shared-styles.css")
@PWA(name = "Shoppy Application",
        shortName = "Shoppy App",
        description = "This is the Shoppy App",
        enableInstallPrompt = false)
public class MainLayout extends AppLayout {

    public MainLayout() {
        createHeader();
        createDrawer();
    }

    private void createHeader() {
        H1 logo = new H1("Shoppy");
        logo.addClassName("logo");

        Anchor logout = new Anchor("logout", "Log out");

        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo, logout);
        header.expand(logo);
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.setWidth("100%");
        header.addClassName("header");
        addToNavbar(header);
   }

    private void createDrawer() {
        RouterLink mainLink = new RouterLink("Main", MainView.class);
        mainLink.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink recipesLink = new RouterLink("Recipes", RecipesView.class);
        mainLink.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink ingredientsLink = new RouterLink("Ingredients", IngredientView.class);
        mainLink.setHighlightCondition(HighlightConditions.sameLocation());

        addToDrawer(new VerticalLayout(mainLink, recipesLink, ingredientsLink));
    }
}
