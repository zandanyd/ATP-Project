package algorithms.mazeGenerators;

import java.io.Serializable;

public class Position implements Serializable {
    private int x;

    private int y;

    public Position(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getRowIndex(){
        return x;
    }

    public int getColumnIndex(){
        return y;
    }

    @Override
    public String toString() {
        return "{" + this.x + "," + this.y + "}";
    }

    @Override
    public boolean equals(Object obj) {
        return (this.x == ((Position) obj).x && this.y == ((Position) obj).y);
    }
}

