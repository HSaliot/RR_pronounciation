/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package readingready;

import com.sun.deploy.util.StringUtils;
import edu.cmu.sphinx.api.SpeechResult;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.sound.sampled.Clip;

/**
 * FXML Controller class
 *
 * @author Lorenz
 */
public class ReadingSelectionPage implements Initializable {

    @FXML
    private TextFlow tfReadings;
    @FXML
    private Label lRSTitle;
    @FXML
    private Label lRSWord;
    @FXML
    private Button btnRSaddPronunciation;
    @FXML
    private VBox vBoxPronunciations;
    
    private SpeechResult speechResult;
    private Stage thisStage;
    private Clip clip;
    private ArrayList<Word> wordsList = new ArrayList<>();
    private String title;
    private int selectedWordIndex;
    private ArrayList<String> strings = new ArrayList<>();
    private boolean exist;
    public ReadingSelectionPage(Stage stage,String title) throws IOException{
        this.title = title;
        thisStage = stage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ReadingSelectionPage.fxml"));
        loader.setController(this);
        Scene scene = new Scene(loader.load());
        scene.getStylesheets().add(getClass().getResource("ReadingSelectionPage.css").toExternalForm());
        thisStage.setScene(scene);
        thisStage.setMaximized(true);

    }

    public void addSentences() throws IOException{
        String passage = "Dark chocolate finds its way into the best ice creams, biscuits and cakes. Although eating chocolate usually comes with a warning that it is fattening, it is also believed by some to have magical and medicinal effects. In fact, cacao trees are sometimes called Theobroma cacao which means “food of the gods.” Dark chocolate has been found out to be helpful in small quantities. One of its benefits is that it has some of the most important minerals and vitamins that people need. It has antioxidants that help protect the heart. Another important benefit is that the fat content of chocolate does not raise the level of cholesterol in the blood stream. A third benefit is that it helps address respiratory problems. Also, it has been found out to help ease coughs and respiratory concerns. Finally, chocolate increases serotonin levels in the brain. This is what gives us a feeling of well-being.";
        String[] words = passage.split(" ");
        ArrayList<Hyperlink> texts = new ArrayList();
        Hyperlink temp;
        exist=doesFileExist();
        for (int i=0; i<words.length;i++){
                wordsList.add(new Word(words[i]));
                
                temp = new Hyperlink(words[i]+" ");
                //int numPro=1;
                if(inDictionary(words[i])==true) {
                    temp.setFont(Font.font("",FontWeight.NORMAL,16));                     
                }else{
                    temp.setFont(Font.font("",FontWeight.NORMAL,16)); 
                    temp.setTextFill(Color.RED); 
                }
                String tempWord = words[i]; 
                Word tWord = wordsList.get(i);
                int tempInt = i;
                temp.setOnAction((ActionEvent e) -> {
                    setSelectedWord(tWord);
                    selectedWordIndex = tempInt;
                });
                texts.add(temp);     
                wordsList.get(i).setLastIndex();
        }
        tfReadings.setTextAlignment(TextAlignment.JUSTIFY); 
        ObservableList list = tfReadings.getChildren(); 
        list.addAll(texts);
        if(!exist)
            createDICT();
    }

