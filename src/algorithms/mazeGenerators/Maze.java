package algorithms.mazeGenerators;

public class Maze {
    int m_rows;
    int m_columns;
    int[][] maze;
    public Maze(int rowsNumber, int columnsNumber){
        m_columns = columnsNumber;
        m_rows = rowsNumber;
        maze = new int[m_rows][m_columns];

    }
    public void addWall(int row, int column){
        maze[row][column] = 1;
    }
    public void addHall(int row, int column){
        maze[row][column] = 0;
    }
    public void print(){

        for (int i = 0;i<m_rows; i++){
            System.out.println(" ");
            for (int j = 0; j<m_columns ; j++){
                if(i == 0 && j == 0)
                    System.out.print("S" + "  ");
                else if(i == m_rows - 1 && j == m_columns - 1)
                    System.out.println("E");
                else
                    System.out.print(maze[i][j] + "  ");
            }
        }

    }
    public int getCell(int row, int column){
        if(row >= m_rows || column >= m_columns || row < 0 || column <0){
            return -1;
        }
        return maze[row][column];
    }

    public Position getStartPosition(){
        return new Position(0,0);
    }

    public Position getGoalPosition(){
        return new Position(m_rows - 1 ,m_columns - 1);
    }




}
