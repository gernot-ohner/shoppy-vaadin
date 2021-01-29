package dev.ohner.shoppy.ui.view;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.router.Route;
import dev.ohner.shoppy.backend.service.MailService;
import dev.ohner.shoppy.ui.MainLayout;

@Route(value = "", layout = MainLayout.class)
public class MainView extends VerticalLayout {

    private final MailService mailService;

    private final EmailField emailField = new EmailField();
    private final Button sendPerMailButton = new Button("Send this mealplan by mail");


    public MainView(MailService mailService) {
        this.mailService = mailService;

        sendPerMailButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        sendPerMailButton.addClickShortcut(Key.ENTER);
        sendPerMailButton.addClickListener(e -> sendMail());

        emailField.setPlaceholder("email@example.com");

        add(emailField, sendPerMailButton);
    }

    public void sendMail() {

        final var mailAddress = emailField.getValue();
        final var text = "this mail would have been sent to " + mailAddress;
        mailService.sendMail("gernot.ohner@gmail.com", "Shoppy Meal Plan", text);

        remove(emailField, sendPerMailButton);
        add(new H2("Sent!"));

    }

}
