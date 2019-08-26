package readingready;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
import javafx.stage.Screen;
import javafx.stage.Stage;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

/**
 * FXML Controller class
 *
 * @author lorenz
 */
public class CompareEvaluationPage implements Initializable {

    @FXML 
    private StackPane stackPane;
    @FXML
    private Button btnBack;
    @FXML 
    private Label lblEval1;
    @FXML 
    private Label lblEval2;
    @FXML 
    private Label lblStudent1;
    @FXML 
    private Label lblStudent2;
    @FXML 
    private ListView listViewEval1;
    @FXML 
    private ListView listViewEval2;
    
    private Stage thisStage;
    private ArrayList<Evaluation> evaluations = new ArrayList<>();
    
    public CompareEvaluationPage (Stage stage, ArrayList<Evaluation> evaluations) throws IOException{
        thisStage = stage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CompareEvaluationPage.fxml"));
        loader.setController(this);
        Scene scene = new Scene(loader.load());
        scene.getStylesheets().add(getClass().getResource("CompareEvaluationPage.css").toExternalForm());
        thisStage = stage;
        thisStage.setScene(scene);
        thisStage.setMaximized(true);        
        
        this.evaluations = evaluations;
        setStaticUI();
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
    public void setStaticUI() throws IOException{
        lblEval1.setText(evaluations.get(0).getLabel());
        lblEval2.setText(evaluations.get(1).getLabel());
        lblStudent1.setText(evaluations.get(0).getStudent().getLName()+", "+evaluations.get(0).getStudent().getFnameetc());
        lblStudent2.setText(evaluations.get(1).getStudent().getLName()+", "+evaluations.get(1).getStudent().getFnameetc());
        setPassage(evaluations.get(0),1);
        setPassage(evaluations.get(1),2);
    }
    
    public void setPassage(Evaluation eval,int num) throws FileNotFoundException, IOException{
        String folder = eval.getFolder() + String.format("%02d/", eval.getId()); // folder <-- "src/resources/evaluations/<user.toString()>/"
        BufferedReader br = new BufferedReader(new FileReader(new File(folder + "resultNormal.txt"))); 
        String line;
        ObservableList<String> files = null;
        List<String> list = new ArrayList<>();
       
        while((line = br.readLine()) != null){
            if(line.startsWith("***")){
                list.add(line.replace("***", ""));
            }
        }
        files = FXCollections.observableArrayList(list);
        if(num == 1)
            listViewEval1.setItems(files);
        else
            listViewEval2.setItems(files);
    }
}
