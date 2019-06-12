/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package readingready;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.text.TextFlow;

/**
 *
 * @author Hannah Saliot
 */
public class ResultPage{
    @FXML
    private HBox leftHB;
    
    @FXML
    private HBox rightTopHB;
    
    @FXML
    private HBox rightBottomHB;
    
    private Stage thisStage;
    private TextFlow textSelection;
    
    public ResultPage() throws IOException{
        thisStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ResultPage.fxml"));
        loader.setController(this);
        thisStage.setScene(new Scene(loader.load()));
    }
    
    public void show(){
        thisStage.showAndWait();
    }
    
    @FXML
    private void initialize(){
        TextFlow textSelectionPane = new TextFlow();
        
        
    }

    
    
    
    
    
    
    
    
}
