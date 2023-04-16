package algorithms.mazeGenerators;

public abstract class AMazeGenerator implements IMazeGenerator{
    @Override
    public abstract Maze generate(int rowsNumber, int columnsNumber) ;

    // method receives the maze size and returns the time of generation of the maze
    public long measureAlgorithmTimeMillis(int rowsNumber, int columnsNumber){
        long start = System.currentTimeMillis();
        this.generate(rowsNumber, columnsNumber);
        long end = System.currentTimeMillis();
//        System.out.println(start + " " + end);
        return end - start;

    }

}
