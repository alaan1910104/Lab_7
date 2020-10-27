import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    public static void main(String[] args){ launch(args);}


    @Override
    public void start(Stage pstage) throws IOException {

        new StageManager(pstage);

        pstage.setTitle("Lab 7!");
        pstage.show();
    }
}
