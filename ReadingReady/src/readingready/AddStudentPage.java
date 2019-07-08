/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package readingready;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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
    
    private Stage thisStage = new Stage();
    
    public AddStudentPage() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddStudentPage.fxml"));
        loader.setController(this);
        //thisStage.initStyle(StageStyle.TRANSPARENT);
        thisStage.setScene(new Scene(loader.load()));
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
        });
    }    
    
    public void submit(){
        System.out.println(
        tfASSurname.getText()+" "+
        tfASFirstName.getText()+" "+
        cbASGrade.getValue()
        );
        close();
    }
    
    public void show(){
        thisStage.show();
    }
    
    public void close(){
        thisStage.close();
    }
}
