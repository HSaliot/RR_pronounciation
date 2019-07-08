/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package readingready;

import edu.cmu.sphinx.api.SpeechResult;
import java.io.File;
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
import javafx.scene.layout.HBox;
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
    private MenuItem add;
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
    private TextFlow tfReadings;
    @FXML 
    private Label lRSWord;
    @FXML
    private VBox vBoxRPParent;
    @FXML
    private ProgressBar progressBar;
    
    private SpeechResult speechResult;
    private final Stage thisStage = new Stage();
    private final Evaluation evaluation;
    private Clip clip;
    private ArrayList<Utterance> wordsList = new ArrayList<>();
    
    
    public ResultPage(Evaluation evaluation) throws IOException{
        this.evaluation = evaluation;
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ResultPage.fxml"));
        loader.setController(this);
        Scene scene = new Scene(loader.load());
        scene.getStylesheets().add(getClass().getResource("ResultPage.css").toExternalForm());
        thisStage.initStyle(StageStyle.TRANSPARENT);
        thisStage.setScene(scene);
        thisStage.setMaximized(true);
    }
    
    public void show(){
        thisStage.showAndWait();
    }
    
    @Override
    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        vBoxRPParent.setPrefSize(Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getHeight());
        tfReadings.setPrefWidth(Screen.getPrimary().getBounds().getWidth()/2);
        
        addSentences();
        labelSelection.setText("Dark Chocolate");
        labelStudent.setText("Cruz, Juan Dela");
        labelDateRecorded.setText("Date");
        labelID.setText("ID 1");

        
        stopButton.setDisable(true);        
        add.setOnAction((ActionEvent e) -> {
            openReadingEvaluationPage();
        });
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
        
    }

    private void openReadingEvaluationPage() {
        ReadingEvaluationPage readingEvaluation;
        try {
            readingEvaluation = new ReadingEvaluationPage();
            readingEvaluation.show();
        } catch (IOException ex) {
            ;
        }
        
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

    public void addSentences(){
        String passage = "Dark chocolate finds its way into the best ice creams, biscuits and cakes. Although eating chocolate usually comes with a warning that it is fattening, it is also believed by some to have magical and medicinal effects. In fact, cacao trees are sometimes called Theobroma cacao which means “food of the gods.” Dark chocolate has been found out to be helpful in small quantities. One of its benefits is that it has some of the most important minerals and vitamins that people need. It has antioxidants that help protect the heart. Another important benefit is that the fat content of chocolate does not raise the level of cholesterol in the blood stream. A third benefit is that it helps address respiratory problems. Also, it has been found out to help ease coughs and respiratory concerns. Finally, chocolate increases serotonin levels in the brain. This is what gives us a feeling of well-being.";
        String[] words = passage.split(" ");
        ArrayList<Hyperlink> texts = new ArrayList();
        Hyperlink temp;
        
        for (int i=0; i<words.length;i++){
                wordsList.add(new Utterance(words[i]));
                temp = new Hyperlink(words[i]+" ");
                temp.setId(Integer.toString(i));
                
                if(wordsList.get(i).getAscr()<800) {
                    temp.setFont(Font.font("",FontWeight.NORMAL,16)); 
                    //temp.setFill(Color.DARKBLUE); 
                }else{
                    temp.setFont(Font.font("",FontWeight.BOLD,16)); 
                
                }
                
                int tempId = i;
                temp.setOnAction((ActionEvent e) -> {
                    selectedWord(tempId);
                });
                texts.add(temp);
        }
        tfReadings.setTextAlignment(TextAlignment.JUSTIFY); 
        ObservableList list = tfReadings.getChildren(); 
        list.addAll(texts);
    }
    
    @FXML
    private void selectedWord(int id){
        Utterance sWord = wordsList.get(id);
        lRSWord.setText(sWord.getWord());
        progressBar.setProgress((double)(sWord.getAscr())/-1797);

    }
    
}
