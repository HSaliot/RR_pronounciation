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
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

/**
 * FXML Controller class
 *
 * @author Lorenz
 */
public class Login implements Initializable {

    @FXML
    Hyperlink hlLSignup;
    @FXML
    TextField tfLUsername;
    @FXML
    PasswordField pfLPassword;
    @FXML
    Button btnLLogin;
    
    private final EntityManagerFactory emf;
    
    @PersistenceContext(unitName="ReadingReadyPU")
    private final EntityManager em;
    
    private Stage thisStage = new Stage();

    public Login()throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
        loader.setController(this);
        Scene scene = new Scene(loader.load());
        scene.getStylesheets().add(getClass().getResource("Login.css").toExternalForm());
        thisStage.setScene(scene);
        thisStage.setMaximized(true);
        emf = Persistence.createEntityManagerFactory("ReadingReadyPU");
        em = emf.createEntityManager();
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        hlLSignup.requestFocus();
        hlLSignup.setOnAction((ActionEvent e) -> {
            Signup signup = null;
            try {
                
                signup = new Signup();
            } catch (IOException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
            close();
            signup.show();
        });
        
        btnLLogin.setOnAction((ActionEvent e) -> {
            String username = tfLUsername.getText();
            String password = pfLPassword.getText();
            
            User user = em.createNamedQuery("User.findByUName", User.class)
                    .setParameter("uName", username)
                    .getSingleResult();
        
            System.out.println(user.getLName());
            
            
            /**
            if(user == null)
                System.out.println("User does not exist");
            else {   
                System.out.println("User");
                if(user.getPassword() == password)
                    home.show();
            }
            **/
            
        });
    }    
    public void show(){
        thisStage.showAndWait();
    }
    public void close(){
        thisStage.close();
    }
}
