package readingready;

import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

/**
 *
 * @author Hannah Saliot
 */
public class Pronounciation extends Application {


  public void start(Stage stage) throws IOException {
      /*
      Evaluation evaluation = new Evaluation("Dark Chocolate", "DELA CRUZ, Juan", null);
        ResultPage resultPage = new ResultPage(evaluation);
        resultPage.show();
      */
        Login login = new Login();
        login.show();
    }

    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}