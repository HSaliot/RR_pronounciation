/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package readingready;

import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Hannah Saliot
 */
public class Pronounciation extends Application {
    
  public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("GUI/general.fxml"));
        stage.setTitle("Reading Ready");
        stage.setScene(new Scene(root));
        stage.setMaximized(true);
        stage.show();
    }

    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
