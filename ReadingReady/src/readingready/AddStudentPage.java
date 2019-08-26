/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package readingready;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import readingready.dao.StudentDao;

/**
 * FXML Controller class
 *
 * @author Lorenz
 */
public class AddStudentPage implements Initializable {

    @FXML
    private TextField tfASSurname;
    @FXML
    private TextField tfASFirstName;
    @FXML
    private ChoiceBox cbASGrade;
    @FXML
    private Button btnASSubmit;
    @FXML
    private Button btnASPCancel;
    private Stage thisStage = new Stage();
    private HomePage hp;
    private StudentDao sDao = new StudentDao();
    
    
    public AddStudentPage(HomePage hp) throws IOException{
        this.hp = hp;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddStudentPage.fxml"));
        loader.setController(this);
        //thisStage.initStyle(StageStyle.TRANSPARENT);
        Scene scene = new Scene(loader.load());
        scene.getStylesheets().add(getClass().getResource("AddStudentPage.css").toExternalForm());
        
        thisStage.setScene(scene);
        thisStage.initModality(Modality.APPLICATION_MODAL);
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        String[] grade = {"7","8","9","10"};
        cbASGrade.setItems(FXCollections.observableArrayList(grade));
        
        btnASSubmit.setOnAction((ActionEvent e) -> {
            if(tfASFirstName.getText().length()!=0&&tfASSurname.getText().length()!=0&&cbASGrade.getValue()!=null)
                submit();
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error dialog");
                alert.setHeaderText("Please complete the form.");
                alert.setContentText(null);
                alert.showAndWait();
            }
        });
        btnASPCancel.setOnAction(e -> {
            close();
        });;
    }    
    
    public void submit(){
        String lName = tfASSurname.getText().toUpperCase();
        String fName = tfASFirstName.getText().toLowerCase();
        int level = Integer.parseInt(cbASGrade.getValue().toString());
        
        Student student = new Student(lName, fName, level);
        sDao.create(student);
        
        new File("src/ReadingReady/resources/evaluations/" + student.toString()).mkdirs();
        
        hp.updateStudents();
        close();
    }
    
    public void show(){
        thisStage.show();
    }
    
    public void close(){
        thisStage.close();
    }
}
