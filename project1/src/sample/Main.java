package sample;
/**
 * "Fast Certificater"
 * Apache PDFBox Library used (version 2.0.8 jar)
 * Scene Builder 2.0 is used to implement the gui
 * x = 220  y = 232  fontSize = 50 (initial coordinates)
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
    @Override  // This part structures the main form
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        //primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setTitle("Fast Certificater");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args); //driver class
    }
}
