/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package readingready;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Lorenz
 */
public class PhonemeBuilderPage implements Initializable {
    Stage thisStage = new Stage();
    @FXML
    private Label lPBWord;
    @FXML
    private Button btnPBSubmit,btnPB2,btnPB3,btnPB4,btnPB5,btnPB6,btnPB7,btnPB8,btnPB9,btnPB10;
    @FXML
    private Button btnPB11,btnPB12,btnPB13,btnPB14,btnPB15,btnPB16,btnPB17,btnPB18,btnPB19,btnPB20;
    @FXML
    private Button btnPB21,btnPB22,btnPB23,btnPB24,btnPB25,btnPB26,btnPB27,btnPB28,btnPB29,btnPB30;
    @FXML
    private Button btnPB31,btnPB32,btnPB33,btnPB34,btnPB35,btnPB36,btnPB37,btnPB38,btnPB39,btnPB40;
    @FXML
    private TextField tfPBPhoneme;
    
    private String title;
    private Word word;
    PhonemeBuilderPage(String title, Word selected) throws IOException {
        this.title = title;
        word = selected;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("PhonemeBuilderPage.fxml"));
        loader.setController(this);
        Scene scene = new Scene(loader.load());
        scene.getStylesheets().add(getClass().getResource("PhonemeBuilderPage.css").toExternalForm());
        thisStage.setScene(scene);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lPBWord.setText(word.getWord());
        btnPBSubmit.setOnAction((ActionEvent e) -> {
            try {
                appendToFile(getFinal());
            } catch (IOException ex) {
                Logger.getLogger(PhonemeBuilderPage.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        btnPB2.setOnAction((ActionEvent e) -> {
            tfPBPhoneme.appendText(btnPB2.getText()+" ");
        });
        btnPB3.setOnAction((ActionEvent e) -> {
            tfPBPhoneme.appendText(btnPB3.getText()+" ");
        });
        btnPB4.setOnAction((ActionEvent e) -> {
            tfPBPhoneme.appendText(btnPB4.getText()+" ");
        });
        btnPB5.setOnAction((ActionEvent e) -> {
            tfPBPhoneme.appendText(btnPB5.getText()+" ");
        });
        btnPB6.setOnAction((ActionEvent e) -> {
            tfPBPhoneme.appendText(btnPB6.getText()+" ");
        });
        btnPB7.setOnAction((ActionEvent e) -> {
            tfPBPhoneme.appendText(btnPB7.getText()+" ");
        });
        btnPB8.setOnAction((ActionEvent e) -> {
            tfPBPhoneme.appendText(btnPB8.getText()+" ");
        });
        btnPB9.setOnAction((ActionEvent e) -> {
            tfPBPhoneme.appendText(btnPB9.getText()+" ");
        });
        btnPB10.setOnAction((ActionEvent e) -> {
            tfPBPhoneme.appendText(btnPB10.getText()+" ");
        });
        btnPB11.setOnAction((ActionEvent e) -> {
            tfPBPhoneme.appendText(btnPB11.getText()+" ");
        });
        btnPB12.setOnAction((ActionEvent e) -> {
            tfPBPhoneme.appendText(btnPB12.getText()+" ");
        });
        btnPB13.setOnAction((ActionEvent e) -> {
            tfPBPhoneme.appendText(btnPB13.getText()+" ");
        });
        btnPB14.setOnAction((ActionEvent e) -> {
            tfPBPhoneme.appendText(btnPB14.getText()+" ");
        });
        btnPB15.setOnAction((ActionEvent e) -> {
            tfPBPhoneme.appendText(btnPB15.getText()+" ");
        });
        btnPB16.setOnAction((ActionEvent e) -> {
            tfPBPhoneme.appendText(btnPB16.getText()+" ");
        });
        btnPB17.setOnAction((ActionEvent e) -> {
            tfPBPhoneme.appendText(btnPB17.getText()+" ");
        });
        btnPB18.setOnAction((ActionEvent e) -> {
            tfPBPhoneme.appendText(btnPB18.getText()+" ");
        });
        btnPB19.setOnAction((ActionEvent e) -> {
            tfPBPhoneme.appendText(btnPB19.getText()+" ");
        });
        btnPB20.setOnAction((ActionEvent e) -> {
            tfPBPhoneme.appendText(btnPB20.getText()+" ");
        });
        btnPB21.setOnAction((ActionEvent e) -> {
            tfPBPhoneme.appendText(btnPB21.getText()+" ");
        });
        btnPB22.setOnAction((ActionEvent e) -> {
            tfPBPhoneme.appendText(btnPB22.getText()+" ");
        });
        btnPB23.setOnAction((ActionEvent e) -> {
            tfPBPhoneme.appendText(btnPB23.getText()+" ");
        });
        btnPB24.setOnAction((ActionEvent e) -> {
            tfPBPhoneme.appendText(btnPB24.getText()+" ");
        });
        btnPB25.setOnAction((ActionEvent e) -> {
            tfPBPhoneme.appendText(btnPB25.getText()+" ");
        });
        btnPB26.setOnAction((ActionEvent e) -> {
            tfPBPhoneme.appendText(btnPB26.getText()+" ");
        });
        btnPB27.setOnAction((ActionEvent e) -> {
            tfPBPhoneme.appendText(btnPB27.getText()+" ");
        });
        btnPB28.setOnAction((ActionEvent e) -> {
            tfPBPhoneme.appendText(btnPB28.getText()+" ");
        });
        btnPB29.setOnAction((ActionEvent e) -> {
            tfPBPhoneme.appendText(btnPB29.getText()+" ");
        });
        btnPB30.setOnAction((ActionEvent e) -> {
            tfPBPhoneme.appendText(btnPB30.getText()+" ");
        });
        btnPB31.setOnAction((ActionEvent e) -> {
            tfPBPhoneme.appendText(btnPB31.getText()+" ");
        });
        btnPB32.setOnAction((ActionEvent e) -> {
            tfPBPhoneme.appendText(btnPB32.getText()+" ");
        });
        btnPB33.setOnAction((ActionEvent e) -> {
            tfPBPhoneme.appendText(btnPB33.getText()+" ");
        });
        btnPB34.setOnAction((ActionEvent e) -> {
            tfPBPhoneme.appendText(btnPB34.getText()+" ");
        });
        btnPB35.setOnAction((ActionEvent e) -> {
            tfPBPhoneme.appendText(btnPB35.getText()+" ");
        });
        btnPB36.setOnAction((ActionEvent e) -> {
            tfPBPhoneme.appendText(btnPB36.getText()+" ");
        });
        btnPB37.setOnAction((ActionEvent e) -> {
            tfPBPhoneme.appendText(btnPB37.getText()+" ");
        });
        btnPB38.setOnAction((ActionEvent e) -> {
            tfPBPhoneme.appendText(btnPB38.getText()+" ");
        });
        btnPB39.setOnAction((ActionEvent e) -> {
            tfPBPhoneme.appendText(btnPB39.getText()+" ");
        });
        btnPB40.setOnAction((ActionEvent e) -> {
            tfPBPhoneme.appendText(btnPB40.getText()+" ");
        });

    }    

    public String getFinal(){
        return ""+word.getWord()+"("+(word.getPronunciations().size()+1)+") "+tfPBPhoneme.getText();
    }
    public void show() {
        thisStage.showAndWait();
    }
    
    public void appendToFile(String textToAppend) throws IOException{  
    BufferedWriter writer = new BufferedWriter(
                                new FileWriter(title+".DICT", true)  //Set true for append mode
                            ); 
    writer.newLine();   //Add new line
    writer.write(textToAppend);
    writer.close();
    }
    
}
