import java.io.*;  
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RegexEngine { 
    public static void main(String[] args) 
    {    
        boolean isFirstLine = true;
        // start reading input
        try 
        {              
            BufferedReader bufferReader = new BufferedReader(new InputStreamReader(System.in));
            
            ExpScanner expScanner = new ExpScanner();

            // while input is no ctrl-c
            while ((bufferReader.readLine()) != null)
            {
                // String input_line;
                // input_line = bufferReader.readLine();
    
                if (isFirstLine == true)
                {   
                    if (bufferReader.readLine().isEmpty())
                    {
                        System.out.println("Incorrect input format, input can only be a-Z, 0-9, |, *, +, bracket");
                        System.exit(1);
                    }
                    expScanner.passFirstLine(bufferReader.readLine());
                }
                isFirstLine = false;
            }
        
        } catch (IOException e) {
            System.out.println("Engine stop.");
            System.exit(1);
        } 
    }
}