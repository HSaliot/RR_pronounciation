/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package readingready;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import readingready.dao.EvaluationDao;
import readingready.dao.ReadingSelectionDao;
import readingready.dao.StudentDao;
import readingready.nodeFactory.Icon;
import readingready.nodeFactory.IconFactory;

/**
 * FXML Controller class
 *
 * @author Lorenz
 * @author Hannah Saliot
 */

public class HomePage implements Initializable {
    
    @FXML
    private VBox vBoxHP;
    @FXML
    private TilePane tpReadingSelections;
    @FXML
    private TilePane tpStudents;
    @FXML
    private TilePane tpEvaluations;
    @FXML
    private Button btnHAddSelection;
    @FXML
    private Button btnHAddStudent;
    @FXML
    private Button btnHAddEvaluation;
    
    private Stage thisStage;
    private List<ReadingSelection> selections;
    private List<Student> students;
    private List<Evaluation> evaluations;
    private final IconFactory iconF = new IconFactory();
    private final ReadingSelectionDao rsDao = new ReadingSelectionDao();
    private final StudentDao sDao = new StudentDao();
    private final EvaluationDao eDao = new EvaluationDao();
  

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
        
        btnHAddSelection.setOnAction(e -> {
            AddReadingSelectionPage addReadingSelectionPage = null;
            try {
                addReadingSelectionPage = new AddReadingSelectionPage(this);
            } catch (IOException ex) {
                Logger.getLogger(HomePage.class.getName()).log(Level.SEVERE, null, ex);
            }
            addReadingSelectionPage.show();
        });
        
        btnHAddStudent.setOnAction(e -> {
            AddReadingSelectionPage addReadingSelectionPage = null;
            try {
                addReadingSelectionPage = new AddReadingSelectionPage(this);
            } catch (IOException ex) {
                Logger.getLogger(HomePage.class.getName()).log(Level.SEVERE, null, ex);
            }
            addReadingSelectionPage.show();
        });
        
        btnHAddEvaluation.setOnAction(e -> {
            ReadingEvaluationPage addReadingEvaluationPage = null;
            try {
                addReadingEvaluationPage = new ReadingEvaluationPage(this);
            } catch (IOException ex) {
                Logger.getLogger(HomePage.class.getName()).log(Level.SEVERE, null, ex);
            }
            addReadingEvaluationPage.show();
        });
        
        updateSelections();
        updateStudents();
        updateEvaluations();
    }    

    private void toFocus(Stage thisStage, ReadingSelection selection) throws IOException {
        ReadingSelectionPage selectionPage = new ReadingSelectionPage(thisStage, selection);
    }
    
    private void toFocus(Stage thisStage, Student student) throws IOException {
        ;
    }
    
    private void toFocus(Stage thisStage, Evaluation evaluation) throws IOException {
        ;
    }
    
    public void updateSelections(){
        tpReadingSelections.getChildren().clear();
        selections = rsDao.findAll(ReadingSelection.class);
        selections.forEach(selection -> {
            Button button = iconF.createButtonL(Icon.BOOK, "  " + selection.getTitle(), "");
            button.getStyleClass().add("btnClear");
            button.setOnAction(e -> {
                try {
                    toFocus(thisStage, selection);
                } catch (IOException ex) {
                    Logger.getLogger(HomePage.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            tpReadingSelections.getChildren().add(button);
        });
    }
    
    public void updateStudents(){
        tpStudents.getChildren().clear();
        students = sDao.findAll(Student.class);
        students.forEach(student -> {
            Button button = iconF.createButtonL(Icon.USER, "  " + student.getLName() + ", " + student.getFnameetc(), "");
            button.getStyleClass().add("btnClear");
            button.setOnAction(e -> {
                try {
                    toFocus(thisStage, student);
                } catch (IOException ex) {
                    Logger.getLogger(HomePage.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            tpStudents.getChildren().add(button);
        });
    }
    
    public void updateEvaluations(){
        tpEvaluations.getChildren().clear();
        evaluations = eDao.findAll(Evaluation.class);
        evaluations.forEach(evaluation -> {
            Button button = iconF.createButtonL(Icon.CHECK_DOUBLE, "  Eval", "");
            button.getStyleClass().add("btnClear");
            button.setOnAction(e -> {
                try {
                    toFocus(thisStage, evaluation);
                } catch (IOException ex) {
                    Logger.getLogger(HomePage.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            tpEvaluations.getChildren().add(button);
        });
    }

}
