import java.io.*;  
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;  

public class RegexEngine { 
    public static void main(String[] args) 
    {    
        boolean isFirstLine = true;
        // start reading input
                  
        ExpScanner expScanner = new ExpScanner();
        Parser parser = new Parser();
        
        try (Scanner scanner = new Scanner(System.in))
        {
            String RegexGrammar;
            while (!(RegexGrammar = scanner.nextLine()).isEmpty()) 
            {
                if (isFirstLine == true)
                {   
                    expScanner.passFirstLine(RegexGrammar);
                    parser.parse_regex(expScanner.regexInArrList);
                    isFirstLine = false;
                }
            }

        } 
    }
}