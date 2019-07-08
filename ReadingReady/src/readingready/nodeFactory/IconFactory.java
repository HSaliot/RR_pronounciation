/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package readingready.nodeFactory;

import java.io.InputStream;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 *
 * @author Hannah Saliot
 */
public class IconFactory {
    
    public Button createButtonS(String iconName, String text, String color)
    {
        Button button = new Button(text);
        button.setGraphic(createIconS (iconName, color));
        return button;
    }
    
    public Button createButtonM(String iconName, String text, String color)
    {
        Button button = new Button(text);
        button.setGraphic(createIconM (iconName, color));
        return button;
    }
    
    public Button createButtonL(String iconName, String text, String color)
    {
        Button button = new Button(text);
        button.setGraphic(createIconL (iconName, color));
        return button;
    }
    
    
    public Label createIconS(String iconName, String color) {   
        Label icon = new Label(iconName);
        InputStream is = IconFactory.class.getResourceAsStream("fontawesome-webfont.ttf");
        Font font = Font.loadFont(is, 18);
        icon.setFont(font);
        if(!color.isEmpty())
            icon.setTextFill(Color.web(color));
        return icon;
    }
    
    public Label createIconM(String iconName, String color)
    {
        Label icon = new Label(iconName);
        InputStream is = IconFactory.class.getResourceAsStream("fontawesome-webfont.ttf");
        Font font = Font.loadFont(is, 20);
        icon.setFont(font);
        if(!color.isEmpty())
            icon.setTextFill(Color.web(color));
        return icon;
    }
    
    public Label createIconL(String iconName, String color)
    {
        Label icon = new Label(iconName);
        InputStream is = IconFactory.class.getResourceAsStream("fontawesome-webfont.ttf");
        Font font = Font.loadFont(is, 25);
        icon.setFont(font);
        if(!color.isEmpty())
            icon.setTextFill(Color.web(color));
        return icon;
    }
    
    
    
    
}
