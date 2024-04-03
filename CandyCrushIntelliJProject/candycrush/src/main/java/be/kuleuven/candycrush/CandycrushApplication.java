package be.kuleuven.candycrush;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class CandycrushApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(CandycrushApplication.class.getResource("candycrush-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 370, 400);
        scene.getStylesheets().add(getClass().getResource("candycrush.css").toExternalForm());
        stage.getIcons().add(new Image(CandycrushApplication.class.getResourceAsStream("icon.png")));


        stage.setTitle("CandyCrush");
        stage.setScene(scene);
        stage.show();


    }

    public static void main(String[] args) {


        launch();
    }
}