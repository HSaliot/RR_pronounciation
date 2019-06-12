/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package readingready;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

/**
 *
 * @author Hannah Saliot
 */
public class ResultPage{
    @FXML
    private HBox leftHB;
    
    @FXML
    private VBox rightTopVB;
    
    @FXML
    private HBox rightBottomHB;
    
    private Stage thisStage;
    private TextFlow textSelection;
    
    private Evaluation evaluation;
    
    public ResultPage(Evaluation evaluation) throws IOException{
        this.evaluation = evaluation;
        thisStage = new Stage();
        thisStage.setMaximized(true);
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ResultPage.fxml"));
        loader.setController(this);
        
        thisStage.setScene(new Scene(loader.load()));
    }
    
    public void show(){
        thisStage.showAndWait();
    }
    
    @FXML
    private void initialize(){
        Label labelSelection = new Label(evaluation.getSelection() + "\n");
        Label labelStudent = new Label(evaluation.getStudent() + "\n");
        Label labelDateRecorded = new Label(evaluation.getDateRecorded() + "\n");
        
        rightTopVB.getChildren().addAll(labelSelection, labelStudent, labelDateRecorded);
        
    }

    
    
    
    
    
    
    
    
}
