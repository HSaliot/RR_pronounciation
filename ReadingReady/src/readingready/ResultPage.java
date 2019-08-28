/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this hLinklate file, choose Tools | Templates
 * and open the hLinklate in the editor.
 */
package readingready;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import readingready.nodeFactory.Icon;
import readingready.nodeFactory.IconFactory;

/**
 *
 * @author Hannah Saliot
 */
public class ResultPage implements Initializable {
    @FXML    private StackPane stackPane;
    @FXML    private Button btnBack;

    @FXML    private Label labelID;
    @FXML    private Label labelSelection;
    @FXML    private Label labelStudent;
    @FXML    private Label labelDateRecorded;
    
    @FXML    private Label lRSWord;
    @FXML    private Button playButton;
    @FXML    private Button stopButton;
    @FXML    private Button pauseButton;
    @FXML    private Label lblScore;

    @FXML   private TextFlow tfReadingsNormal;
    @FXML   private TextFlow tfReadingsForced;
    @FXML   private ProgressBar progress;
    
    private Stage thisStage;
    private Evaluation evaluation;
    private String folder;
    private Clip clip;
    private long pauseTime;
    private final IconFactory iconF = new IconFactory();
    
    private List<Utterance> utterancesNormal = new ArrayList<>();
    private List<Utterance> utterancesForced = new ArrayList<>();
    private Map<Utterance, Integer> wordSentenceMap = new HashMap<>();
    private List<String> clipsDir = new ArrayList<>();
    private String currentClipDir;
    private boolean sphinxUsed;
    
    public ResultPage(Stage stage, Evaluation evaluation) throws IOException{
        thisStage = stage;
        this.evaluation = evaluation;
        this.sphinxUsed = evaluation.isSphinxUsed();
        folder = evaluation.getFolder() + String.format("%02d/", evaluation.getId()); // folder <-- "src/resources/evaluations/<user.toString()>/"
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ResultPage.fxml"));
        loader.setController(this);
        Scene scene = new Scene(loader.load());
        scene.getStylesheets().add(getClass().getResource("ResultPage.css").toExternalForm());
        thisStage.setScene(scene);

    }
    
    @Override
    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        setStaticUI();
        try {
            setPassageNormal(); 
            setPassageForced();
        } catch (IOException ex) {
            Logger.getLogger(ResultPage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(ResultPage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(ResultPage.class.getName()).log(Level.SEVERE, null, ex);
        }
        playButton.setOnAction((ActionEvent e) -> {
            try {
                playWavFile();
            } catch (LineUnavailableException ex) {
            } catch (IOException ex) {
            } catch (UnsupportedAudioFileException ex) {
            }
            
        });
        stopButton.setOnAction((ActionEvent e) -> {
            stopWavFile();          
        });
        pauseButton.setOnAction((ActionEvent e) -> {
            pauseWavFile();         
        });
    }
    
    private void playWavFile() throws LineUnavailableException, IOException, UnsupportedAudioFileException{
        if(clip!=null)
            clip.stop();
        pauseButton.setDisable(false);
        stopButton.setDisable(false);
        
        if(pauseTime == 0){
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(currentClipDir).getAbsoluteFile()); 
            clip = AudioSystem.getClip(); 
            clip.open(audioInputStream); ; 
            clip.setLoopPoints(0, -1);
            clip.start();
        }
        else
        {
            clip.setMicrosecondPosition(pauseTime);
            clip.start();
        }
        
        Task task = taskCreator(clip.getMicrosecondLength()/1000000,pauseTime/1000000);
        progress.progressProperty().unbind();
        progress.progressProperty().bind(task.progressProperty());
        new Thread(task).start();
    }
    private void stopWavFile(){
        pauseButton.setDisable(true);
        stopButton.setDisable(true);
        pauseTime = 0;
        clip.stop();
        Task task = taskCreator(1,0.9);
        progress.progressProperty().unbind();
        progress.progressProperty().bind(task.progressProperty());
        new Thread(task).start();
    }
    private void pauseWavFile(){
        pauseButton.setDisable(true);
        pauseTime = clip.getMicrosecondPosition();
        clip.stop();
        Task task = taskCreator(1,0.9);
        progress.progressProperty().unbind();
        progress.progressProperty().bind(task.progressProperty());
        new Thread(task).start();
    }

