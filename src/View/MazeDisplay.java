package View;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
import ViewModel.MyViewModel;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
import algorithms.search.AState;
import algorithms.search.MazeState;
import algorithms.search.Solution;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;


public class MazeDisplay extends Canvas  {

    private Maze maze;
    private MyViewModel model = MyViewModel.getInstance();
    StringProperty player = new SimpleStringProperty();
    public Position currPlayerPosition = new Position(0,0);
    public GraphicsContext graphicsContext;
    public Solution sol;
    public ArrayList<Position> solArray = new ArrayList<>();
    public int offsetX, offsetY;
    private static GraphicsContext mazeImage = null;
    public Boolean solutionDisplayed = false;
    public String getPlayer(){
        return player.get();
    }
    public void setPlayer(String image){
        player.set(image);
    }
    public void setModel(MyViewModel m){
        this.model = m;
    }
    public Double cellHeight;
    public Double cellWidth;
    public void drawMaze(Object maze, int opt) {
        switch (opt) {
            case 1:
                setPlayer("./src/View/Resources/player.png");
                break;
            case 2:
                setPlayer("./src/View/Resources/player1.png");
                break;
            case 3:
                setPlayer("./src/View/Resources/player2.png");
                break;
        }
        solutionDisplayed = false;
        this.maze =(Maze) maze;
        draw();

    }
    public void drawSolution(Object s) {
        solutionDisplayed = true;
        if (maze != null) {
            sol = (Solution) s;
            for (AState pos : sol.getSolutionPath()) {
                MazeState mState = (MazeState) pos;
                Position posFromSol = (Position) mState.getState();
                if(maze.getGoalPosition().equals(posFromSol))
                {
                    break;
                }
                solArray.add(posFromSol);
            }
            draw();
        }
    }
    private void draw() {

        if(maze != null){
            double canvasHeight = getHeight();
            double canvasWidth = getWidth();
            int rows = maze.getRows();
            int cols = maze.getColumns();
            cellHeight = canvasHeight / rows;
            cellWidth = canvasWidth / cols;

            graphicsContext = getGraphicsContext2D();
            //clear the canvas:
            graphicsContext.clearRect(0, 0, canvasWidth, canvasHeight);
            graphicsContext.setFill(Color.YELLOW);
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    if(maze.getCell(i,j) == 1){
                        //if it is a wall:
                        double x = j * cellWidth;
                        double y = i * cellHeight;
                        graphicsContext.fillRect(x, y, cellWidth, cellHeight);
                    }
                }
            }
            Image playerTwoGoalPos = null;
            graphicsContext.setFill(Color.BLACK);
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    if(maze.getCell(i,j) == 0){
                        if(solArray.contains(new Position(i,j)))
                        {
                            graphicsContext.setFill(Color.LIGHTGREEN);
                            double x = j * cellWidth;
                            double y = i * cellHeight;
                            graphicsContext.fillRect(x, y, cellWidth, cellHeight);
                            graphicsContext.setFill(Color.BLACK);
                            continue;
                        }
                        if(i == rows - 1 && j == cols - 1 && getPlayer().equals("./src/View/Resources/player.png"))
                        {
                            graphicsContext.setFill(Color.RED);
                        }
                        else if(i == rows - 1 && j == cols - 1 && getPlayer().equals("./src/View/Resources/player1.png"))
                        {
                            double w = j * cellWidth;
                            double h = i * cellHeight;
                            playerTwoGoalPos = new Image("/View/Resources/Pokeball.png");
                            graphicsContext.fillRect(w, h, cellWidth, cellHeight);
                            graphicsContext.drawImage(playerTwoGoalPos , w - 1, h - 1, cellWidth, cellHeight);
                            continue;
                        }
                        else if(i == rows - 1 && j == cols - 1 && getPlayer().equals("./src/View/Resources/player2.png"))
                        {
                            double w = j * cellWidth;
                            double h = i * cellHeight;
                            playerTwoGoalPos = new Image("/View/Resources/CrispyCraby.jpg");
                            graphicsContext.fillRect(w, h, cellWidth, cellHeight);
                            graphicsContext.drawImage(playerTwoGoalPos , w - 1, h - 1, cellWidth, cellHeight);
                            continue;
                        }
                        //if it is a wall:
                        double x = j * cellWidth;
                        double y = i * cellHeight;
                        graphicsContext.fillRect(x, y, cellWidth, cellHeight);
                    }
                }
            }
            double h_p = 0;
            double w_p = 0;
            Image image = null;
            currPlayerPosition = (Position) model.getPlayerPosition();
            w_p = cellWidth * currPlayerPosition.getColumnIndex();
            h_p = cellHeight * currPlayerPosition.getRowIndex();
            try {
                image = new Image(new FileInputStream(getPlayer()));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            graphicsContext.drawImage(image, w_p,h_p , cellWidth, cellHeight);
            mazeImage = graphicsContext;
        }
    }



    public void setPosition(Object newPos)
    {
        this.currPlayerPosition = (Position) newPos;
        if(solutionDisplayed)
        {
            drawSolution(sol);
        }
        else {
            draw();
        }
    }


    public static GraphicsContext getMazeImage()
    {
        return mazeImage;
    }
}
