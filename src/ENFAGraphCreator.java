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
    // get the passed input
    ArrayList<String> regex;
    ArrayList<State> states_list = new ArrayList<State>();

    // constructor
    ENFAGraphCreator (ArrayList<String> regex)
    {
        this.regex = regex;
    }

    TableConverter tableConverter = new TableConverter();

    // function to create a start state
    State create_start_state()
    {
        State start_state = new State(0);
        start_state.setTrans_exp("epsilon");

        return start_state;
    }

    // function to create an accepting state
    State create_accept_state(int id)
    {
        State accept_state = new State(id);

        return accept_state;
    }

    // function to build graph for REGEX or ALT_L node types
    ENFAGraph buildGraph(ExpNode node_of_sequence)
    {
        ENFAGraph graph = new ENFAGraph();

        int id = 0;
        // remind next state to point previous state if it's "one or more"
        boolean previous_is_kplus = false;

        for (int i = 0; i < node_of_sequence.children.size(); i++)
        {
            ExpNode scanning_node = node_of_sequence.children.get(i);

            if (scanning_node.nodeType == NodeType.CHARACTER)
            {
                State newState = new State(id);
                newState.setTrans_exp(scanning_node.exp);
                newState.addTo_acceptedInput(scanning_node.exp);
                id++;

                if (previous_is_kplus == true)
                {
                    Transition toPrevious = new Transition("epsilon", graph.statesList.get(id-2));
                    newState.addTo_acceptedInput("epsilon");
                    newState.add_trans_func(toPrevious);
                    previous_is_kplus = false;
                }

                graph.statesList.add(newState);

            } else if (scanning_node.nodeType == NodeType.KSTAR)
            {
                // create a state before
                State beforeKstarState = new State(id);
                beforeKstarState.setTrans_exp("epsilon");
                beforeKstarState.addTo_acceptedInput("epsilon");
                id++;
                graph.statesList.add(beforeKstarState);

                State newState = new State(id);
                newState.setTrans_exp("epsilon");
                newState.addTo_acceptedInput("epsilon");
                id++;

                // a transition of pointing itself
                Transition toSelf = new Transition(scanning_node.children.get(0).exp, newState);
                newState.add_trans_func(toSelf);
                newState.addTo_acceptedInput(scanning_node.children.get(0).exp);
                graph.statesList.add(newState);

            } else if (scanning_node.nodeType == NodeType.KPLUS)
            {
                // create a state before
                previous_is_kplus = true;

                State beforeKplusState = new State(id);
                beforeKplusState.setTrans_exp("epsilon");
                beforeKplusState.addTo_acceptedInput("epsilon");
                id++;
                graph.statesList.add(beforeKplusState);

                State newState = new State(id);
                newState.setTrans_exp(scanning_node.children.get(0).exp);
                newState.addTo_acceptedInput(scanning_node.children.get(0).exp);
                id++;

                graph.statesList.add(newState);

                // if + or * is the last element of input
                if (i == node_of_sequence.children.size()-1)
                {
                    // create a new accepting state
                    State extraState = new State(id);
                    extraState.setTrans_exp("epsilon");

                    Transition toPrevious = new Transition("epsilon", newState);
                    extraState.add_trans_func(toPrevious);

                    graph.statesList.add(extraState);
                    id++;
                }
            }
        }

        // combine the statelist
        // if the list size is only 1
        if (graph.statesList.size()==1)
        {
            State state = graph.statesList.get(0);

            State new_accept_state = create_accept_state(1);
            graph.statesList.add(new_accept_state);

            state.addTo_acceptedInput(state.trans_exp);

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

        return graph;
    }

    // function to build graph for ALT node type
    ENFAGraph buildAltGraph(ArrayList<ExpNode> grammar_tree)
    {
        ExpNode alt = grammar_tree.get(0);
        ENFAGraph alt_left = buildGraph(alt.children.get(0));
        ENFAGraph alt_right = buildGraph(alt.children.get(1));

        ArrayList<State> alt_left_states = alt_left.getStatesList();
        ArrayList<State> alt_right_states = alt_right.getStatesList();

        int id = 1;
        for (int i = 0; i < alt_left_states.size(); i++)
        {
            alt_left_states.get(i).setStateId(id);
            id++;
        }

        for (int i = 0; i < alt_right_states.size(); i++)
        {
            alt_right_states.get(i).setStateId(id);
            id++;
        }

        ENFAGraph altGraph = new ENFAGraph();

        // create a new start state which points to the head state of left and right lists of states
        State altState = create_start_state();
        altState.addTo_acceptedInput("epsilon");
        Transition toLeft = new Transition("epsilon", alt_left_states.get(0));
        Transition toRight = new Transition("epsilon", alt_right_states.get(0));
        altState.add_trans_func(toLeft);
        altState.add_trans_func(toRight);

        // create a new accepting state which is pointed by the accepting states of left and right lists of states
        State newAcceptState = new State(id);
        Transition toNewAcc = new Transition("epsilon", newAcceptState);
        alt_left_states.get(alt_left_states.size()-1).add_trans_func(toNewAcc);
        alt_right_states.get(alt_right_states.size()-1).add_trans_func(toNewAcc);

        altGraph.statesList.add(altState);
        altGraph.statesList.addAll(alt_left_states);
        altGraph.statesList.addAll(alt_right_states);
        altGraph.statesList.add(newAcceptState);

        return altGraph;
    }

    // function to print the e-nfa table
    ArrayList<ArrayList<String>> table_converter(ArrayList<ExpNode> grammar_tree)
    {
        ENFAGraph graph = new ENFAGraph();

        if (grammar_tree.get(0).nodeType == NodeType.REGEX)
        {
            graph = buildGraph(grammar_tree.get(0));
            states_list = graph.getStatesList();

        } else if (grammar_tree.get(0).nodeType == NodeType.ALT)
        {
            graph = buildAltGraph(grammar_tree);
            states_list = graph.getStatesList();
        }
        // convert states list to an e-nfa table
        ArrayList<ArrayList<String>> tmp = tableConverter.create_table(graph, regex);

        return tmp;
    }

    // function to get accepting states from state list
    int return_accepting_state()
    {
        return states_list.get(states_list.size()-1).getStateId();
    }

    // function to print the e-nfa table
    void print_table(ArrayList<ArrayList<String>> table)
    {
        for (int i = 0; i < table.size(); i++)
        {
            for (int j = 0; j < table.get(i).size(); j++)
            {
                System.out.print(table.get(i).get(j)+"  ");
            }
                System.out.println();
        }
    }
}
