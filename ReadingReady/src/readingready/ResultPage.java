/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package readingready;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Hannah Saliot
 */
public class ResultPage{
    
    @FXML
    private MenuItem add;
    @FXML
    private HBox leftHB;
    @FXML
    private VBox rightTopVB;
    @FXML
    private Label labelSelection;
    @FXML
    private Label labelStudent;
    @FXML
    private Label labelDateRecorded;
    @FXML
    private HBox rightBottomHB;
    
    private final Stage thisStage = new Stage();
    private final Evaluation evaluation;
    
    public ResultPage(Evaluation evaluation) throws IOException{
        this.evaluation = evaluation;
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ResultPage.fxml"));
        loader.setController(this);
        
        thisStage.setScene(new Scene(loader.load()));
        thisStage.setMaximized(true);
    }
    
    public void show(){
        thisStage.showAndWait();
    }
    
    @FXML
    private void initialize() throws IOException{
        labelSelection.setText(evaluation.getSelection());
        labelStudent.setText(evaluation.getStudent());
        labelDateRecorded.setText(evaluation.getDateRecorded());
        
        add.setOnAction((ActionEvent e) -> {
            openAddWavPage();
        });
        /*
        evaluation.getEvaluatedSentences().forEach(c -> {
            c.getWords().forEach(i -> {
                this
            })
        });
        */
        
    }

    private void openAddWavPage() {
        AddWavPage addWavPage;
        try {
            addWavPage = new AddWavPage();
            addWavPage.show();
        } catch (IOException ex) {
            ;
        }
        
    }
}
