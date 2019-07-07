/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package readingready;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Screen;
import javafx.stage.Stage;

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
    
    private Stage thisStage;
    ReadingSelection selection;
    private ArrayList<Word> wordsList = new ArrayList<>();
    
    private int selectedWordIndex;
    private ArrayList<String> strings = new ArrayList<>();
    private boolean exist;
    
    public ReadingSelectionPage(Stage stage, ReadingSelection selection) throws IOException{
        this.selection = selection;
        thisStage = stage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ReadingSelectionPage.fxml"));
        loader.setController(this);
        Scene scene = new Scene(loader.load());
        scene.getStylesheets().add(getClass().getResource("ReadingSelectionPage.css").toExternalForm());
        thisStage.setScene(scene);
        thisStage.setMaximized(true);
    }
    
    public String getPassage() throws FileNotFoundException, IOException{
        String filename = "src/readingready/resources/" + selection.getTitle() + ".txt";
        File open = new File(filename);
        FileReader fr = new FileReader(open);  //Creation of File Reader object
        BufferedReader br = new BufferedReader(fr); //Creation of BufferedReader object
        String s;
        String passage="";
        while((s=br.readLine())!=null)   //Reading Content from the file
            passage+=s;
        
        br.close();
        fr.close();
        return passage;        
    }

    public void addSentences() throws IOException, URISyntaxException{
        String passage = getPassage();
        passage = passage.replace("-", " ");
        String[] words = passage.split(" ");
        
        ArrayList<Hyperlink> hyperlinks = new ArrayList<>();
        Hyperlink hyperlink;
        
        exist = doesFileExist();
        
        for (int i = 0; i < words.length; i++){
            hyperlink = new Hyperlink(words[i]+" ");

            //marking dictionary-foreign words
            if(inDictionary(words[i])) {
                hyperlink.setFont(Font.font("",FontWeight.NORMAL,16));                     
            } else {
                hyperlink.setFont(Font.font("",FontWeight.NORMAL,16)); 
                hyperlink.setTextFill(Color.RED); 
            }
            
            int tempInt = i;
            Word word = new Word(words[i]);
            hyperlink.setOnAction((ActionEvent e) -> {
                setSelectedWord(word);
                selectedWordIndex = tempInt;
            });
            
            wordsList.add(word);
            hyperlinks.add(hyperlink);     
            wordsList.get(i).setLastIndex();
        }
        
        tfReadings.setTextAlignment(TextAlignment.JUSTIFY); 
        ObservableList list = tfReadings.getChildren(); 
        list.addAll(hyperlinks);
        if(!exist)
            createDICT();
    }

    @Override
    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        lRSTitle.setText(selection.getTitle());
        tfReadings.setPrefWidth(Screen.getPrimary().getBounds().getWidth()/2);
        
        try {
            addSentences();
        } catch (IOException ex) {
            Logger.getLogger(ReadingSelectionPage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(ReadingSelectionPage.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        btnRSaddPronunciation.setOnAction((ActionEvent e) -> {
            PhonemeBuilderPage phonemeBuilderPage = null;
            try {
                phonemeBuilderPage = new PhonemeBuilderPage(this,selection.getTitle(),wordsList.get(selectedWordIndex));
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
            open=new File(selection.getTitle()+".DICT"); //Creation of File Descriptor for input file
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
        File inputFile = new File(selection.getTitle()+".DICT");
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
        File file = new File(selection.getTitle() + ".DICT");
        if(file.length() != 0)
            exist = true;
        return exist;        
    }
    
    private void createDICT() throws IOException{
        Collections.sort(strings);
        Path out = Paths.get(selection.getTitle() + ".DICT");
        Files.write(out,strings,Charset.defaultCharset());
    }
    
    
    
    
    
}
