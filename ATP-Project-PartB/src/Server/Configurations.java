package Server;

import java.io.*;
import java.util.Properties;


public class Configurations {
    private static Configurations single_instance = null;
    // map of all the Branches in the company
    private final String URL = "resources/config.properties";
    private Configurations()  {
    }
    public static synchronized Configurations getInstance(){
        if(single_instance == null){
            single_instance =  new Configurations();
        }
        return single_instance;
    }

    public String getSearchAlgorithmName(){
        String searchAlgoName = null;
        try (InputStream input = new FileInputStream(URL)) {

            Properties prop = new Properties();

            // load a properties file
            prop.load(input);

            // get the property value and print it out
            searchAlgoName = prop.getProperty("SearchAlgorithmName");

        } catch (IOException ex) {

        }
        return searchAlgoName;
    }

    public int getNumOfThreadsPool(){
        int numOfThreadsPool = 0;
        String Num = null;
        try (InputStream input = new FileInputStream(URL)) {

            Properties prop = new Properties();

            // load a properties file
            prop.load(input);

            // get the property value and print it out
            Num = prop.getProperty("NumberOfThreadsPool");
            numOfThreadsPool = Integer.parseInt(Num);

        } catch (IOException ex) {
            numOfThreadsPool = 10;
        }
        return numOfThreadsPool;
    }

    public String getGenerateAlgorithmName(){
        String generateAlgoName = null;
        try (InputStream input = new FileInputStream(URL)) {

            Properties prop = new Properties();

            // load a properties file
            prop.load(input);

            // get the property value and print it out
            generateAlgoName = prop.getProperty("GenerateAlgorithmName");

        } catch (IOException ex) {

        }
        return generateAlgoName;
    }

    public void setSearchAlgorithmName(String name){
        try (OutputStream output = new FileOutputStream(URL)) {

            Properties prop = new Properties();


            // get the property value and print it out
            prop.setProperty("SearchAlgorithmName", name);
            prop.store(output,null);

        } catch (IOException ex) {

        }
    }

    public void setNumOfThreadsPool(int num){
        try (OutputStream output = new FileOutputStream(URL)) {

            Properties prop = new Properties();

            // get the property value and print it out
            prop.setProperty("NumberOfThreadsPool", Integer.toString(num));
            prop.store(output, null);
        } catch (IOException ex) {

        }
    }

    public void setGenerateAlgorithmName(String name){
        try (OutputStream output = new FileOutputStream(URL)) {

            Properties prop = new Properties();

            // load a properties file


            // get the property value and print it out
            prop.setProperty("GenerateAlgorithmName", name);
            prop.store(output, null);


        } catch (IOException ex) {

        }
    }



}
