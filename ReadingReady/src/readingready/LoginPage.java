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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import readingready.dao.UserDao;
import readingready.nodeFactory.Icon;
import readingready.nodeFactory.IconFactory;

/**
 * FXML Controller class
 *
 * @author Lorenz
 * @author Hannah Saliot
 */
public class LoginPage implements Initializable {

    @FXML
    private Hyperlink hlLSignup;
    @FXML
    private TextField tfLUsername;
    @FXML
    private PasswordField pfLPassword;
    @FXML
    private Button btnLLogin;
    @FXML
    private StackPane stackPane;
    @FXML
    private HBox hbUName;
    @FXML
    private HBox hbPassword;
    
    
    private Stage thisStage;
    
    private final UserDao uDao = new UserDao();


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
        stackPane.setPrefHeight(Screen.getPrimary().getBounds().getHeight());
        stackPane.setPrefWidth(Screen.getPrimary().getBounds().getWidth());
        
        Label icon;
        IconFactory iconF = new IconFactory();
        
        icon = iconF.createIconS(Icon.USER, "#087830");
        hbUName.getChildren().add(icon);
        
        icon = iconF.createIconS(Icon.LOCK, "#087830");
        hbPassword.getChildren().add(icon);
        
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
            
            if(uName.isEmpty() || password.isEmpty()){
                System.out.println("Please complete the form");
            }
            else{
                tfLUsername.clear();
                pfLPassword.clear();
            
                User user = uDao.findAndAuthenticate(uName, password);
                if(user == null)
                    System.out.println("Wrong username or password");
                else
                    try {
                        toHome(user);
                    } catch (IOException ex) {
                    Logger.getLogger(LoginPage.class.getName()).log(Level.SEVERE, null, ex);
                    }
            }
        });
    }    
    
    public void show(){
        thisStage.showAndWait();
    }
    
    public void toHome(User user) throws IOException{
        HomePage home = new HomePage(thisStage, user);
    }
}
