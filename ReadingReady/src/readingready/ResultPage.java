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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tab;
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
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author Hannah Saliot
 */
public class ResultPage implements Initializable {
    @FXML    private VBox vBoxRPParent;
    @FXML    private MenuItem btnBack;

    @FXML    private Label labelID;
    @FXML    private Label labelSelection;
    @FXML    private Label labelStudent;
    @FXML    private Label labelDateRecorded;
    
    @FXML    private TextFlow tfReadings;
    @FXML    private Label lRSWord;
    @FXML    private Button playButton;
    @FXML    private Button stopButton;
    @FXML    private ProgressBar progressBar;

    @FXML   private Tab tabNormal;
    @FXML   private Tab tabForced;
    @FXML   private TextFlow tfReadingsNormal;
    @FXML   private TextFlow tfReadingsForced;
    
    private Stage thisStage;
    private Evaluation evaluation;
    private String folder;
    private Clip clip;
    
    private final Font reg = Font.font("", FontWeight.NORMAL, 16);
    private final Font bold = Font.font("", FontWeight.BOLD, 16);
    
    private List<Utterance> utterancesNormal = new ArrayList<>();
    private List<Utterance> utterancesForced = new ArrayList<>();
    private Map<Utterance, Integer> wordSentenceMap = new HashMap<>();
    private List<Clip> clips = new ArrayList<>();
    
    private boolean sphinxUsed;
    private double threshold;
    
    public ResultPage(Stage stage, Evaluation evaluation) throws IOException{
        thisStage = stage;
        this.evaluation = evaluation;
        this.sphinxUsed = evaluation.isSphinxUsed();
        folder = evaluation.getFolder() + String.format("%02d/", evaluation.getId()); // folder <-- "src/resources/evaluations/<user.toString()>/"
        System.out.println(folder);
    }
    
    public void show(){
        thisStage.showAndWait();
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
    }
    
    private void playWavFile() throws LineUnavailableException, IOException, UnsupportedAudioFileException{
    /*
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("src/readingready/resources/"+"aron.wav").getAbsoluteFile()); 
        clip = AudioSystem.getClip(); 
        clip.open(audioInputStream); ; 
        clip.setLoopPoints(0, -1);
        clip.start();
        stopButton.setDisable(false);
    */
    }
    private void stopWavFile(){
        clip.stop();
        stopButton.setDisable(true);
    }

    public void setPassageNormal() throws FileNotFoundException, IOException, UnsupportedAudioFileException, LineUnavailableException{
        System.out.println(folder);
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
                audioInputStream = AudioSystem.getAudioInputStream(
                        new File(folder + String.format("wavs/%02d.wav", sentence))
                        .getAbsoluteFile()); 
                clips.add(AudioSystem.getClip());
            }
            else {
                strArray = line.split(" ");
                word = strArray[0];
                ascr = Double.parseDouble(strArray[3]);
                pronunciation = (sphinxUsed) ? strArray[4] : null;

                hLink = new Hyperlink(word);
                hLink.setFont((ascr > threshold) ? reg : bold); 
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
                hLink.setFont((ascr > threshold) ? reg : bold); 
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
        progressBar.setProgress((double)(sWord.getAscr()) / threshold);
    }
    
    private void selectedWordForced(int id){
        Utterance sWord = utterancesForced.get(id);
        lRSWord.setText(sWord.getWord());
        progressBar.setProgress((double)(sWord.getAscr()) / threshold);
    }

    private void setStaticUI() {
        vBoxRPParent.setPrefSize(Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getHeight());
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
    }

    private void setCurrWav(int sentence) {
        clip = clips.get(sentence);
    }
}