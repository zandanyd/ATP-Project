package View;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

import ViewModel.MyViewModel;
import algorithms.mazeGenerators.Maze;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Window;
import javafx.util.Duration;


public class MyViewController implements IView {

    private static MyViewController single_instance = null;

    public MenuItem newItem;
    public MenuItem saveItem = new javafx.scene.control.MenuItem("Save");
    public MenuItem loadItem = new javafx.scene.control.MenuItem("Load");
    public MenuItem propertiesItem = new MenuItem("Properties");
    public Menu exitMenu = new Menu("Exit");
    public Menu helpMenu = new Menu("Help");

    public Menu aboutMenu = new Menu("About");
    public Button Start;
    public Button playAgainButton;

    public Button Solution;
    public ComboBox difficultyComboBox;
    public RadioButton rightRadioButton;
    public RadioButton centerRadioButton;
    public RadioButton leftRadioButton;
    public ImageView leftImage;
    public ImageView centerImage;
    public ImageView rightImage;
    public Label difficultyLabel;
    public Label rowsLabel;
    public Label colsLabel;

    public MyViewModel model = MyViewModel.getInstance();;
    public TextField textField_mazeRows;
    public TextField textField_mazeColumns;
    public MazeDisplay playGround = new MazeDisplay();
    //public Stage primaryStage;
    public MediaPlayer backGroundMusic;
    public  BorderPane openingScene;
    public ArrayList<Stage> allAppStages = new ArrayList<>();;
    public DirectoryChooser directoryChooser = new DirectoryChooser();;
    public FileChooser fileChooser = new FileChooser();
    public Boolean[] radioButtonsPressed = {false, false, false};
    public Double xPosOnPressed = 0.0;
    public Double yPosOnPressed = 0.0;
    public int startOffSetX = 0;
    public int startOffSetY = 0;
    public Boolean newFromMenuBar = true;
    public String defaultDiff = "Medium";
    public Stage primaryStage;

