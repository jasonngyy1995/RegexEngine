import org.junit.Test;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class InputChecker_Test
{
    @Test
    public void test_input_check_result_of_kStar_1()
    {
        // ab*
        ArrayList<String> test_list = new ArrayList<String>();
        test_list.add("a");
        test_list.add("b");
        test_list.add("*");

        ArrayList<ExpNode> test_node_list;

        Parser parser = new Parser();
        parser.start_parsing(test_list);
        test_node_list = parser.grammarTree;

        ENFAGraphCreator enfaGraphCreator = new ENFAGraphCreator(test_list);
        ENFAGraph test_graph;

        test_graph = enfaGraphCreator.buildGraph(test_node_list.get(0));
        ArrayList<State> test_state_list = test_graph.statesList;
        int test_acceptingStateId = test_state_list.get(test_state_list.size()-1).getStateId();

        InputChecker inputChecker = new InputChecker();
        boolean test_accept = inputChecker.reachAcceptingState("", test_acceptingStateId, test_state_list.get(0));
        String test_input = "ab";

        for (int i = 0; i < test_input.length(); i++)
        {
            char getEachChar = test_input.charAt(i);
            String toString = String.valueOf(getEachChar);

            test_accept = inputChecker.reachAcceptingState(toString, test_acceptingStateId, test_state_list.get(0));
        }

        assertTrue(test_accept);
    }

    @Test
    public void test_input_check_result_of_kStar_2()
    {
        // ab*
        ArrayList<String> test_list = new ArrayList<String>();
        test_list.add("a");
        test_list.add("b");
        test_list.add("*");

        ArrayList<ExpNode> test_node_list;

        Parser parser = new Parser();
        parser.start_parsing(test_list);
        test_node_list = parser.grammarTree;

        ENFAGraphCreator enfaGraphCreator = new ENFAGraphCreator(test_list);
        ENFAGraph test_graph;

        test_graph = enfaGraphCreator.buildGraph(test_node_list.get(0));
        ArrayList<State> test_state_list = test_graph.statesList;
        int test_acceptingStateId = test_state_list.get(test_state_list.size()-1).getStateId();

        InputChecker inputChecker = new InputChecker();
        boolean test_accept = inputChecker.reachAcceptingState("", test_acceptingStateId, test_state_list.get(0));
        String test_input = "a";

        for (int i = 0; i < test_input.length(); i++)
        {
            char getEachChar = test_input.charAt(i);
            String toString = String.valueOf(getEachChar);

            test_accept = inputChecker.reachAcceptingState(toString, test_acceptingStateId, test_state_list.get(0));
        }

        assertTrue(test_accept);
    }

    @Test
    public void test_input_check_result_of_kStar_3()
    {
        // ab*
        ArrayList<String> test_list = new ArrayList<String>();
        test_list.add("a");
        test_list.add("b");
        test_list.add("*");

        ArrayList<ExpNode> test_node_list;

        Parser parser = new Parser();
        parser.start_parsing(test_list);
        test_node_list = parser.grammarTree;

        ENFAGraphCreator enfaGraphCreator = new ENFAGraphCreator(test_list);
        ENFAGraph test_graph;

        test_graph = enfaGraphCreator.buildGraph(test_node_list.get(0));
        ArrayList<State> test_state_list = test_graph.statesList;
        int test_acceptingStateId = test_state_list.get(test_state_list.size()-1).getStateId();

        InputChecker inputChecker = new InputChecker();
        boolean test_accept = inputChecker.reachAcceptingState("", test_acceptingStateId, test_state_list.get(0));
        String test_input = "abc";

        for (int i = 0; i < test_input.length(); i++)
        {
            char getEachChar = test_input.charAt(i);
            String toString = String.valueOf(getEachChar);

            test_accept = inputChecker.reachAcceptingState(toString, test_acceptingStateId, test_state_list.get(0));
        }

        assertFalse(test_accept);
    }

    @Test
    public void test_input_check_result_of_kPlus_1()
    {
        // ab+
        ArrayList<String> test_list = new ArrayList<String>();
        test_list.add("a");
        test_list.add("b");
        test_list.add("+");

        ArrayList<ExpNode> test_node_list;

        Parser parser = new Parser();
        parser.start_parsing(test_list);
        test_node_list = parser.grammarTree;

        ENFAGraphCreator enfaGraphCreator = new ENFAGraphCreator(test_list);
        ENFAGraph test_graph;

        test_graph = enfaGraphCreator.buildGraph(test_node_list.get(0));
        ArrayList<State> test_state_list = test_graph.statesList;
        int test_acceptingStateId = test_state_list.get(test_state_list.size()-1).getStateId();

        InputChecker inputChecker = new InputChecker();
        boolean test_accept = inputChecker.reachAcceptingState("", test_acceptingStateId, test_state_list.get(0));
        String test_input = "ab";

        for (int i = 0; i < test_input.length(); i++)
        {
            char getEachChar = test_input.charAt(i);
            String toString = String.valueOf(getEachChar);

            test_accept = inputChecker.reachAcceptingState(toString, test_acceptingStateId, test_state_list.get(0));
        }

        assertTrue(test_accept);
    }

    @Test
    public void test_input_check_result_of_kPlus_2()
    {
        // ab+
        ArrayList<String> test_list = new ArrayList<String>();
        test_list.add("a");
        test_list.add("b");
        test_list.add("+");

        ArrayList<ExpNode> test_node_list;

        Parser parser = new Parser();
        parser.start_parsing(test_list);
        test_node_list = parser.grammarTree;

        ENFAGraphCreator enfaGraphCreator = new ENFAGraphCreator(test_list);
        ENFAGraph test_graph;

        test_graph = enfaGraphCreator.buildGraph(test_node_list.get(0));
        ArrayList<State> test_state_list = test_graph.statesList;
        int test_acceptingStateId = test_state_list.get(test_state_list.size()-1).getStateId();

        InputChecker inputChecker = new InputChecker();
        boolean test_accept = inputChecker.reachAcceptingState("", test_acceptingStateId, test_state_list.get(0));
        String test_input = "a";

        for (int i = 0; i < test_input.length(); i++)
        {
            char getEachChar = test_input.charAt(i);
            String toString = String.valueOf(getEachChar);

            test_accept = inputChecker.reachAcceptingState(toString, test_acceptingStateId, test_state_list.get(0));
        }

        assertFalse(test_accept);
    }
}
