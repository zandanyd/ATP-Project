package algorithms.mazeGenerators;

public class EmptyMazeGenerator extends AMazeGenerator {
    @Override
    // this method receives maze sizes and geanerates an empty maze
    public  Maze generate( int rowsNumber, int columnsNumber) {
        if (rowsNumber == columnsNumber && rowsNumber == 0){
            return null;
        }
        Maze maze = new Maze(rowsNumber, columnsNumber);
        for (int i = 0 ; i< rowsNumber; i++ ){
            for (int j = 0 ; j < columnsNumber; j++){
                maze.addHall(i,j);
            }
        }
        return maze;


    }

    @Override
    public long measureAlgorithmTimeMillis(int rowsNumber, int columnsNumber) {
        return super.measureAlgorithmTimeMillis(columnsNumber, rowsNumber);
    }
}
