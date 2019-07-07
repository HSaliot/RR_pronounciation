/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package readingready.nodeFactory;

import java.io.InputStream;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

/**
 *
 * @author Hannah Saliot
 */
public class ButtonFactory {
    
    private InputStream is = ButtonFactory.class.getResourceAsStream("fontawesome-webfont.ttf");
    private Font font = Font.loadFont(is, 25);
    
    public Button createIconButton(String iconName, String text)
    {
        Label icon = new Label(iconName);
        icon.setFont(font);
        
        Button button = new Button(text);
        button.setGraphic(icon);
        return button;
    }
}
