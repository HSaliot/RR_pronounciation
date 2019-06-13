/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package readingready;

import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;

/**
 *
 * @author Hannah Saliot
 */
public class Pronunciation extends Application {
    
    
  public void start() throws IOException {
        Evaluation evaluation = new Evaluation("Dark Chocolate", "DELA CRUZ, Juan", null);
        ResultPage resultPage = new ResultPage(evaluation);
        resultPage.show();
      
    }

    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
