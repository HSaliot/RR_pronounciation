/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package readingready;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Lorenz
 */
public class Home implements Initializable {
    private Stage thisStage = new Stage();

    public Home() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Home.fxml"));
        loader.setController(this);
        Scene scene = new Scene(loader.load());
        scene.getStylesheets().add(getClass().getResource("Home.css").toExternalForm());
        thisStage.setScene(scene);
        thisStage.setMaximized(true);
        Evaluation evaluation = new Evaluation();
        ResultPage resultPage = new ResultPage(evaluation);
        
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void show(){
        thisStage.show();
    }
}
