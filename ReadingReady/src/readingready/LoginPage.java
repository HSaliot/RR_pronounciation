/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package readingready;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.StageStyle;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Lorenz
 */
public class LoginPage implements Initializable {

    @FXML
    Hyperlink hlLSignup;
    @FXML
    TextField tfLUsername;
    @FXML
    PasswordField pfLPassword;
    @FXML
    Button btnLLogin;
    @FXML
    StackPane stackPane;
    private Stage thisStage;

    public LoginPage()throws IOException{
        thisStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginPage.fxml"));
        loader.setController(this);
        Scene scene = new Scene(loader.load());
        scene.getStylesheets().add(getClass().getResource("LoginPage.css").toExternalForm());
        thisStage.setScene(scene);
        thisStage.setMaximized(true);
    }
    public LoginPage(Stage stage)throws IOException{
        thisStage = stage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginPage.fxml"));
        loader.setController(this);
        Scene scene = new Scene(loader.load());
        scene.getStylesheets().add(getClass().getResource("LoginPage.css").toExternalForm());
        thisStage.setScene(scene);
        thisStage.setMaximized(true);
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        stackPane.setPrefHeight(Screen.getPrimary().getBounds().getHeight());
        stackPane.setPrefWidth(Screen.getPrimary().getBounds().getWidth());
        
        hlLSignup.requestFocus();
        hlLSignup.setOnAction((ActionEvent e) -> {
            SignupPage signup = null;
            try {
                
                signup = new SignupPage(thisStage);
            } catch (IOException ex) {
                Logger.getLogger(LoginPage.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        btnLLogin.setOnAction((ActionEvent e) -> {
            if(tfLUsername.getText().length()!=0&&pfLPassword.getText().length()!=0){
                System.out.println(tfLUsername.getText());
                System.out.println(pfLPassword.getText());
                try {
                    HomePage home = new HomePage();
                    home.show();
                } catch (IOException ex) {
                    Logger.getLogger(LoginPage.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else
                System.out.println("else");
        });
    }    
    public void show(){
        thisStage.showAndWait();
    }
}
