import java.util.ArrayList;
import java.util.Scanner;

public class RegexEngine { 
    public static void main(String[] args) 
    {    
        boolean isFirstLine = true;
        ArrayList<String> passed_regex;
        ArrayList<ExpNode> parsing_tree = new ArrayList<ExpNode>();
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
                    passed_regex = expScanner.regexInArrList;

                    parser.start_parsing(passed_regex);
                    parsing_tree = parser.grammarTree;

                    ENFAGraphCreator enfaGraphCreator = new ENFAGraphCreator(passed_regex);
                    enfaGraphCreator.check_tree_type(parsing_tree);

                    isFirstLine = false;
                }


            }

        } 
    }
}