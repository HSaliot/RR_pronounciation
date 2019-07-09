/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package readingready;

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
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import readingready.dao.EvaluationDao;

/**
 * FXML Controller class
 *
 * @author Lorenz
 */
public class ReadingEvaluationPage implements Initializable {
    @FXML
    private ChoiceBox cbSelection;
    @FXML
    private Button uploadButton;
    @FXML
    private ChoiceBox cbStudent;
    @FXML
    private Button cancelButton;
    @FXML
    private Button submitButton;
    @FXML
    private ListView<File> fileListView;
    @FXML
    private TextField tfLabel;
    @FXML
    private RadioButton rbSphinx4;
    @FXML
    private RadioButton rbPocketSphinx;

    private final FileChooser fileChooser = new FileChooser();
    private ObservableList<File> files = null;
    private Stage thisStage = new Stage();
    
    private List<File> list;
    private ArrayList<String> filenames = new ArrayList<>();
    private HomePage hp;
    private boolean usePocketSphinx = false;
    private EvaluationDao eDao = new EvaluationDao();
    private String dir;
    
    public ReadingEvaluationPage(HomePage hp) throws IOException{
        this.hp = hp;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ReadingEvaluationPage.fxml"));
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
        
        cbSelection.getItems().addAll(hp.getSelections());
        cbStudent.getItems().addAll(hp.getStudents());
        
        rbSphinx4.setSelected(true);
        rbPocketSphinx.setSelected(false);
        
        if(OSCheck.isWindows)
            rbPocketSphinx.setDisable(true);
        
        rbSphinx4.setOnAction(e -> {
            rbPocketSphinx.setSelected(false);
            usePocketSphinx = false;
        });
        
        rbPocketSphinx.setOnAction(e -> {
            rbSphinx4.setSelected(false);
            usePocketSphinx = true;
        });
        
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
        
        cancelButton.setOnAction(e -> {
            close();
        });
        
        submitButton.setOnAction(e -> {
            try {
                submit();
            } catch (IOException ex) {
                Logger.getLogger(ReadingEvaluationPage.class.getName()).log(Level.SEVERE, null, ex);
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
        Evaluation evaluation = eDao.findNewest();
        
        dir = "src/readingready/resources/evaluations/" + evaluation.getStudent().toString() + "/" 
                        + String.format("%02d", evaluation.getId());
        new File(dir + "/wavs").mkdirs();
        
        if (fileListView.getItems().size() != 0) {
            for ( int i =0; i<fileListView.getItems().size();i++) {
                current = fileListView.getItems().get(i);
                String filename = "src/readingready/resources/evaluations/" + evaluation.getStudent().toString() + "/" 
                        + String.format("%02d/wavs/%02d.wav", evaluation.getId(), i);
                File copied = new File(filename);
                filenames.add(filename);
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
        ReadingSelection selection = (ReadingSelection) cbSelection.getValue();
        Student student = (Student) cbStudent.getValue();
        String label = tfLabel.getText();
        
        
        Evaluation evaluation = new Evaluation(student, selection, label);
        eDao.create(evaluation);
        
        saveFileToProject();
        
        for(int i=0; i < filenames.size(); i++){
            if(usePocketSphinx) {
                Pocketsphinx ps = new Pocketsphinx();
                ps.evaluateNormal(dir, String.format("%02d", i));
                ps.evaluateForced(dir, String.format("%02d", i), selection.getTitle());
            }
            else {
                Sphinx s = new Sphinx();
                s.evaluateNormal(dir, String.format("%02d", i));
                s.evaluateForced(dir, i, selection.getTitle());
            }
        }
        
        hp.updateEvaluations();
        close();
        
    }
}
