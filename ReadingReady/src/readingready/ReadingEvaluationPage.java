/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package readingready;

import java.awt.Desktop;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Lorenz
 */
public class ReadingEvaluationPage implements Initializable {
    @FXML
    private ChoiceBox readingSelection;
    @FXML
    private Button uploadButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Button submitButton;
    @FXML
    private ListView<File> fileListView;
    @FXML
    private TextField lastName;
    @FXML
    private TextField firstName;

    private final FileChooser fileChooser = new FileChooser();
    private ObservableList<File> files = null;
    private Stage thisStage;
    private String[] selections = {"Dark Chocolate", "Sneezing", "Dust", "Pain", "Diving"};
    private List<File> list;
    
    public ReadingEvaluationPage() throws IOException{
        
        thisStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ReadingEvaluation.fxml"));
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
        
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("WAV files (*.wav)", "*.wav");
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.setTitle("Upload a wav file/s");

        lastName.setPromptText("Last Name");
        firstName.setPromptText("First Name");
        readingSelection.getItems().addAll(selections);
        readingSelection.setValue(readingSelection.getItems().get(0));
        uploadButton.setOnAction(
            new EventHandler<ActionEvent>() {
                @Override
                public void handle(final ActionEvent e) {
                    list = fileChooser.showOpenMultipleDialog(thisStage);
                    if (list != null) {
                        for (File file : list) {
                        }
                        files = FXCollections.observableArrayList(list);
                        fileListView.setItems(files);
                        
                    }
                }
            });
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                close();
            }
        });
        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                try {
                    submit();
                } catch (IOException ex) {
                    Logger.getLogger(ReadingEvaluationPage.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }    
    public void show() {
        thisStage.show();
    }
    public void close(){
        thisStage.close();
    }
    private void saveFileToProject() throws IOException {
        File current;
        if (fileListView.getItems().size() != 0) {
            for ( int i =0; i<fileListView.getItems().size();i++) {
                current = fileListView.getItems().get(i);
                File copied = new File("src/readingready/resources/"+current.getName());
                try (
                  InputStream in = new BufferedInputStream(
                    new FileInputStream(current));
                  OutputStream out = new BufferedOutputStream(
                    new FileOutputStream(copied))) {

                    byte[] buffer = new byte[1024];
                    int lengthRead;
                    while ((lengthRead = in.read(buffer)) > 0) {
                        out.write(buffer, 0, lengthRead);
                        out.flush();
                    }
                }
            }
        }
    }
    public void submit() throws IOException{
        
        System.out.println(readingSelection.getValue());
        System.out.println(getLastName()+", "+getFirstName());
        saveFileToProject();
    }
    private String getFirstName(){
        return firstName.getText();
    }
    private String getLastName(){
        return lastName.getText();
    }
}
