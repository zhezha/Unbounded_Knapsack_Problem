package UnboundedKnapsackProblem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Zhao Zhengyang
 */
class FileIO{

    public static List<String> readFile(String filename ) {
        
        String line = "";
        List<String> lines = new ArrayList<>();
        BufferedReader bufferedReader = null;
        
        try {
            bufferedReader = new BufferedReader(new FileReader(filename));
            
            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }
            
            if (bufferedReader != null) {
            	bufferedReader.close();
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("Unable to open file: " + filename);
        }
        catch (IOException e) {
            System.out.println("Error reading file: " + filename);
        }
        
        return lines;
    }
    
    
    public static void writeFile(String filename, String[] lines) {
    	
    	BufferedWriter bufferedWriter = null;
    	
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(filename));
            
            for (int i = 0; i < lines.length; i++){
                bufferedWriter.write(lines[i]);
            }
            
            if (bufferedWriter != null) {
            	bufferedWriter.close();
            }
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    
    }
    
}
