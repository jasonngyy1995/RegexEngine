import org.junit.Test;
import java.util.ArrayList;
import static org.junit.Assert.assertEquals;

public class ENFAGraphCreator_Test
{
    @Test
    public void testBasicGraph()
    {
        // ab
        ArrayList<String> test_list = new ArrayList<String>();
        test_list.add("a");
        test_list.add("b");

        ArrayList<ExpNode> test_node_list;

        Parser parser = new Parser();
        parser.start_parsing(test_list);
        test_node_list = parser.grammarTree;

        ENFAGraphCreator enfaGraphCreator = new ENFAGraphCreator(test_list);
        ENFAGraph test_graph;

        test_graph = enfaGraphCreator.buildGraph(test_node_list.get(0));
        ArrayList<State> test_state_list = test_graph.statesList;

        assertEquals(test_state_list.get(0).id, 0);
        assertEquals(test_state_list.get(0).transitions.get(0).transExp,"a" );
        assertEquals(test_state_list.get(0).transitions.get(0).next_state.getStateId(), 1);

        assertEquals(test_state_list.get(1).id, 1);
        assertEquals(test_state_list.get(1).transitions.get(0).transExp,"b" );
        assertEquals(test_state_list.get(1).transitions.get(0).next_state.getStateId(), 2);

        assertEquals(test_state_list.get(2).id, 2);
        assertEquals(test_state_list.get(2).transitions.size(),0 );

        enfaGraphCreator.states_list = test_state_list;
        int accepting_state_id = enfaGraphCreator.return_accepting_state();
        assertEquals(accepting_state_id, 2);

    }

    @Test
    public void testBasicWithStarGraph()
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

        assertEquals(test_state_list.get(0).id, 0);
        assertEquals(test_state_list.get(0).transitions.get(0).transExp,"a" );
        assertEquals(test_state_list.get(0).transitions.get(0).next_state.getStateId(), 1);

        assertEquals(test_state_list.get(1).id, 1);
        assertEquals(test_state_list.get(1).transitions.get(0).transExp,"epsilon" );
        assertEquals(test_state_list.get(1).transitions.get(0).next_state.getStateId(), 2);

        assertEquals(test_state_list.get(2).id, 2);
        assertEquals(test_state_list.get(2).transitions.get(0).transExp,"b" );
        assertEquals(test_state_list.get(2).transitions.get(0).next_state.getStateId(), 2);
        assertEquals(test_state_list.get(2).transitions.get(1).transExp,"epsilon" );
        assertEquals(test_state_list.get(2).transitions.get(1).next_state.getStateId(), 3);

        assertEquals(test_state_list.get(3).id, 3);
        assertEquals(test_state_list.get(3).transitions.size(),0 );

        enfaGraphCreator.states_list = test_state_list;
        int accepting_state_id = enfaGraphCreator.return_accepting_state();
        assertEquals(accepting_state_id, 3);
    }

    @Test
    public void testBasicWithPlusGraph()
    {
        ArrayList<String> test_list = new ArrayList<String>();
        test_list.add("1");
        test_list.add(" ");
        test_list.add("+");

        ArrayList<ExpNode> test_node_list;

        Parser parser = new Parser();
        parser.start_parsing(test_list);
        test_node_list = parser.grammarTree;

        ENFAGraphCreator enfaGraphCreator = new ENFAGraphCreator(test_list);
        ENFAGraph test_graph;

        test_graph = enfaGraphCreator.buildGraph(test_node_list.get(0));
        ArrayList<State> test_state_list = test_graph.statesList;

        assertEquals(test_state_list.get(0).id, 0);
        assertEquals(test_state_list.get(0).transitions.get(0).transExp,"1" );
        assertEquals(test_state_list.get(0).transitions.get(0).next_state.getStateId(), 1);

        assertEquals(test_state_list.get(1).id, 1);
        assertEquals(test_state_list.get(1).transitions.get(0).transExp,"epsilon" );
        assertEquals(test_state_list.get(1).transitions.get(0).next_state.getStateId(), 2);

        assertEquals(test_state_list.get(2).id, 2);
        assertEquals(test_state_list.get(2).transitions.get(0).transExp," " );
        assertEquals(test_state_list.get(2).transitions.get(0).next_state.getStateId(), 3);

        assertEquals(test_state_list.get(3).id, 3);
        assertEquals(test_state_list.get(3).transitions.get(0).transExp,"epsilon" );
        assertEquals(test_state_list.get(3).transitions.get(0).next_state.getStateId(), 2);
        assertEquals(test_state_list.get(3).transitions.get(1).transExp,"epsilon" );
        assertEquals(test_state_list.get(3).transitions.get(1).next_state.getStateId(), 4);

        assertEquals(test_state_list.get(4).id, 4);
        assertEquals(test_state_list.get(4).transitions.size(),0 );

        enfaGraphCreator.states_list = test_state_list;
        int accepting_state_id = enfaGraphCreator.return_accepting_state();
        assertEquals(accepting_state_id, 4);
    }

    @Test
    public void testOneAltGraph()
    {
        // a|b
        ArrayList<String> test_list = new ArrayList<String>();
        test_list.add("a");
        test_list.add("|");
        test_list.add("b");

        ArrayList<ExpNode> test_node_list;

        Parser parser = new Parser();
        parser.start_parsing(test_list);
        test_node_list = parser.grammarTree;

        ENFAGraphCreator enfaGraphCreator = new ENFAGraphCreator(test_list);
        ENFAGraph test_graph;

        test_graph = enfaGraphCreator.buildAltGraph(test_node_list);
        ArrayList<State> test_state_list = test_graph.statesList;

        assertEquals(test_state_list.get(0).id, 0);
        assertEquals(test_state_list.get(0).transitions.size(), 2);

        assertEquals(test_state_list.get(0).transitions.get(0).transExp,"epsilon" );
        assertEquals(test_state_list.get(0).transitions.get(0).next_state.getStateId(), 1);
        assertEquals(test_state_list.get(0).transitions.get(1).transExp,"epsilon" );
        assertEquals(test_state_list.get(0).transitions.get(1).next_state.getStateId(), 3);

        assertEquals(test_state_list.get(1).transitions.get(0).transExp,"a" );
        assertEquals(test_state_list.get(1).transitions.get(0).next_state.getStateId(), 2);
        assertEquals(test_state_list.get(2).transitions.get(0).transExp,"epsilon" );
        assertEquals(test_state_list.get(2).transitions.get(0).next_state.getStateId(), 5);

        assertEquals(test_state_list.get(3).transitions.get(0).transExp,"b" );
        assertEquals(test_state_list.get(3).transitions.get(0).next_state.getStateId(), 4);
        assertEquals(test_state_list.get(4).transitions.get(0).transExp,"epsilon" );
        assertEquals(test_state_list.get(4).transitions.get(0).next_state.getStateId(), 5);

        enfaGraphCreator.states_list = test_state_list;
        int accepting_state_id = enfaGraphCreator.return_accepting_state();
        assertEquals(accepting_state_id, 5);

    }
}
