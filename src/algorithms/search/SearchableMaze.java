package algorithms.search;

import java.util.ArrayList;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;

public class SearchableMaze implements ISearchable{
    Maze maze;
    public SearchableMaze(Maze maze){
        this.maze = maze;
    }
    @Override
    public AState getStartState() {
        if (maze == null){
            return null;
        }
        return new MazeState(maze.getStartPosition()) ;

    }

    @Override
    public AState getGoalState() {
        if (maze == null){
            return null;
        }
        return new MazeState(maze.getGoalPosition()) ;
    }

    @Override
    // this method receives a state and checks every option of positioning and progressing from the current state
    public ArrayList<AState> getAllStates(AState state) {
        ArrayList<AState> list = new ArrayList<>();
        Position position =  (Position) (state.getState());
        int x = position.getRowIndex();
        int y = position.getColumnIndex();
        if(maze.getCell(x-1,y-1) ==0 && (maze.getCell(x-1,y) ==0 || maze.getCell(x,y-1) ==0)){
            list.add(new MazeState(new Position(x-1, y-1), 15));}
        if(maze.getCell(x+1,y-1) ==0 && (maze.getCell(x+1,y) ==0 || maze.getCell(x,y-1) ==0)){
            list.add(new MazeState(new Position(x+1, y-1), 15));}
        if(maze.getCell(x-1,y+1) ==0 && (maze.getCell(x-1,y) == 0 || maze.getCell(x,y+1) == 0)){
            list.add(new MazeState(new Position(x-1, y+1), 15));}
        if(maze.getCell(x+1,y+1) == 0 && (maze.getCell(x+1,y) == 0 || maze.getCell(x,y+1) == 0)){
            list.add(new MazeState(new Position(x+1, y+1), 15));}
        if(maze.getCell(x+1,y) == 0){
            list.add(new MazeState(new Position(x+1, y), 10));}
        if(maze.getCell(x,y+1) == 0){
            list.add(new MazeState(new Position(x, y+1), 10));}
        if(maze.getCell(x-1,y) == 0){
            list.add(new MazeState(new Position(x-1, y), 10));}
        if(maze.getCell(x,y-1) == 0){
            list.add(new MazeState(new Position(x, y-1), 10));}

        return list;
    }
}
