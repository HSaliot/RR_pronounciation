/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package readingready;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author Hannah Saliot
 */
public class Pronounciation extends Application {
    
    
  public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GUI/general.fxml"));
        Parent root = loader.load();
        
        stage.setTitle("Reading Ready");
        stage.setMaximized(true);
        stage.setScene(new Scene(root));
        
        stage.show();
    }

    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    public static String readFileAsString(String fileName)throws Exception 
    { 
        String body = ""; 
        body = new String(Files.readAllBytes(Paths.get(fileName))); 
        return body; 
    } 
    
    
    
}
