import java.util.ArrayList;
import java.util.Scanner;

public class RegexEngine
{
    public static void main(String[] args)
    {
        ArrayList<String> passed_regex;
        ArrayList<ExpNode> parsing_tree = new ArrayList<ExpNode>();
        ArrayList<ArrayList<String>> eNfa_table = new ArrayList<ArrayList<String>>();
        ArrayList<State> states_list = new ArrayList<State>();

        // check if verbose
        boolean verbose_mode = false;
        if (args.length != 0 && args[0].equals("-v"))
        {
            verbose_mode = true;
        }

        ExpScanner expScanner = new ExpScanner();
        Parser parser = new Parser();
        InputChecker inputChecker = new InputChecker();
        Scanner scanner = new Scanner(System.in);

        boolean isFirstLine = true;
        // reading regular expression
        String regular_expression = scanner.nextLine();
        expScanner.passFirstLine(regular_expression);
        passed_regex = expScanner.regexInArrList;

        parser.start_parsing(passed_regex);
        parsing_tree = parser.grammarTree;

        ENFAGraphCreator enfaGraphCreator = new ENFAGraphCreator(passed_regex);
        eNfa_table = enfaGraphCreator.table_converter(parsing_tree);

        if (verbose_mode == true)
        {
            enfaGraphCreator.print_table(eNfa_table);
        }

        states_list = enfaGraphCreator.states_list;
        int accepting_state_id = enfaGraphCreator.return_accepting_state();

        while (true)
        {
            if (isFirstLine == true && verbose_mode == true)
            {
                System.out.println();
            }

            if (isFirstLine == true)
            {
                System.out.println("Ready");
            }

            String input = scanner.nextLine();

            // start checking input
            boolean accept = inputChecker.reachAcceptingState("", accepting_state_id, states_list.get(0));

            if (verbose_mode == true)
            {
                if (accept)
                {
                    System.out.println("true");
                } else
                {
                    System.out.println("false");
                }
            }

            // parse the input string
            for (int i = 0; i < input.length(); i++)
            {
                char getEachChar = input.charAt(i);
                String toString = String.valueOf(getEachChar);

                accept = inputChecker.reachAcceptingState(toString, accepting_state_id, states_list.get(0));
                // for normal mode result only prints when all strings are checked
                if (verbose_mode == true || i == (input.length()-1))
                {
                    if (accept == true)
                    {
                        System.out.println("true");
                    } else
                    {
                        System.out.println("false");
                    }
                }
            }
            isFirstLine = false;
        }
    }
}
