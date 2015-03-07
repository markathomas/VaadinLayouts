package org.vaadin.addons.layouts.demo;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import javax.servlet.annotation.WebServlet;

import org.vaadin.addons.layouts.CardLayout;

@Theme("demo")
@Title("Layouts Add-on Demo")
@SuppressWarnings("serial")
public class DemoUI extends UI {

    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = DemoUI.class,
      widgetset = "org.vaadin.addons.layouts.demo.DemoWidgetSet")
    public static class Servlet extends VaadinServlet {
    }

    @Override
    protected void init(VaadinRequest request) {


        // Initialize our new UI component
        final CardLayout component = new CardLayout();
        component.addComponent(newLabel("First"));
        component.addComponent(newLabel("Second"));
        component.addComponent(newLabel("Third"));
        component.addComponent(newLabel("Fourth"));

        Button first = new Button("First <<", new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                component.first();
            }
        });
        Button next = new Button("Next >", new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                component.next();
            }
        });
        Button previous = new Button("< Previous", new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                component.previous();
            }
        });
        Button last = new Button("Last >>", new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                component.last();
            }
        });

        HorizontalLayout hl = new HorizontalLayout(first, previous, next, last);
        hl.setMargin(true);
        hl.setSpacing(true);
        hl.setSizeUndefined();

        // Show it in the middle of the screen
        final VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        layout.setStyleName("demoContentLayout");
        layout.addComponent(hl);
        layout.addComponent(component);
        layout.setComponentAlignment(hl, Alignment.TOP_CENTER);
        layout.setComponentAlignment(component, Alignment.TOP_CENTER);
        setContent(layout);

    }

    private Component newLabel(String s) {
        Label label = new Label(s);
        label.setHeight("50px");
        label.setWidth("150px");
        return label;
    }

}
