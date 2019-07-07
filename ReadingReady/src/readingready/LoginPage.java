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
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javax.persistence.NoResultException;
import readingready.dao.UserDao;

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
    
    UserDao uDao = new UserDao();


    public LoginPage()throws IOException{
        thisStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
        loader.setController(this);
        Scene scene = new Scene(loader.load());
        scene.getStylesheets().add(getClass().getResource("Login.css").toExternalForm());
        thisStage.setScene(scene);
        thisStage.setMaximized(true);
    }
    
    public LoginPage(Stage stage)throws IOException{
        thisStage = stage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
        loader.setController(this);
        Scene scene = new Scene(loader.load());
        scene.getStylesheets().add(getClass().getResource("Login.css").toExternalForm());
        thisStage.setScene(scene);
        thisStage.setMaximized(true);
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
            String uName = tfLUsername.getText();
            String password = pfLPassword.getText();
            boolean pass = false;
            
            if(uName.isEmpty() || password.isEmpty()){
                System.out.println("Please complete the form");
            }
            else{
                try {
                    User user = uDao.findByUName(uName);
                    if(password.equals(user.getPassword()))
                        pass = true;    
                } catch (NoResultException ex){
                } finally {
                    tfLUsername.clear();
                    pfLPassword.clear();
                    if(pass)
                        try {
                            toHome();
                    } catch (IOException ex) {
                        Logger.getLogger(LoginPage.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    else
                        System.out.println("Wrong username or password");
                }
            }
        });
    }    
    public void show(){
        thisStage.showAndWait();
    }
    
    public void toHome() throws IOException{
        HomePage home = new HomePage(thisStage);
    }
}
