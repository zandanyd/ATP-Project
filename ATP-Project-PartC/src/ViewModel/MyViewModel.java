package ViewModel;
import Model.IModel;
import Model.MyModel;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class MyViewModel {

    private static MyViewModel single_instance = null;
    // map of all the Branches in the company
    private MyModel currModel = MyModel.getInstance();

    private MyViewModel() {
    }
    public static synchronized MyViewModel getInstance() {
        if(single_instance == null){
            single_instance =  new MyViewModel();
        }
        return single_instance;
    }

    public Object GenerateMaze(int rows, int cols, String diff){
        Object playground;
        playground = currModel.createPlayGround(rows,cols,diff);
        return playground;
    }

    public Object moveToCheck(int step){
       return currModel.makeAMove(step);
    }
    public Object getcurrModel(){
        return currModel;
    }

    public void saveContent(File file,int opt){
        currModel.save(file, opt);
    }

    public Object[] loadContent(File file){
       return currModel.load(file);
    }

    public void mazeProperties(){

    }
    public Object getPlayerPosition(){
        return currModel.getPlayerPosition();
    }


    public Object showPossibleSolution(){
       return currModel.showPossibleSolution();
    }


    public boolean isDone()
    {
        return currModel.isDone();
    }

    public void setGameLevel(String lev){
         currModel.setDifficultyLevel(lev);
    }

    public String[] getGameProperties()
    {
        return currModel.getProperties();
    }

}
