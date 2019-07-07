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
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Lorenz
 */

public class HomePage implements Initializable {
    @FXML
    private Hyperlink hlHDarkChocolate;
    private Stage thisStage;

    public HomePage(Stage stage) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("HomePage.fxml"));
        loader.setController(this);
        Scene scene = new Scene(loader.load());
        scene.getStylesheets().add(getClass().getResource("HomePage.css").toExternalForm());
        
        thisStage = stage;
        thisStage.setScene(scene);
        //Evaluation evaluation = new Evaluation();
        //ResultPage resultPage = new ResultPage(evaluation);
        
    }
    /**
     * Initializes the controller class.
     */
    @Override
    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        hlHDarkChocolate.setOnAction((ActionEvent e) -> {
            ReadingSelectionPage readingSelection = null;
            try {
                
                readingSelection = new ReadingSelectionPage("Dark Chocolate");
            } catch (IOException ex) {
                Logger.getLogger(LoginPage.class.getName()).log(Level.SEVERE, null, ex);
            }
            readingSelection.show();
        });
    }    
    
    public void show(){
        thisStage.show();
    }
}
