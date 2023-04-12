package algorithms.mazeGenerators;

import algorithms.mazeGenerators.AMazeGenerator;
import algorithms.mazeGenerators.EmptyMazeGenerator;
import algorithms.mazeGenerators.Maze;

import java.awt.*;
import java.util.Random;


public class SimpleMazeGenerator extends AMazeGenerator {

    @Override
    public Maze generate(int rowsNumber, int columnsNumber) {
        if (rowsNumber == 0 && columnsNumber == 0)
            return null;
        EmptyMazeGenerator emptyMaze = new EmptyMazeGenerator();
        Maze maze = emptyMaze.generate(rowsNumber, columnsNumber);
        if (columnsNumber < 2){
            return maze;
        }
        // fills every odd row with walls except from the first column of each row
        for (int i = 1; i < rowsNumber ; i = i + 2) {
            for (int j = 1; j < columnsNumber; j++) {
                maze.addWall(i, j);
            }
            Random random = new Random();
            // choose a random wall from a specific row and makes it a hall
            int randomNumber = random.nextInt(columnsNumber - 1) + 1;
            maze.addHall(i, randomNumber);
        }
        maze.addHall(rowsNumber-1,columnsNumber-1);
        return maze;


    }
}

