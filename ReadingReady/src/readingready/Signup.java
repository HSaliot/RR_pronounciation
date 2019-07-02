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
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Lorenz
 */
public class Signup implements Initializable {

    @FXML
    Hyperlink hlSLogin;
    @FXML
    TextField tfSUsername;
    @FXML
    PasswordField pfSPassword;
    @FXML
    PasswordField pfSConfirmPassword;
    @FXML
    Button btnSSignup;
    
    private Stage thisStage = new Stage();

    public Signup()throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Signup.fxml"));
        loader.setController(this);
        Scene scene = new Scene(loader.load());
        scene.getStylesheets().add(getClass().getResource("Signup.css").toExternalForm());
        thisStage.setScene(scene);
        thisStage.setMaximized(true);
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        hlSLogin.setOnAction((ActionEvent e) -> {
            try {
                
                toLogin();
                        } catch (IOException ex) {
                Logger.getLogger(Signup.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        btnSSignup.setOnAction((ActionEvent e) -> {
            if(tfSUsername.getText().length()!=0&&pfSPassword.getText().length()!=0&&pfSConfirmPassword.getText().equals(pfSPassword.getText())){
                System.out.println(tfSUsername.getText());
                System.out.println(pfSPassword.getText());
                try {
                    toLogin();
                } catch (IOException ex) {
                    Logger.getLogger(Signup.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else
                System.out.println("else");
        });
    }
    public void show(){
        thisStage.showAndWait();
    }
    public void close(){
        thisStage.close();
    }
    public void toLogin() throws IOException{
        Login login = new Login();
        login.show();
    }
}
