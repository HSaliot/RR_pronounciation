/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package readingready;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import readingready.dao.ReadingSelectionDao;
import readingready.nodeFactory.Icon;
import readingready.nodeFactory.IconFactory;

/**
 * FXML Controller class
 *
 * @author Lorenz
 */

public class HomePage implements Initializable {
    
    @FXML
    private VBox vBoxHP;
    
    @FXML
    private TilePane tpReadingSelections;
    
    @FXML
    private Button btnHAddSelection;
    
    private Stage thisStage;
    private final IconFactory iconF = new IconFactory();
    private final ReadingSelectionDao rsDao = new ReadingSelectionDao();
    private List<ReadingSelection> selections;

    public HomePage(Stage stage, User user) throws IOException{
        thisStage = stage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("HomePage.fxml"));
        loader.setController(this);
        Scene scene = new Scene(loader.load());
        scene.getStylesheets().add(getClass().getResource("HomePage.css").toExternalForm());
        
        thisStage = stage;
        thisStage.setScene(scene);
        thisStage.setMaximized(true);        
    }
    /**
     * Initializes the controller class.
     */
    @Override
    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        vBoxHP.setPrefHeight(Screen.getPrimary().getBounds().getHeight());
        vBoxHP.setPrefWidth(Screen.getPrimary().getBounds().getWidth());
        
        btnHAddSelection.setOnAction(e -> {
                AddReadingSelectionPage addReadingSelectionPage = null;
            try {
                addReadingSelectionPage = new AddReadingSelectionPage(this);
            } catch (IOException ex) {
                Logger.getLogger(HomePage.class.getName()).log(Level.SEVERE, null, ex);
            }
                addReadingSelectionPage.show();
        });
        
        updateSelections();
    }    

    private void toFocus(Stage thisStage, ReadingSelection selection) throws IOException {
        ReadingSelectionPage selectionPage = new ReadingSelectionPage(thisStage, selection);
    }
    
    public void updateSelections(){
        tpReadingSelections.getChildren().clear();
        selections = rsDao.findAll(ReadingSelection.class);
        selections.forEach(selection -> {
            Button button = iconF.createButtonL(Icon.BOOK, "  " + selection.getTitle(), "");
            button.getStyleClass().add("btnClear");
            button.setOnAction(e -> {
                try {
                    toFocus(thisStage, selection);
                } catch (IOException ex) {
                    Logger.getLogger(HomePage.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            tpReadingSelections.getChildren().add(button);
        });
    }

}
