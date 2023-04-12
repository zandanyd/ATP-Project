package algorithms.mazeGenerators;

public interface IMazeGenerator {
    Maze generate(int rowsNumber, int columnsNumber);
    long measureAlgorithmTimeMillis(int rowsNumber, int columnsNumber);
}
