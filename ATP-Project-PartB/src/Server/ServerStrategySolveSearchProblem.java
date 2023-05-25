package Server;
import algorithms.search.*;
import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.Stream;

public class ServerStrategySolveSearchProblem implements IServerStrategy{

    private final File FolderDir = new File(System.getProperty("java.io.tmpdir"));

    public void applyStrategy(InputStream inFromClient, OutputStream outToClient) {
        try (ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
             ObjectOutputStream toClient = new ObjectOutputStream(outToClient)) {
            boolean solFound = false;
            Solution solution = null;
            Maze maze = (Maze) fromClient.readObject();
            Solution isExistedSol = ReadObjectFromFile(maze);
            if (isExistedSol != null) {
                solFound = true;
                solution = isExistedSol;
            }
            if (!solFound) {
                ASearchingAlgorithm searchAlgorithm = getSearchAlgorithm();
                SearchableMaze searchableMaze = new SearchableMaze(maze);
                String searchName = Configurations.getInstance().getSearchAlgorithmName();
                solution = searchAlgorithm.solve(searchableMaze);
                Object[] mazeAndSol = new Object[]{maze, solution, searchName};
                WriteObjectToFile(mazeAndSol);
            }
            toClient.writeObject(solution);
            toClient.flush();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private ASearchingAlgorithm getSearchAlgorithm() {
        String algorithmName = Configurations.getInstance().getSearchAlgorithmName();
        switch (algorithmName) {
            case "BestFirstSearch":
                return new BestFirstSearch();
            case "BreadthFirstSearch":
                return new BreadthFirstSearch();
            case "DepthFirstSearch":
                return new DepthFirstSearch();
            default:
                return new BestFirstSearch();
        }
    }

    public void WriteObjectToFile(Object serObj) {
        try (Stream<Path> files = Files.list(Paths.get(FolderDir.getAbsolutePath()))) {
            long count = files.count();
            //String filePath = FolderDir.getAbsolutePath() + "\\Solution_" + count;
            File newFile = new File(FolderDir, "Solution_" + count);
            try (FileOutputStream fileOut = new FileOutputStream(newFile);
                 ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {
                objectOut.writeObject(serObj);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Solution ReadObjectFromFile(Maze maze) {
        Solution existSol = null;
        try {
            File[] directoryListing = FolderDir.listFiles();
            if (directoryListing != null) {
                for (File file : directoryListing) {
                    if(file.getName().contains("Solution")) {
                        try (FileInputStream fileIn = new FileInputStream(file);
                             ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {
                            Object[] objFromFile = (Object[]) objectIn.readObject();
                            Maze mazeFromFile = (Maze) objFromFile[0];
                            String SearchName = (String) objFromFile[2];
                            if (Arrays.equals(mazeFromFile.toByteArray(), maze.toByteArray()) && SearchName.equals(Configurations.getInstance().getSearchAlgorithmName())) {
                                existSol = (Solution) objFromFile[1];
                                break;
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return existSol;
    }

}
