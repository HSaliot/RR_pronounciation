/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package readingready;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import static java.util.Collections.list;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Lorenz
 */
public class AddReadingSelectionPage implements Initializable {
    
    @FXML
    private TextField tfARSTitle;
    
    @FXML
    private RadioButton rbARSFile;
    
    @FXML
    private RadioButton rbARSType;
    
    @FXML
    private Button btnARSChooseFile;
    
    @FXML
    private Button btnARSSave;
    
    @FXML
    private TextArea taARSPassage;
    
    @FXML
    private HBox hBoxARSFile;
    
    @FXML
    private HBox hBoxARSType;
                
    @FXML
    private Label lARSFilename;
    
    private Stage thisStage = new Stage();
    
    private final FileChooser fileChooser = new FileChooser();
    
    private File file;


    public AddReadingSelectionPage() throws IOException {
        thisStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddReadingSelectionPage.fxml"));
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
        hBoxARSFile.setDisable(true);
        hBoxARSType.setDisable(true);
        rbARSFile.setOnAction(e -> {
            rbARSType.setSelected(false);
            hBoxARSFile.setDisable(false);
            hBoxARSType.setDisable(true);
        });
        rbARSType.setOnAction(e -> {
            rbARSFile.setSelected(false);
            hBoxARSFile.setDisable(true);
            hBoxARSType.setDisable(false);
        });;
        btnARSChooseFile.setOnAction(e -> {
            file = fileChooser.showOpenDialog(thisStage);
            if(file!=null)
                lARSFilename.setText(file.getName());
        });;
        btnARSSave.setOnAction(e -> {
            if(tfARSTitle.getText().length()>0){
                if(rbARSFile.isSelected()&&file!=null){
                    try {
                        saveFileToProject();
                    } catch (IOException ex) {
                        Logger.getLogger(AddReadingSelectionPage.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }else if(rbARSType.isSelected()&&taARSPassage.getText().length()>0){
                    try {
                        saveTextToFile();
                    } catch (IOException ex) {
                        Logger.getLogger(AddReadingSelectionPage.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                thisStage.close();
            }
        });;
    }    

    void show() {
        thisStage.showAndWait();
    }
    private void saveFileToProject() throws IOException {
        File copied = new File("src/readingready/resources/"+tfARSTitle.getText()+".txt");
                try (
                  InputStream in = new BufferedInputStream(
                    new FileInputStream(file));
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
    private void saveTextToFile() throws IOException{
        BufferedWriter writer = new BufferedWriter(
                                new FileWriter("src/readingready/resources/"+tfARSTitle.getText()+".txt")
                            ); 
        writer.write(taARSPassage.getText());
        writer.close();
    }

    
}
