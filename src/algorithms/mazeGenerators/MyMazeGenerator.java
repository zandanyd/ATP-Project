package algorithms.mazeGenerators;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MyMazeGenerator extends AMazeGenerator {

    @Override
    public Maze generate(int rowsNumber, int columnsNumber) {
        if (rowsNumber == 0 && columnsNumber == 0)
            return null;
        int randomIndex = 0;
        Maze maze = new Maze(rowsNumber, columnsNumber);
        // fill up the maze with walls
        for(int i = 0; i<rowsNumber; i++){
            for (int j = 0; j< columnsNumber; j++){
                maze.addWall(i,j);}}
        List<Position> ListOfWalls = new ArrayList<>();
        maze.addHall(0,0);
        // enters the neighbors of the start position to the wall list
        Position position1 = new Position(0,1);
        if(maze.getCell(0,1) != -1){
            ListOfWalls.add(position1);
        }
        Position position2 = new Position(1,0);
        if(maze.getCell(1,0) != -1){
            ListOfWalls.add(position2);
        }
        Random random = new Random();
        while(!ListOfWalls.isEmpty()){int NeighborHalls = 0;
            // choosing a random wall from the wall list
            randomIndex = random.nextInt(ListOfWalls.size());
             Position currPoint = ListOfWalls.get(randomIndex);
             ListOfWalls.remove(randomIndex);
             // checks how many of the current wall neighbors are halls
             if(maze.getCell((currPoint.getRowIndex() + 1), currPoint.getColumnIndex()) == 0){NeighborHalls++;}
             if(maze.getCell((currPoint.getRowIndex()), currPoint.getColumnIndex() + 1) == 0){NeighborHalls++;}
             if(maze.getCell(currPoint.getRowIndex() -1 , currPoint.getColumnIndex()) == 0){NeighborHalls++;}
             if(maze.getCell((currPoint.getRowIndex()), currPoint.getColumnIndex() - 1) == 0){NeighborHalls++;}
             // checks if we should make the current wall a hall  and add his neighbors to the wall list
             if (NeighborHalls == 1){maze.addHall(currPoint.getRowIndex(),currPoint.getColumnIndex());
                 if(maze.getCell(currPoint.getRowIndex() + 1, currPoint.getColumnIndex()) == 1){Position right = new Position(currPoint.getRowIndex() + 1, currPoint.getColumnIndex());ListOfWalls.add(right);}
                 if(maze.getCell((currPoint.getRowIndex()), currPoint.getColumnIndex() + 1) == 1){Position up = new Position(currPoint.getRowIndex(), currPoint.getColumnIndex() + 1);ListOfWalls.add(up);}
                 if(maze.getCell((currPoint.getRowIndex())-1, currPoint.getColumnIndex()) == 1){Position left = new Position(currPoint.getRowIndex() - 1, currPoint.getColumnIndex());ListOfWalls.add(left);}
                 if(maze.getCell((currPoint.getRowIndex()), currPoint.getColumnIndex()  - 1) == 1){Position down = new Position(currPoint.getRowIndex(),currPoint.getColumnIndex() -1);ListOfWalls.add(down);}}}
        if(rowsNumber > 4 && columnsNumber >4 ){
            maze.addHall(rowsNumber-2, columnsNumber-1);
            maze.addHall(rowsNumber-1, columnsNumber-2);
            maze.addHall(rowsNumber-2, columnsNumber-2);
        }
        maze.addHall(rowsNumber-1, columnsNumber-1);

        return maze;
    }
}