    public MyViewController() {
        playGround.setModel(model);
        directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
    }
    public static synchronized MyViewController getInstance() {
        if(single_instance == null){
            single_instance =  new MyViewController();
        }
        return single_instance;
    }
    public void Start(ActionEvent actionEvent) {
        int rows = 0;
        int cols = 0;
        int playerSelection = whichCharacter();
        Node openingScene = (Node) actionEvent.getSource();
        primaryStage = (Stage) openingScene.getScene().getWindow();
        try {
            rows = Integer.valueOf(textField_mazeRows.getText());
            cols = Integer.valueOf(textField_mazeColumns.getText());
        }catch(NumberFormatException e){
            Alert inputAlert = new Alert(Alert.AlertType.ERROR);
            inputAlert.setTitle("Please notice");
            inputAlert.setHeaderText(null);
            inputAlert.setContentText("Please enter number for Maze rows: and for Maze columns: !");
            inputAlert.showAndWait();
            try {
                returnToHomeScreen();
                return;
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
        if(difficultyComboBox.getValue() == null)
        {
            Alert diffAlert = new Alert(Alert.AlertType.WARNING);
            diffAlert.setTitle("Please notice");
            diffAlert.setHeaderText(null);
            diffAlert.setContentText("You didnt choose any difficulty  for your maze" + "\n" + "Do you want to proceed with the default difficulty level?");
            ButtonType nextButtonType = new ButtonType("NEXT");
            diffAlert.getButtonTypes().setAll(nextButtonType);
            Button nextButton = (Button) diffAlert.getDialogPane().lookupButton(nextButtonType);
            nextButton.setOnAction(ActionEvent -> {difficultyComboBox.setValue("Medium");});
            diffAlert.showAndWait();
        }
        String diffLevel = (String) difficultyComboBox.getValue();
        Maze m = (Maze) model.GenerateMaze(rows, cols, diffLevel);
        if(playerSelection == 0)
        {
            Alert playerSelectionlert = new Alert(Alert.AlertType.WARNING);
            playerSelectionlert.setTitle("Please notice");
            playerSelectionlert.setHeaderText(null);
            playerSelectionlert.setContentText("You didnt choose any character to play with" + "\n" + "Do you want to proceed with the default one?");
            ButtonType nextButtonType = new ButtonType("NEXT");
            playerSelectionlert.getButtonTypes().setAll(nextButtonType);
            Button nextButton = (Button) playerSelectionlert.getDialogPane().lookupButton(nextButtonType);
            nextButton.setOnAction(actionEvent1 -> {playGround.drawMaze(m,2);
            } );
            playerSelectionlert.showAndWait();
        }
        makeUnvisible();
        primaryStage.setMaximized(true);
        playGround.drawMaze(m,playerSelection);
       startBackGroundMusic();
    }

    public void setPrimaryStage(Stage primary){
        this.primaryStage = primary;
    }

    public void returnToHomeScreen()
    {
        Parent root = null;
        if(primaryStage == null)
        {
            primaryStage = KeyBoardController.getInstance().primaryStage;
        }
        try {
            root = FXMLLoader.load(getClass().getResource("MyView.fxml"));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        Scene openingScene= new Scene(root, 600, 600);
        primaryStage.setScene(openingScene);
        primaryStage.show();
    }
    @Override
    public void solve(ActionEvent actionEvent) {
        playGround.drawSolution(model.showPossibleSolution());
    }
    @Override
    public void newOption(ActionEvent e) throws Exception{
        Alert newGameAlert;
        if(!e.getSource().equals(playAgainButton) && !Start.isVisible())
        {
            newGameAlert = new Alert(Alert.AlertType.CONFIRMATION);
            newGameAlert.setTitle("New Game");
            newGameAlert.setHeaderText(null);
            newGameAlert.setContentText("Dont forget to save your current game :)" + "\n" + "Do you want to save or to return to the home screen?");
            ButtonType yesButtonType = new ButtonType("YES");
            ButtonType noButtonType = new ButtonType("NO");
            newGameAlert.getButtonTypes().setAll(yesButtonType, noButtonType);
            Button yesButton = (Button) newGameAlert.getDialogPane().lookupButton(yesButtonType);
            yesButton.setOnAction(saveItem.getOnAction());
            Button noButton = (Button) newGameAlert.getDialogPane().lookupButton(noButtonType);
            newGameAlert.showAndWait();
            backGroundMusic.stop();
            returnToHomeScreen();
        }
        else{
            if (KeyBoardController.getInstance().openedWindow)
            {
                KeyBoardController.getInstance().newWindow.close();
                KeyBoardController.getInstance().mediaPlayer.stop();
                KeyBoardController.getInstance().openedWindow = false;
            }
            newGameAlert = new Alert(Alert.AlertType.INFORMATION);
            newGameAlert.setTitle("New Game");
            newGameAlert.setHeaderText(null);
            newGameAlert.setContentText("Have fun!");
            newGameAlert.showAndWait();
        }
        returnToHomeScreen();

    }
    @Override
    public void saveOption(ActionEvent event) {
        File selectedDirectory = directoryChooser.showDialog(primaryStage);
        if (selectedDirectory != null) {
            // Handle the selected directory
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(selectedDirectory);
            fileChooser.setTitle("Save File");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
            File selectedFile = fileChooser.showSaveDialog(primaryStage);
            if (selectedFile != null) {
                // Perform the save operation
                model.saveContent(selectedFile,whichCharacter());
                Alert saveAlert = new Alert(Alert.AlertType.CONFIRMATION);
                saveAlert.setTitle("Save Complete");
                saveAlert.setHeaderText(null);
                saveAlert.setContentText("Your progress has been successfully saved");
                saveAlert.showAndWait();
            }
        }
    }
    @Override
    public void loadOption(ActionEvent event){
        Alert loadAlert;
        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        if (selectedFile != null) {
            // Perform the load operation
            Object[] content = model.loadContent(selectedFile);
            if (content != null) {
                GraphicsContext mazeImage = MazeDisplay.getMazeImage();
                if(mazeImage != null) {
                    mazeImage.clearRect(0, 0, mazeImage.getCanvas().getWidth(), mazeImage.getCanvas().getHeight());
                }
                makeUnvisible();
                playGround.drawMaze((Maze) content[0],(int) content[1]);
                loadAlert = new Alert(Alert.AlertType.CONFIRMATION);
                loadAlert.setTitle("Load complete");
                loadAlert.setHeaderText(null);
                loadAlert.setContentText("Here is your save enjoy!");
                loadAlert.showAndWait();
            } else {
                loadAlert = new Alert(Alert.AlertType.ERROR);
                loadAlert.setTitle("Found an error");
                loadAlert.setHeaderText(null);
                loadAlert.setContentText("Sorry to inform you but we couldn't load the desirable file");
                loadAlert.showAndWait();
            }
        }
    }
    @Override
    public void propertiesOption(ActionEvent event){
        Stage newPropWindow = new Stage();
        newPropWindow.setTitle("Game Properties");
        String[] prop = model.getGameProperties();
        Text textNode = new Text("Game Properties :" + "\n" + "Generate Algorithm : " + prop[0] + "\n" + "Solution Algorithm : " + prop[1] + "\n" + "Number Of Threads : " + prop[2]);
        VBox fileBox = new VBox(textNode);
        Scene fileScene = new Scene(fileBox, 400, 300);
        newPropWindow.setScene(fileScene);
        if(!allAppStages.contains(newPropWindow)) {
            allAppStages.add(newPropWindow);
        }
        newPropWindow.show();
    }

    public void exitAppMenuOption(ActionEvent event){
        Alert exitGameAlert = new Alert(Alert.AlertType.CONFIRMATION);
        exitGameAlert.setTitle("Exit");
        exitGameAlert.setHeaderText(null);
        exitGameAlert.setContentText("Do you want to save your progress before leaving?");
        ButtonType yesButtonType = new ButtonType("YES");
        ButtonType noButtonType = new ButtonType("NO");
        exitGameAlert.getButtonTypes().setAll(yesButtonType, noButtonType);
        Button yesButton = (Button) exitGameAlert.getDialogPane().lookupButton(yesButtonType);
        yesButton.setOnAction(saveItem.getOnAction());
        Button noButton = (Button) exitGameAlert.getDialogPane().lookupButton(noButtonType);
        exitGameAlert.showAndWait();
        Parent root = null;
        if(KeyBoardController.getInstance().openedWindow)
        {
            KeyBoardController.getInstance().newWindow.close();
            KeyBoardController.getInstance().mediaPlayer.stop();
            KeyBoardController.getInstance().openedWindow = false;
        }
        Platform.exit();
        for(Stage stage : allAppStages)
        {
            stage.close();
        }
        Platform.exit();
    }
    public void helpAppMenuOption(Event event){
        Stage helpStage = new Stage();
        helpStage.setTitle("Help");
        Text textNode = new Text("Welcome to Dvir's and Ido's amazing maze" + "\n" + "The rules are : " + "\n" + "1. You csn only move " +
                "right (By pressing 6), left (By pressing 4), up (By pressing 8) and down (By pressing 2)" + "\n" + "" +
                "you can also move on which diagonal you want ! (Bottom left by pressing 1, Bottom right by pressing 3, Top left " +
                "by pressing 7 and Top left by pressing 9" + "\n" + "2. You can only move one step by one pressing " + "\n" +
                "3. You can't move to a cell that look like a wall" + "\n" + "" +
                "4.You also have an option to cheat (not recommended) by pressing the show solution button" + "\n" +
                "5.On the menu bar you have the option to save the current progress you just made and of course you can also load one :) " + "\n" +
                "6. Have fun!!!");
        VBox layout = new VBox(textNode);
        Scene scene = new Scene(layout, 200, 200);
        helpStage.setScene(scene);
        if(!allAppStages.contains(helpStage)) {
            allAppStages.add(helpStage);
        }
        helpStage.show();
    }

    public void aboutAppMenuOption(ActionEvent event){
        Stage aboutStage = new Stage();
        aboutStage.setTitle("About the App");
        Text textNode = new Text("Welcome to Dvir's and Ido's amazing maze" + "\n" + "1. You csn only move " +
                "right (By pressing 6), left (By pressing 4), up (By pressing 8) and down (By pressing 2)" + "\n" + "" +
                "you can also move on which diagonal you want ! (Bottom left by pressing 1, Bottom right by pressing 3, Top left " +
                "by pressing 7 and Top left by pressing 9" + "\n" + "2. You can only move one step by one pressing " + "\n" +
                "3. You can't move to a cell that look like a wall" + "\n" + "" +
                "4.You also have an option to cheat (not recommended) by pressing the show solution button" + "\n" +
                "5.On the menu bar you have the option to save the current progress you just made and of course you can also load one :) " + "\n" +
                "6. Have fun!!!");
        VBox layout = new VBox(textNode);
        Scene scene = new Scene(layout, 300, 300);
        aboutStage.setScene(scene);
        if(!allAppStages.contains(aboutStage)) {
            allAppStages.add(aboutStage);
        }
            aboutStage.show();
    }
    public void keyPressed(KeyEvent keyEvent) {
        KeyBoardController.getInstance().keyHandle(keyEvent, playGround);

    }

    public void startBackGroundMusic()
    {
        Media media = null;
        try {
            media = new Media(getClass().getResource("/View/Resources/BlackAndYellow.mp3").toURI().toString());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        backGroundMusic = new MediaPlayer(media);
        backGroundMusic.setOnReady(() -> {
            backGroundMusic.play();
        });
    }

    public void stopBackGroundMusic()
    {
       backGroundMusic.stop();
    }
    public void mouseClicks(MouseEvent mouseEvent) {
      playGround.requestFocus();
    }

    public void keyReleased(KeyEvent keyEvent) {
        try {
            KeyBoardController.getInstance().handleKeyRelease();
        }
        catch (Exception e)
        {
        }
    }

    public void makeUnvisible()
    {
        Start.setVisible(false);
        Solution.setVisible(true);
        difficultyComboBox.setVisible(false);
        rightRadioButton.setVisible(false);
        centerRadioButton.setVisible(false);
        leftRadioButton.setVisible(false);
        leftImage.setVisible(false);
        centerImage.setVisible(false);
        rightImage.setVisible(false);
        difficultyLabel.setVisible(false);
        rowsLabel.setVisible(false);
        colsLabel.setVisible(false);
        textField_mazeRows.setVisible(false);
        textField_mazeColumns.setVisible(false);

    }

    public void rightButtonClicked(ActionEvent actionEvent) {
            Media media = null;
            MediaPlayer  mediaPlayer;
            try {
                media = new Media(getClass().getResource("/View/Resources/AreYouReadyKids.mp3").toURI().toString());
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setOnReady(() -> {
                mediaPlayer.play();
            });
            radioButtonsPressed[0] = false;
            radioButtonsPressed[1] = false;
            radioButtonsPressed[2] = true;
            leftRadioButton.setSelected(false);
            centerRadioButton.setSelected(false);
        }


    public void leftButtonClicked(ActionEvent actionEvent) {
        Media media = null;
        MediaPlayer  mediaPlayer;
        try {
            media = new Media(getClass().getResource("/View/Resources/pacmanIntro.mp3").toURI().toString());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setOnReady(() -> {
            mediaPlayer.play();
        });
        radioButtonsPressed[0] = true;
        radioButtonsPressed[1] = false;
        radioButtonsPressed[2] = false;
        centerRadioButton.setSelected(false);
        rightRadioButton.setSelected(false);
    }
    public void centerButtonClicked(ActionEvent actionEvent) {
        Media media = null;
        MediaPlayer  mediaPlayer;
        try {
            media = new Media(getClass().getResource("/View/Resources/pikachuSound.mp3").toURI().toString());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setOnReady(() -> {
            mediaPlayer.play();
        });
        radioButtonsPressed[0] = false;
        radioButtonsPressed[1] = true;
        radioButtonsPressed[2] = false;
        leftRadioButton.setSelected(false);
        rightRadioButton.setSelected(false);
    }

    public int whichCharacter()
    {
        if(radioButtonsPressed[0]){

            return 1;
        }
        if(radioButtonsPressed[1]){
            return 2;
        }
        if(radioButtonsPressed[2]) {
            return 3;
        }
        return 0;
    }

    public void mousePressed(MouseEvent e) {
        xPosOnPressed = e.getX();
        yPosOnPressed = e.getY();
        startOffSetX = (int) (xPosOnPressed / playGround.cellWidth.intValue());
        startOffSetY = (int) (yPosOnPressed / playGround.cellHeight.intValue());
    }

    public void mouseScrolled(ScrollEvent e) {
        if(e.isControlDown()) {
            double ZOOM_FACTOR = 1.1;
            double zoomFactor = e.getDeltaY() > 0 ? ZOOM_FACTOR : 1 / ZOOM_FACTOR;
            playGround.setScaleX(playGround.getScaleX() * zoomFactor);
            playGround.setScaleY(playGround.getScaleY() * zoomFactor);
            e.consume();
        }
    }


    public void mouseReleased(MouseEvent e) {
        if(MyViewModel.getInstance().isDone())
        {
            KeyBoardController.getInstance().handleKeyRelease();
        }
        else {
            int cellHeight = playGround.cellHeight.intValue();
            int cellWidth = playGround.cellWidth.intValue();
            double deltaX = e.getX() - xPosOnPressed;
            double deltaY = e.getY() - yPosOnPressed;
            int newCellRow = startOffSetY + (int) (deltaY / cellHeight);
            int newCellColumn = startOffSetX + (int) (deltaX / cellWidth);
            if (newCellColumn < startOffSetX && newCellRow > startOffSetY) {
                playGround.setPosition(MyViewModel.getInstance().moveToCheck(1));
            }
            if (newCellColumn == startOffSetX && newCellRow > startOffSetY) {
                playGround.setPosition(MyViewModel.getInstance().moveToCheck(2));
            }
            if (newCellColumn > startOffSetX && newCellRow > startOffSetY) {
                playGround.setPosition(MyViewModel.getInstance().moveToCheck(3));
            }
            if (newCellColumn < startOffSetX && newCellRow == startOffSetY) {
                playGround.setPosition(MyViewModel.getInstance().moveToCheck(4));
            }
            if (newCellColumn > startOffSetX && newCellRow == startOffSetY) {
                playGround.setPosition(MyViewModel.getInstance().moveToCheck(6));
            }
            if (newCellColumn < startOffSetX && newCellRow < startOffSetY) {
                playGround.setPosition(MyViewModel.getInstance().moveToCheck(7));
            }
            if (newCellColumn == startOffSetX && newCellRow < startOffSetY) {
                playGround.setPosition(MyViewModel.getInstance().moveToCheck(8));
            }
            if (newCellColumn > startOffSetX && newCellRow < startOffSetY) {
                playGround.setPosition(MyViewModel.getInstance().moveToCheck(9));
            }
        }
    }
}
