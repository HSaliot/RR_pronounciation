/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package readingready;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;
import javafx.stage.Screen;
import javafx.stage.Stage;
import readingready.dao.EvaluationDao;
import readingready.nodeFactory.Icon;

/**
 * FXML Controller class
 *
 * @author lorenz
 */
public class StudentPage implements Initializable {
    
    @FXML
    private StackPane stackPane;
    @FXML
    private Label lSPLName;
    @FXML
    private Label lSPFName;
    @FXML
    private Label lSPGrade;
    @FXML
    private ListView<Hyperlink> evaluationListView;
    @FXML
    private Button btnBack;

    
    private Stage thisStage;
    private List<Evaluation> evaluations;
    private final EvaluationDao eDao = new EvaluationDao();
    private ObservableList<Hyperlink> hyperlinks = null;
    private Student student;
    
    public StudentPage(Stage stage, Student student) throws IOException{
        thisStage = stage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("StudentPage.fxml"));
        loader.setController(this);
        Scene scene = new Scene(loader.load());
        scene.getStylesheets().add(getClass().getResource("StudentPage.css").toExternalForm());
        thisStage = stage;
        thisStage.setScene(scene);
        thisStage.setMaximized(true);        
        
        this.student = student;
        setStaticUI();
        updateEvaluations();
    }
    /**
     * Initializes the controller class.
     */
    @Override
    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        stackPane.setPrefHeight(Screen.getPrimary().getBounds().getHeight());
        stackPane.setPrefWidth(Screen.getPrimary().getBounds().getWidth());
        
        btnBack.setOnAction((ActionEvent e) -> {
            try {
                HomePage home = new HomePage(thisStage);
            } catch (IOException ex) {
                Logger.getLogger(ResultPage.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }    

    @FXML
    public void setStaticUI(){
        lSPLName.setText(student.getLName()+", ");
        lSPFName.setText(student.getFnameetc());
        lSPGrade.setText(String.valueOf(student.getLevel()));

    }
    
    public void updateEvaluations(){
        //tpEvaluations.getChildren().clear();
        evaluations = eDao.findAll(Evaluation.class);
        List<Hyperlink> hpEvaluations = new ArrayList<>();
        evaluations.forEach(evaluation -> {
            if(evaluation.getStudent().getId().equals(student.getId())){
                Hyperlink temp;
                temp = new Hyperlink(evaluation.getLabel());
                temp.setOnAction((ActionEvent e) -> {
                    try {
                        ResultPage resultPage = new ResultPage(thisStage, evaluation);
                    } catch (IOException ex) {
                        Logger.getLogger(StudentPage.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
                hpEvaluations.add(temp);
            }
        });
        hyperlinks = FXCollections.observableArrayList(hpEvaluations);
        evaluationListView.setItems(hyperlinks);
    }
}
