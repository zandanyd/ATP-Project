package Server;

import algorithms.mazeGenerators.*;
import IO.*;

import java.io.*;

public class ServerStrategyGenerateMaze implements IServerStrategy {
    @Override
    public void applyStrategy(InputStream inFromClient, OutputStream outToClient) {
        try (ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
             ObjectOutputStream toClient = new ObjectOutputStream(outToClient)) {

            int[] mazeSizes = (int[]) fromClient.readObject();
            int numOfRows = mazeSizes[0];
            int numOfColumns = mazeSizes[1];
            IMazeGenerator generator;

            if (Configurations.getInstance().getGenerateAlgorithmName().equals("SimpleMazeGenerator")) {
                generator = new SimpleMazeGenerator();
            } else if (Configurations.getInstance().getGenerateAlgorithmName().equals("EmptyMazeGenerator")) {
                generator = new EmptyMazeGenerator();
            } else {
                generator = new MyMazeGenerator();
            }

            Maze mazeFromClient = generator.generate(numOfRows, numOfColumns);
            byte[] compressedMaze = CompressMaze(mazeFromClient.toByteArray());
            toClient.writeObject(compressedMaze);
            toClient.flush();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private byte[] CompressMaze(byte[] mazeData) {
        try (ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
             MyCompressorOutputStream compressor = new MyCompressorOutputStream(byteOut)) {
            compressor.write(mazeData);
            compressor.flush();
            return byteOut.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Failed to compress maze data.", e);
        }
    }
}