    public void setPassageNormal() throws FileNotFoundException, IOException, UnsupportedAudioFileException, LineUnavailableException{
        BufferedReader br = new BufferedReader(new FileReader(new File(folder + "resultNormal.txt"))); 
        String line, word, pronunciation;
        String[] strArray;
        Hyperlink hLink;
        double ascr;
        AudioInputStream audioInputStream;
        List<Hyperlink> readings = new ArrayList<>();
        int idx = 0, sentence = -1;
        
        while((line = br.readLine()) != null){
            if(line.startsWith("***")){
                sentence++;
                String temp = folder + String.format("wavs/%02d.wav", sentence);
                clipsDir.add(temp);
            }
            else {
                strArray = line.split(" ");
                word = strArray[0];
                ascr = Double.parseDouble(strArray[3]);
                pronunciation = (sphinxUsed) ? "temp" : null;//strArray[4] : null;

                hLink = new Hyperlink(word);
                readings.add(hLink);

                Utterance utterance = new Utterance(
                        word,                               //word
                        Integer.parseInt(strArray[1]),      //start
                        Integer.parseInt(strArray[2]),      //end
                        ascr,                               //acoustic score
                        pronunciation);                     //pronunciation       
                utterancesNormal.add(utterance);

                int index = idx;
                int sIndex = sentence;
                hLink.setOnAction((ActionEvent e) -> {
                    selectedWordNormal(index);
                    setCurrWav(sIndex);
                });

                idx++;
            }
        }
        
        ObservableList list = tfReadingsNormal.getChildren(); 
        list.addAll(readings);
        setCurrWav(0);
    }
        
    public void setPassageForced() throws FileNotFoundException, IOException, UnsupportedAudioFileException, LineUnavailableException{
        BufferedReader br = new BufferedReader(new FileReader(new File(folder + "resultForced.txt"))); 
        String line, word, pronunciation;
        String[] strArray;
        Hyperlink hLink;
        double ascr;
        AudioInputStream audioInputStream;
        List<Hyperlink> readings = new ArrayList<>();
        int idx = 0, sentence = -1;
        while((line = br.readLine()) != null){
            if(line.startsWith("***"))
                sentence++;
            else {
                strArray = line.split(" ");
                word = strArray[0];
                ascr = Double.parseDouble(strArray[3]);
                pronunciation = (sphinxUsed) ? strArray[4] : null;

                hLink = new Hyperlink(word);
                readings.add(hLink);

                Utterance utterance = new Utterance(
                        word,                               //word
                        Integer.parseInt(strArray[1]),      //start
                        Integer.parseInt(strArray[2]),      //end
                        ascr,                               //acoustic score
                        pronunciation);                     //pronunciation       
                utterancesForced.add(utterance);

                int index = idx;
                int sIndex = sentence;
                hLink.setOnAction((ActionEvent e) -> {
                    selectedWordForced(index);
                    setCurrWav(sIndex);
                });

                idx++;
            }
        }
        
        ObservableList list = tfReadingsForced.getChildren(); 
        list.addAll(readings);
    }
    
    private void selectedWordNormal(int id){
        Utterance sWord = utterancesNormal.get(id);
        lRSWord.setText(sWord.getWord());
        lblScore.setText(Double.toString(sWord.getAscr()));
    }
    
    private void selectedWordForced(int id){
        Utterance sWord = utterancesForced.get(id);
        lRSWord.setText(sWord.getWord());
        lblScore.setText(Double.toString(sWord.getAscr()));
    }

    private void setStaticUI() {
        stackPane.setPrefSize(Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getHeight());
        tfReadingsNormal.setPrefWidth(Screen.getPrimary().getBounds().getWidth()/2);
        tfReadingsForced.setPrefWidth(Screen.getPrimary().getBounds().getWidth()/2);

        labelSelection.setText(evaluation.getSelection().getTitle());
        labelStudent.setText(evaluation.getStudent().toString());
        labelDateRecorded.setText(evaluation.getDatedone().toString());
        labelID.setText(evaluation.getLabel());        
        
        btnBack.setOnAction((ActionEvent e) -> {
            try {
                HomePage home = new HomePage(thisStage);
            } catch (IOException ex) {
                Logger.getLogger(ResultPage.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        tfReadingsNormal.setTextAlignment(TextAlignment.JUSTIFY); 
        tfReadingsForced.setTextAlignment(TextAlignment.JUSTIFY); 
        
        pauseButton.setDisable(true);
        stopButton.setDisable(true);

    }

    private void setCurrWav(int sentence) {
        pauseButton.setDisable(true);
        stopButton.setDisable(true);
        currentClipDir = clipsDir.get(sentence);
        pauseTime = 0;
    }
    
    private Task taskCreator(long seconds,double start){
        return new Task() {
            @Override
            protected Object call() throws Exception {
                for(double i=start; i<seconds;i++){
                 Thread.sleep(1000);
                 updateProgress(i+1, seconds);
                }
                return true;
            }
        };
    }

}