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
public class SignupPage implements Initializable {

    @FXML
    private Hyperlink hlSLogin;
    @FXML
    private TextField tfSUsername;
    @FXML
    private TextField tfSFirstName;
    @FXML
    private TextField tfSLastName;
    @FXML
    private PasswordField pfSPassword;
    @FXML
    private PasswordField pfSConfirmPassword;
    @FXML
    private Button btnSSignup;
    @FXML
    private StackPane stackPane;
    
    private Stage thisStage;
    private final UserDao uDao = new UserDao();

    public SignupPage(Stage stage)throws IOException{
        thisStage = stage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SignupPage.fxml"));
        loader.setController(this);
        Scene scene = new Scene(loader.load());
        scene.getStylesheets().add(getClass().getResource("SignupPage.css").toExternalForm());
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
        
        hlSLogin.setOnAction((ActionEvent e) -> {
            try {
                toLogin();
            } catch (IOException ex) {
                Logger.getLogger(SignupPage.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        btnSSignup.setOnAction((ActionEvent e) -> {
            String fName = tfSFirstName.getText();
            String lName = tfSLastName.getText();
            String uName = tfSUsername.getText();
            String password1 = pfSPassword.getText();
            String password2 = pfSConfirmPassword.getText();
            
            if(fName.isEmpty() || lName.isEmpty() || uName.isEmpty() || password1.isEmpty() || password1.isEmpty()) 
                System.out.println("Please complete the form");
            
            else {
                if(password1.equals(password2)) {
                    try {
                        User user = uDao.findByUName(uName);
                        System.out.println("Username already taken. Please choose another username");
                    } catch (NoResultException ex) {
                        User user = new User(fName.toUpperCase(), lName.toUpperCase(), uName, password1);
                        uDao.create(user);
                        notifyCompletion();
                    }
                }
                else {
                    System.out.println("Password Mismatch. Please re-confirm password.");
                }
            }
            
            clearFields();
        });
    }
    public void toLogin() throws IOException{
        LoginPage login = new LoginPage(thisStage);
    }
    
    public void clearFields(){
        tfSFirstName.clear();
        tfSLastName.clear();
        tfSUsername.clear();
        pfSPassword.clear();
        pfSConfirmPassword.clear();
    }
    
    public void notifyCompletion(){
        System.out.println("User registered");
    }
}
