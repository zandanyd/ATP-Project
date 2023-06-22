package View;
import Model.MyModel;
import ViewModel.MyViewModel;
import algorithms.mazeGenerators.Position;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.AudioClip;
import javafx.scene.control.Button;
import javafx.scene.web.WebView;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import javafx.util.Duration;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class KeyBoardController {

    private static KeyBoardController single_instance = null;
    private MyModel currModel = MyModel.getInstance();
    public AudioClip aPlayer;
    public Button exitButton;
    public Button playAgainButton;
    public Stage newWindow;
    public boolean openedWindow = false;
    public MediaPlayer mediaPlayer;
    public Scene endingScene;
    public Stage primaryStage;

    private KeyBoardController() {
    }
    public static synchronized KeyBoardController getInstance() {
        if(single_instance == null){
            single_instance =  new KeyBoardController();
        }
        return single_instance;
    }
    private MyViewModel viewModel = MyViewModel.getInstance();

    private Position newPos = null;

    public void keyHandle(KeyEvent e, MazeDisplay playground){
        switch (e.getCode()){
//            case M:
//                MyViewController.getInstance().stopBackGroundMusic();
            case DIGIT1, NUMPAD1:
                 playground.setPosition(viewModel.moveToCheck(1));
                break;
            case DIGIT2, NUMPAD2, DOWN:
                playground.setPosition(viewModel.moveToCheck(2));
                break;
            case DIGIT3, NUMPAD3:
                playground.setPosition(viewModel.moveToCheck(3));
                break;
            case DIGIT4, NUMPAD4, LEFT:
                playground.setPosition(viewModel.moveToCheck(4));
                break;
            case DIGIT6, NUMPAD6, RIGHT:
                //maze.draw();
                playground.setPosition(viewModel.moveToCheck(6));
                break;
            case DIGIT7, NUMPAD7:
                playground.setPosition(viewModel.moveToCheck(7));
                break;
            case DIGIT8, NUMPAD8, UP:
                playground.setPosition(viewModel.moveToCheck(8));
                break;
            case DIGIT9, NUMPAD9:
                playground.setPosition(viewModel.moveToCheck(9));
                break;
        }
    }

    public void handleKeyRelease()
    {
        Parent root = null;
        if(primaryStage == null) {
            primaryStage = MyViewController.getInstance().primaryStage;
        }
        if (MyViewModel.getInstance().getcurrModel() == null)
            return;
        if(MyViewModel.getInstance().isDone())
        {
            try {
                root = FXMLLoader.load(getClass().getResource("endingFXMLFile.fxml"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            newWindow = new Stage();
            Media media = null;
            try {
                media = new Media(getClass().getResource("/View/Resources/AllIDoIsWin.mp3").toURI().toString());
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setOnReady(() -> {
                Duration seekTime = Duration.seconds(6.93); // Desired start time
                mediaPlayer.seek(seekTime);
                mediaPlayer.play();
            });
            endingScene= new Scene(root, 300, 250);
            newWindow.setScene(endingScene);
            newWindow.show();
            openedWindow = true;
        }

    }


}
