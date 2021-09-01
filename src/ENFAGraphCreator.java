import java.util.ArrayList;

class ENFAGraph {

    ArrayList<State> statesList;

    // construct a E-NFA graph
    ENFAGraph()
    {
        this.statesList = new ArrayList<State>();
    }

    ArrayList<State> getStatesList()
    {
        return this.statesList;
    }
}

public class ENFAGraphCreator
{
    ArrayList<String> regex;

    ENFAGraphCreator (ArrayList<String> regex)
    {
        this.regex = regex;
    }

    boolean if_only_char = false;
    TablePrinter tablePrinter = new TablePrinter();

    boolean check_if_only_char(ArrayList<ExpNode> grammar_tree)
    {
        if (grammar_tree.get(0).nodeType == NodeType.REGEX)
        {
            for (int i = 0; i < grammar_tree.get(0).children.size(); i++)
            {
                if (grammar_tree.get(0).children.get(i).nodeType != NodeType.CHARACTER)
                {
                    return false;
                }
            }
        }
        return true;
    }

    State create_start_state()
    {
        State start_state = new State(0);
        start_state.setTrans_exp("epsilon");

        return start_state;
    }

    State create_accept_state(int id)
    {
        State accept_state = new State(id);

        return accept_state;
    }

//    ENFAGraph buildNfaGraph(ArrayList<ExpNode> grammar_tree)
//    {
//        ENFAGraph graph = new ENFAGraph();
//        State start_state = create_start_state();
//        graph.statesList.add(start_state);
//
//        int id = 1;
//        for (int i = 0; i < grammar_tree.get(0).children.size(); i++)
//        {
//            State newState = new State(id);
//            newState.setTrans_exp(grammar_tree.get(0).children.get(i).exp);
//            id++;
//
//            graph.statesList.add(newState);
//        }
//
//        for (int i = 0; i < graph.statesList.size() - 1; i++)
//        {
//            State state = graph.statesList.get(i);
//            State next_state = graph.statesList.get(i+1);
//            Transition transition = new Transition(state.trans_exp, next_state);
//            state.add_trans_func(transition);
//        }
//
//        State new_accept_state = create_accept_state(id);
//        graph.statesList.add(new_accept_state);
//
//        State oldAcceptState = graph.statesList.get(graph.statesList.size()-2);
//        Transition toNewAcceptState = new Transition("epsilon", new_accept_state);
//        oldAcceptState.add_trans_func(toNewAcceptState);
//
//        return graph;
//    }

    ENFAGraph buildOnlyCharGraph(ArrayList<ExpNode> grammar_tree)
    {
        ENFAGraph graph = new ENFAGraph();

        int id = 0;
        for (int i = 0; i < grammar_tree.get(0).children.size(); i++)
        {
            State newState = new State(id);
            newState.setTrans_exp(grammar_tree.get(0).children.get(i).exp);
            id++;

            graph.statesList.add(newState);
        }

        if (graph.statesList.size()==1)
        {
            State state = graph.statesList.get(0);

            State new_accept_state = create_accept_state(1);
            graph.statesList.add(new_accept_state);

            Transition transition = new Transition(state.trans_exp, new_accept_state);
            state.add_trans_func(transition);

        } else
        {
            for (int i = 0; i < graph.statesList.size() - 1; i++)
            {
                State state = graph.statesList.get(i);
                State next_state = graph.statesList.get(i+1);
                Transition transition = new Transition(state.trans_exp, next_state);
                state.add_trans_func(transition);
            }

            State new_accept_state = create_accept_state(id);
            graph.statesList.add(new_accept_state);

            State oldAcceptState = graph.statesList.get(graph.statesList.size()-2);
            Transition toNewAcceptState = new Transition(oldAcceptState.trans_exp, new_accept_state);
            oldAcceptState.add_trans_func(toNewAcceptState);
        }

//        for (int i = 0; i < graph.statesList.size(); i++)
//        {
//            System.out.print("q"+graph.statesList.get(i).id+" ");
//        }

        return graph;
    }

    void check_tree_type(ArrayList<ExpNode> grammar_tree)
    {
        if_only_char = check_if_only_char(grammar_tree);

        if (if_only_char == true)
        {
            ENFAGraph graph = buildOnlyCharGraph(grammar_tree);

            ArrayList<ArrayList<String>> tmp = tablePrinter.create_table(graph, regex);

            for (int i = 0; i < tmp.size(); i++)
            {
                for (int j = 0; j < tmp.get(i).size(); j++)
                {
                    System.out.print(tmp.get(i).get(j)+"  ");
                }
                System.out.println();
            }

        }
    }

}
