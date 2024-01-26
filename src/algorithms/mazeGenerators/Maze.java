package algorithms.mazeGenerators;

import java.io.Serializable;
import java.util.ArrayList;

public class Maze implements Serializable {
    int m_rows;
    int m_columns;
    int[][] maze;
    public Maze(int rowsNumber, int columnsNumber){
        m_columns = columnsNumber;
        m_rows = rowsNumber;
        maze = new int[m_rows][m_columns];

    }

    public int getColumns() {
        return m_columns;
    }
    public int getRows(){
        return m_rows;
    }

    public Maze(byte[] bytes) {
        int rows = 0;
        int col = 0;
        int index = 0;
        while (bytes[index] == (byte) 127) {
            rows += 127;
            index++;
        }
        rows += bytes[index];
        this.m_rows = rows;
        index++;
        while (bytes[index] == (byte) 127) {
            col += 127;
            index++;
        }
        col += bytes[index];
        this.m_columns = col;
        index++;
        maze = new int[m_rows][m_columns];
        for (int i = 0; i < m_rows; i++) {
            for (int j = 0; j < m_columns; j++) {
                if (bytes[index]==(byte) 0) {
                    addHall(i,j);
                }
                else {
                    addWall(i,j);
                }
                index++;
            }
        }
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

    public byte[] toByteArray(){
        ArrayList<Byte> newByteArr = new ArrayList<>();
        double cellsForColumns = Math.ceil(this.m_columns / 127);
        double cellsForRows = Math.ceil(this.m_rows / 127);
        for(int i = 0; i < cellsForRows; i++){
            newByteArr.add(Byte.MAX_VALUE);
        }
        newByteArr.add((byte) (this.m_rows % 127) );
        for(int i = 0; i < cellsForColumns; i++){
                newByteArr.add((byte) 127 );
        }
        newByteArr.add((byte) (this.m_rows % 127) );
        for(int i = 0; i < this.m_rows; i++){
            for (int k = 0; k < this.m_columns; k++){
                newByteArr.add((byte) this.getCell(i,k));
            }
        }
        byte[] mazeToByteArr = new byte[newByteArr.size()];
        for(int i = 0; i < newByteArr.size(); i++){
            mazeToByteArr[i] = newByteArr.get(i);
        }
        return mazeToByteArr;
    }

}
