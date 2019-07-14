/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package readingready;

import edu.cmu.sphinx.api.SpeechResult;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
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
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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
    
    @FXML
    private Label labelSelection;
    @FXML
    private Label labelStudent;
    @FXML
    private Label labelDateRecorded;
    @FXML
    private Label labelID;
    @FXML
    private Button playButton;
    @FXML
    private Button stopButton;
    @FXML
    private TextFlow tfReadingsNormal;
    @FXML
    private TextFlow tfReadingsForced;
    @FXML 
    private Label lRSWord;
    @FXML
    private VBox vBoxRPParent;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private MenuItem btnBack;
    
    private SpeechResult speechResult;
    private Stage thisStage;
    private Evaluation evaluation;
    private Clip clip;
    private ArrayList<Utterance> wordsListNormal = new ArrayList<>();
    private ArrayList<Utterance> wordsListForced = new ArrayList<>();
    private String filenameNormal="";
    private String filenameForced="";
    public ResultPage(Stage stage,String filename,Evaluation evaluation) throws IOException{
        thisStage = stage;
        this.evaluation = evaluation;
        String pathFileNormal = "src/readingready/resources/evaluations/" + evaluation.getStudent().toString()+"/"+String.format("%02d", evaluation.getId())+"/" + "pocketSphinxResultNormal.txt";
        String pathFileForced = "src/readingready/resources/evaluations/" + evaluation.getStudent().toString()+"/"+String.format("%02d", evaluation.getId())+"/" + "pocketSphinxResultForced.txt";
        this.filenameNormal = pathFileNormal;
        this.filenameForced = pathFileForced;        
        thisStage = stage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ResultPage.fxml"));
        loader.setController(this);
        Scene scene = new Scene(loader.load());
        scene.getStylesheets().add(getClass().getResource("ResultPage.css").toExternalForm());
        thisStage.setScene(scene);
        
    }
    
    
    public void show(){
        thisStage.showAndWait();
    }
    
    @Override
    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        vBoxRPParent.setPrefSize(Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getHeight());
        tfReadingsNormal.setPrefWidth(Screen.getPrimary().getBounds().getWidth()/2);
        tfReadingsForced.setPrefWidth(Screen.getPrimary().getBounds().getWidth()/2);
        try {
            setPassage(true);
            setPassage(false);
        } catch (IOException ex) {
            Logger.getLogger(ResultPage.class.getName()).log(Level.SEVERE, null, ex);
        }
        labelSelection.setText(evaluation.getSelection().getTitle());
        labelStudent.setText(evaluation.getStudent().getLName()+", "+evaluation.getStudent().getFnameetc());
        labelDateRecorded.setText(evaluation.getDatedone().toString());
        labelID.setText(evaluation.getId().toString());

        
        stopButton.setDisable(true);        
        
        /*
        evaluation.getEvaluatedSentences().forEach(c -> {
            c.getWords().forEach(i -> {
                this
            })
        });
        */
        playButton.setOnAction((ActionEvent e) -> {
            try {
                playWavFile();
            } catch (LineUnavailableException ex) {
                Logger.getLogger(ResultPage.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ResultPage.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnsupportedAudioFileException ex) {
                Logger.getLogger(ResultPage.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        stopButton.setOnAction((ActionEvent e) -> {
            stopWavFile();
        });
        
        btnBack.setOnAction((ActionEvent e) -> {
            try {
                HomePage home = new HomePage(thisStage);
            } catch (IOException ex) {
                Logger.getLogger(ReadingSelectionPage.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        
    }
    
    private void playWavFile() throws LineUnavailableException, IOException, UnsupportedAudioFileException{
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("src/readingready/resources/"+"aron.wav").getAbsoluteFile()); 
        clip = AudioSystem.getClip(); 
        clip.open(audioInputStream); ; 
        clip.setLoopPoints(3560, 4110);
        clip.start();
        stopButton.setDisable(false);
    }
    private void stopWavFile(){
        clip.stop();
        stopButton.setDisable(true);
    }

    public void setPassage(Boolean normal) throws FileNotFoundException, IOException{
        String filePath;
        ArrayList<Utterance> wordsList = new ArrayList<>();
        
        if(normal)
            filePath = filenameNormal;
        else
            filePath = filenameForced;
        
        File open = new File(filePath);
        FileReader fr = new FileReader(open);  //Creation of File Reader object
        BufferedReader br = new BufferedReader(fr); //Creation of BufferedReader object
        String s;
        String passage = null;
        ArrayList<Hyperlink> texts = new ArrayList();
        Hyperlink temp;
        int tempInt=0;
        while((s=br.readLine())!=null){   //Reading Content from the file
            String[] strArray = s.split(" ");
            if(!strArray[0].contains("***")){
                wordsList.add(new Utterance(strArray[0]));
                temp = new Hyperlink(wordsList.get(tempInt).getWord());
                if(wordsList.get(tempInt).getAscr()>-1000)
                    temp.setFont(Font.font("",FontWeight.NORMAL,16)); 
                else
                    temp.setFont(Font.font("",FontWeight.BOLD,16)); 
                int index =tempInt;
                temp.setOnAction((ActionEvent e) -> {
                    selectedWord(index,normal);
                });
                texts.add(temp);
                tempInt++;
            }
        }
        if(normal){
            wordsListNormal = wordsList;
            tfReadingsNormal.setTextAlignment(TextAlignment.JUSTIFY); 
            ObservableList list = tfReadingsNormal.getChildren(); 
            list.addAll(texts);
        }
        else{
            wordsListForced = wordsList;
            tfReadingsForced.setTextAlignment(TextAlignment.JUSTIFY); 
            ObservableList list = tfReadingsForced.getChildren(); 
            list.addAll(texts);
        }
        
        br.close();
        fr.close();     
    }

    
    @FXML
    private void selectedWord(int id,boolean normal){
        Utterance sWord;
        if(normal)
            sWord = wordsListNormal.get(id);
        else
            sWord = wordsListForced.get(id);
        
        lRSWord.setText(sWord.getWord());

    }
    
}
