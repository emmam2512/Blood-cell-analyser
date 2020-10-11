package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.stage.Stage;

public class Main extends Application {
    public static Stage primaryStage;
    public static Parent root;
    public static Image image;
    public static PixelReader reader;
    public static PixelReader triColourReader;

    @Override
    public void start(Stage ps) throws Exception{

        root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage = ps;
        primaryStage.setTitle(" Blood Analyzer ");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
