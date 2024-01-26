package Model;
import Client.IClientStrategy;
import IO.MyDecompressorInputStream;
import Server.Configurations;
import algorithms.mazeGenerators.*;
import algorithms.search.*;
import Server.*;
import Client.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.tools.Generate;
import test.RunCommunicateWithServers;


import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.LinkOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;


public class MyModel implements IModel{

    private static MyModel single_instance = null;
    private static final Logger LOG = LogManager.getLogger(); //Log4j2

    // map of all the Branches in the company
    private static Maze currMaze;

    public Position currPos;
    public Solution solvedMaze;
    private final String URL = "resources/config.properties";


    private MyModel() {
    }
    public static synchronized MyModel getInstance() {
        if(single_instance == null){
            single_instance =  new MyModel();
        }
        return single_instance;
    }
    public Maze createPlayGround(int row, int col, String diff){
        setDifficultyLevel(diff);
        Server mazeGeneratingServer = new Server(5404, 1000, new ServerStrategyGenerateMaze());
        LOG.info(mazeGeneratingServer.toString() + " Starting server at port = " + 5404);
        mazeGeneratingServer.start();
        try{
                Client client = new Client(InetAddress.getLocalHost(), 5404, new IClientStrategy() {
                @Override
                public void clientStrategy(InputStream inFromServer,
                                           OutputStream outToServer) {
                    try {
                        ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                        ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                        toServer.flush();
                        int[] mazeDimensions = new int[]{row, col};
                        toServer.writeObject(mazeDimensions); //send maze dimensions to server
                        toServer.flush();
                        byte[] compressedMaze = (byte[]) fromServer.readObject();
                        InputStream is = new MyDecompressorInputStream(new ByteArrayInputStream(compressedMaze));
                        byte[] decompressedMaze = new byte[250200/*CHANGESIZE ACCORDING TO YOU MAZE SIZE*/]; //allocating byte[] for the decompressed maze -
                        is.read(decompressedMaze); //Fill decompressedMaze 25 | P a g e with bytes

                        currMaze = new Maze(decompressedMaze);
                        currPos = new Position(0,0);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            client.communicateWithServer();
            LOG.info("Client accepted: " + client.toString());
            LOG.info("Created new maze with - " + currMaze.getRows() + " rows and " + currMaze.getColumns() + " columns");
        } catch (UnknownHostException e) {
            LOG.error("Unknown Host Exception: " + e);
            e.printStackTrace();
        }
        LOG.info("Stopping server... " );
        mazeGeneratingServer.stop();
        return currMaze;
    }

    public static Object getCurrModel()
    {
        return currMaze;
    }

    public void setDifficultyLevel(String lev){
        try (OutputStream output = new FileOutputStream(URL)) {
            Properties prop = new Properties();
            // get the property value and print it out
            if(lev.equals("Medium"))
            {
                lev = "SimpleMazeGenerator";
                prop.setProperty("GenerateAlgorithmName", lev);
            }
            if(lev.equals("Hard"))
            {
                lev = "MyMazeGenerator";
                prop.setProperty("GenerateAlgorithmName", lev);
            }
            prop.setProperty("SearchAlgorithmName", "BestFirstSearch");
            prop.setProperty("threadsPoolSize", "10");
            prop.store(output,null);

        } catch (IOException ex) {

        }
    }

    public Solution showPossibleSolution(){
//        Server solveSearchProblemServer = new Server(5401, 1000, new ServerStrategySolveSearchProblem());
//        solveSearchProblemServer.start();
//        SearchableMaze searchableMaze = new SearchableMaze(currMaze);
//        ASearchingAlgorithm bestFirst = new BestFirstSearch();
//        Solution sol = bestFirst.solve(searchableMaze);
//        return sol;
        Server solveServer = new Server(5405, 1000, new ServerStrategySolveSearchProblem());
        LOG.info(solveServer.toString() + "Starting server at port: " + 5405);
        solveServer.start();
        try {
            Client solveClient = new Client(InetAddress.getLocalHost(), 5405, new IClientStrategy() {
                @Override
                public void clientStrategy(InputStream inFromServer,
                                           OutputStream outToServer) {
                    try {
                        ObjectOutputStream toServer = new
                                ObjectOutputStream(outToServer);
                        ObjectInputStream fromServer = new
                                ObjectInputStream(inFromServer);
                        toServer.flush();
                        Maze maze = currMaze;
                        toServer.writeObject(maze); //send maze to server
                        toServer.flush();
                        solvedMaze = (Solution) fromServer.readObject();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            solveClient.communicateWithServer();
            LOG.info(solveServer.toString() + "Client accepted : " + solveClient.toString());
            LOG.info(solveClient.toString() + "Client requested for a maze solution");
        }catch (Exception e)
        {
            LOG.error("Unknown Host Exception: " + e);
        }
        LOG.info(solveServer.toString() + " Stopping sever...");
        solveServer.stop();
        return solvedMaze;
    }
    public Object makeAMove(Object move){
        int step = (Integer)move;
        if(step == 1 && checkValidStep(1)){
            if(currMaze.getCell(currPos.getRowIndex() + 1,currPos.getColumnIndex() - 1) != 1 && (currMaze.getCell(currPos.getRowIndex(),currPos.getColumnIndex() - 1) != 1 || currMaze.getCell(currPos.getRowIndex() + 1,currPos.getColumnIndex()) != 1)){
                currPos = new Position(currPos.getRowIndex() + 1,currPos.getColumnIndex() - 1);
                return currPos;
            }
            return currPos;
        }
        if(step == 2 && checkValidStep(2)){
            if(currMaze.getCell(currPos.getRowIndex() + 1,currPos.getColumnIndex()) != 1){
                currPos = new Position(currPos.getRowIndex() + 1,currPos.getColumnIndex());
                return currPos;
            }
            return currPos;
        }
        if(step == 3 && checkValidStep(3)){
            if(currMaze.getCell(currPos.getRowIndex() + 1,currPos.getColumnIndex() + 1) != 1 && (currMaze.getCell(currPos.getRowIndex(),currPos.getColumnIndex() + 1) != 1 || currMaze.getCell(currPos.getRowIndex() + 1,currPos.getColumnIndex()) != 1)){
                currPos = new Position(currPos.getRowIndex() + 1,currPos.getColumnIndex() + 1);
                return currPos;
            }
            return currPos;
        }
        if(step == 4 && checkValidStep(4)){
            if(currMaze.getCell(currPos.getRowIndex(),currPos.getColumnIndex() - 1) != 1){
                currPos = new Position(currPos.getRowIndex(),currPos.getColumnIndex() - 1);
                return currPos;
            }
            return currPos;
        }
        if(step == 6 && checkValidStep(6)){
            if(currMaze.getCell(currPos.getRowIndex(),currPos.getColumnIndex() + 1) != 1){
                currPos = new Position(currPos.getRowIndex(),currPos.getColumnIndex() + 1);
                return currPos;
            }
            return currPos;
        }
        if(step == 7 && checkValidStep(7)){
            if(currMaze.getCell(currPos.getRowIndex() - 1,currPos.getColumnIndex() - 1) != 1 && (currMaze.getCell(currPos.getRowIndex() ,currPos.getColumnIndex() - 1) != 1 || currMaze.getCell(currPos.getRowIndex() - 1,currPos.getColumnIndex()) != 1)){
                currPos = new Position(currPos.getRowIndex() - 1,currPos.getColumnIndex() - 1);
                return currPos;
            }
            return currPos;
        }
        if(step == 8 && checkValidStep(8)){
            if(currMaze.getCell(currPos.getRowIndex() - 1,currPos.getColumnIndex()) != 1){
                currPos = new Position(currPos.getRowIndex() - 1,currPos.getColumnIndex());
                return currPos;
            }
            return currPos;
        }
        if(step == 9 && checkValidStep(9)){
            if(currMaze.getCell(currPos.getRowIndex() - 1,currPos.getColumnIndex() + 1) != 1 && (currMaze.getCell(currPos.getRowIndex() - 1,currPos.getColumnIndex()) != 1 || currMaze.getCell(currPos.getRowIndex(),currPos.getColumnIndex()) + 1 != 1)){
                currPos = new Position(currPos.getRowIndex() - 1,currPos.getColumnIndex() + 1);
                return currPos;
            }
            return currPos;
        }
        return currPos;
    }

    @Override
    public void save(File file, int opt) {
        Object[] currStateMaze = new Object[3];
        currStateMaze[0] = currMaze;
        currStateMaze[1] = currPos;
        currStateMaze[2] = opt;
        try (FileOutputStream fileOut = new FileOutputStream(file);
             ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {
            objectOut.writeObject(currStateMaze);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Object[] load(File file) {
        Object[] fromFile = new Object[2];
        try (FileInputStream fileIn = new FileInputStream(file);
             ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {
            Object[] objFromFile = (Object[]) objectIn.readObject();
            Maze mazeFromFile = (Maze) objFromFile[0];
            Position playerPosition = (Position) objFromFile[1];
            int playerNumber = (int) objFromFile[2];
            fromFile[1] = playerNumber;
            if (mazeFromFile != null){
                currMaze = mazeFromFile;
                fromFile[0] = currMaze;
            }
            if (playerPosition != null){
                currPos = playerPosition;
            }
            } catch (Exception ex) {
            ex.printStackTrace();
        }
        return fromFile;
    }
    public Object getPlayerPosition(){
        return currPos;
    }

    public boolean isDone()
    {
        return currMaze.getGoalPosition().equals(currPos);
    }

    public boolean checkValidStep(int step)
    {

        if(step == 7){
            if(currPos.getColumnIndex() - 1 < 0 || currPos.getRowIndex() - 1 < 0){
                return false;
            }
        }
        if(step == 2){
            if(currPos.getRowIndex() + 1 > currMaze.getRows() - 1){
                return false;
            }
        }
        if(step == 9){
            if(currPos.getRowIndex() - 1 < 0 || currPos.getColumnIndex() + 1 > currMaze.getColumns() - 1){
                return false;
            }
        }
        if(step == 4){
            if(currPos.getColumnIndex() - 1 < 0){
                return false;
            }
        }
        if(step == 6){
            if(currPos.getColumnIndex() + 1 > currMaze.getColumns() - 1){
                return false;
            }
        }
        if(step == 1){
            if(currPos.getColumnIndex() - 1 < 0 || currPos.getRowIndex() + 1 > currMaze.getRows() - 1){
                return false;
            }
        }
        if(step == 8){
            if(currPos.getRowIndex() - 1 < 0){
                return false;
            }
        }
        if(step == 3){
            if(currPos.getColumnIndex() + 1 > currMaze.getColumns() - 1 || currPos.getRowIndex() + 1 > currMaze.getRows() - 1){
                return false;
            }
        }
        return true;
    }

    public String[] getProperties()
    {
        String[] allProperties = new String[3];
        allProperties[0] = Configurations.getInstance().getGenerateAlgorithmName();
        allProperties[1] = Configurations.getInstance().getSearchAlgorithmName();
        int numOfThreads = Configurations.getInstance().getNumOfThreadsPool();
        allProperties[2] = Integer.toString(numOfThreads);
        return allProperties;
    }
}
