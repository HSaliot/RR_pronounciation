/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package readingready;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Lorenz
 */

public class HomePage implements Initializable {
    
    @FXML
    private Hyperlink hlHDarkChocolate;
    
    @FXML
    private VBox vBoxHP;
    
    private Stage thisStage;

    public HomePage(Stage stage, User user) throws IOException{
        thisStage = stage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("HomePage.fxml"));
        loader.setController(this);
        Scene scene = new Scene(loader.load());
        scene.getStylesheets().add(getClass().getResource("HomePage.css").toExternalForm());
        
        thisStage = stage;
        thisStage.setScene(scene);
        thisStage.setMaximized(true);        
    }
    /**
     * Initializes the controller class.
     */
    @Override
    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        
        vBoxHP.setPrefHeight(Screen.getPrimary().getBounds().getHeight());
        vBoxHP.setPrefWidth(Screen.getPrimary().getBounds().getWidth());
        hlHDarkChocolate.setOnAction((ActionEvent e) -> {
            ReadingSelectionPage readingSelection = null;
            try {
                
                readingSelection = new ReadingSelectionPage(thisStage,"Dark Chocolate");
            } catch (IOException ex) {
                Logger.getLogger(LoginPage.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }    

}