    @Override
    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        lRSTitle.setText(title);
        tfReadings.setPrefWidth(Screen.getPrimary().getBounds().getWidth()/2);
        try {
            addSentences();
        } catch (IOException ex) {
            Logger.getLogger(ReadingSelectionPage.class.getName()).log(Level.SEVERE, null, ex);
        }
        btnRSaddPronunciation.setOnAction((ActionEvent e) -> {
            PhonemeBuilderPage phonemeBuilderPage = null;
            try {
                phonemeBuilderPage = new PhonemeBuilderPage(this,title,wordsList.get(selectedWordIndex));
            } catch (IOException ex) {
                Logger.getLogger(ReadingSelectionPage.class.getName()).log(Level.SEVERE, null, ex);
            }
            phonemeBuilderPage.show();
            
        });
    }
    
    public boolean inDictionary(String input) throws IOException {

        input = input.replace(".", ""); //replace all . character
        input = input.replace(",", ""); //replace all , character
        input = input.replace("“", ""); //replace all “ character
        input = input.replace("”", ""); //replace all ” character
        input = input.replace("—", ""); //replace all — character
        input = input.replace("’", ""); //replace all ’ character
        
        String[] words=null;  //Intialize the word Array
        File open;
        if(exist)
            open=new File(title+".DICT"); //Creation of File Descriptor for input file
        else
            open=new File("cmudict-en-us.DICT"); //Creation of File Descriptor for input file
        FileReader fr = new FileReader(open);  //Creation of File Reader object
        BufferedReader br = new BufferedReader(fr); //Creation of BufferedReader object
        String s;     
        boolean found = false;   
        String delimiters = "[ \\(]";
        while((s=br.readLine())!=null)   //Reading Content from the file
        {
            words=s.split(delimiters);  //Split the word using space
            
            if (words[0].equals(input.toLowerCase()))   //Search for the given word
            {
                wordsList.get(wordsList.size()-1).addPronunciation(words);
                if(exist==false){
                    if(!(strings.contains(s)))
                        strings.add(s);
                }
                found = true;    //If Present, found is true
            }
        }
        fr.close();
        return found;
    }
    private void setSelectedWord(Word word){
        vBoxPronunciations.getChildren().clear();
        String temp = word.getWord();
        temp = temp.replace(".", ""); //replace all . character
        temp = temp.replace(",", ""); //replace all , character
        temp = temp.replace("“", ""); //replace all “ character
        temp = temp.replace("”", ""); //replace all ” character
        temp = temp.toLowerCase();
        lRSWord.setText(temp);        
        ArrayList<HBox> hboxes = new ArrayList<>();
        for(int i=0; i<word.getPronunciations().size();i++){
            HBox tempHBox = new HBox();
            Label tempLabel = new Label("/ "+word.getPronunciations().get(i)+" /");
            Button tempBtn = new Button("X");
            int tempInt = i;
            tempBtn.setOnAction((ActionEvent e) -> {
                try {
                    deletePronunciation(word,tempInt);
                } catch (IOException ex) {
                    Logger.getLogger(ReadingSelectionPage.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            tempHBox.getChildren().add(tempLabel);
            tempHBox.getChildren().add(tempBtn);
            hboxes.add(tempHBox);
        }
        ObservableList list = vBoxPronunciations.getChildren(); 
        list.addAll(hboxes);
    }
    public void deletePronunciation(Word word, int index) throws IOException{
        deleteInFile(word,index);
        for(int i = 0; i<wordsList.size(); i++){
            if(wordsList.get(i).getWord().equalsIgnoreCase(word.getWord()))
                wordsList.get(i).removePronunciation(index);
        }
        setSelectedWord(word);
    }
    public void  deleteInFile(Word word,int index) throws FileNotFoundException, IOException{
        File inputFile = new File(title+".DICT");
        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        String currentLine;
        ArrayList<String> tempStrings = new ArrayList<>();
        while((currentLine = reader.readLine()) != null) {
            if((currentLine.equals(word.getWord()+" "+word.getPronunciations().get(index)))||(currentLine.equals(word.getWord()+" "+word.getPronunciations().get(index)+" "))||
                (currentLine.startsWith(word.getWord()+"(")&&(currentLine.endsWith(") "+word.getPronunciations().get(index))||currentLine.endsWith(") "+word.getPronunciations().get(index)+" "))));
            else
                tempStrings.add(currentLine);
        }
        reader.close(); 
        strings = tempStrings;
        createDICT();
    }
    
    public void addedPronunciation() throws IOException{
        wordsList.get(selectedWordIndex).increaseLastIndex();

    }
    private boolean doesFileExist(){
        boolean exist = false;
        File selection = new File(title+".DICT");
        if(selection.length()!=0)
            exist = true;
        return exist;        
    }
    private void createDICT() throws IOException{
        Collections.sort(strings);
        Path out = Paths.get(title+".DICT");
        Files.write(out,strings,Charset.defaultCharset());
    }
    
}
