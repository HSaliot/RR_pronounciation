/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package readingready;

import edu.cmu.sphinx.api.SpeechResult;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Lorenz
 */
public class AddWavPage {
    private File selectedFile;
    @FXML private Text fileName;
    @FXML private TextField name;
    @FXML private Button uploadFile;
    @FXML private Button submit;
    @FXML private Button back;
    private Stage thisStage;
    private double yOffset = 0;
    private double xOffset = 0;

    public AddWavPage() throws IOException{
        
        thisStage = new Stage();
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddWavPage.fxml"));
        loader.setController(this);
        thisStage.initStyle(StageStyle.TRANSPARENT);
        thisStage.setScene(new Scene(loader.load()));
        thisStage.initModality(Modality.APPLICATION_MODAL);

        
    }   
    
    public void openFile() throws IOException {
        FileChooser fc = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("WAV files (*.wav)", "*.wav");
        fc.getExtensionFilters().add(extFilter);
        fc.setTitle("Attach a file");
        selectedFile = fc.showOpenDialog(null);
        if(selectedFile!=null)
                show(selectedFile.getName());
    }
    public void submit() throws FileNotFoundException, IOException {
    	if (selectedFile != null) {
            File copied = new File("src/readingready/resources/"+selectedFile.getName());
            try (
              InputStream in = new BufferedInputStream(
                new FileInputStream(selectedFile));
              OutputStream out = new BufferedOutputStream(
                new FileOutputStream(copied))) {
          
                byte[] buffer = new byte[1024];
                int lengthRead;
                while ((lengthRead = in.read(buffer)) > 0) {
                    out.write(buffer, 0, lengthRead);
                    out.flush();
                }
            }    
            
            
            Sphinx sphinx = new Sphinx();
            List<SpeechResult> sentences = new ArrayList<>();
            sentences.add(sphinx.getSpeechResult("src/readingready/resources/"+"aron.wav"));
            ResultPage result = new ResultPage(new Evaluation());
            result.addSentences();
            close();
            result.show();
            
            
        }
    }
    @FXML
    private void show(String filename) {
    	this.fileName.setText(filename);
    }
    public void setStage(Stage stage) {
        this.thisStage = stage;
    }

    public void close() {
        thisStage.close();
    }
    
    public void show() {
        thisStage.show();
    }
    
    @FXML
    private void initialize(){
        
        uploadFile.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                try {
                    openFile();
                } catch (IOException ex) {
                    Logger.getLogger(AddWavPage.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        submit.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                try {
                    submit();
                } catch (IOException ex) {
                    Logger.getLogger(AddWavPage.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        back.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                close();
            }
        });
    }
    
    
    
}
