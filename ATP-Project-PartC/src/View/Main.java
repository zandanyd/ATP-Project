package View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.awt.*;
import java.io.FileInputStream;
import javafx.scene.control.MenuItem;

public class Main extends Application {


    public Button Start = new Button();

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MyView.fxml"));
        MyViewController controller = loader.getController();
        // Pass the primary stage to the controller
        MyViewController.getInstance().setPrimaryStage(primaryStage);
        Parent root = loader.load();
        primaryStage.setScene(new Scene(root, 600, 600));
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
