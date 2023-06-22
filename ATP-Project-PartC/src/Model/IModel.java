package Model;

import algorithms.mazeGenerators.*;

import java.io.File;

public interface IModel {
    public Object createPlayGround(int row, int col, String diff);
    public Object showPossibleSolution();
    public void save(File file, int opt);
    public Object[] load(File file);
    public Object makeAMove(Object move);
    public Object getPlayerPosition();
}
