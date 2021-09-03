import java.util.ArrayList;

public class TableConverter
{
    // get the first row of the table
    ArrayList<String> get_first_row(ArrayList<String> regex)
    {
        ArrayList<String> tmp = new ArrayList<String>();
        ArrayList<String> first_row = new ArrayList<String>();


        for (int i = 0; i < regex.size(); i++)
        {
            if (!regex.get(i).equals("(") && !regex.get(i).equals(")") && !regex.get(i).equals("|") && !regex.get(i).equals("+") && !regex.get(i).equals("*"))
            {
                tmp.add(regex.get(i));
            }
        }

        for (String str : tmp)
        {
            if (!first_row.contains(str))
            {
                first_row.add(str);
            }
        }

        first_row.add(0,"epsilon");
        first_row.add(" other");

        return first_row;
    }

    // function to each row of e-nfa table
    ArrayList<ArrayList<String>> create_table(ENFAGraph graph, ArrayList<String> regex)
    {
        // e-NFA table to return
        ArrayList<ArrayList<String>> enfaTable = new ArrayList<ArrayList<String>>();

        ArrayList<String> valid_input = get_first_row(regex);
        // get information from each state
        ArrayList<State> stateList = graph.getStatesList();
        int valid_input_size = valid_input.size();

        for (int i = 0; i < stateList.size(); i++)
        {
            // initialize the row
            ArrayList<String> state = new ArrayList<String>();
            for (int j = 0; j < valid_input_size; j++)
            {
                state.add("-");
            }

            // create the head element of each row e.g >q0, q1, q2...
            String stateId;
            if (i == 0)
            {
                stateId = ">q" + stateList.get(i).getStateId();

            } else if (i == stateList.size() - 1)
            {
                stateId = "*q" + stateList.get(i).getStateId();
            } else
            {
                stateId = "q" + stateList.get(i).getStateId();
            }

            // find matched states and regular expressions
            for (int j = 0; j < valid_input.size(); j++)
            {
                if (stateList.get(i).transitions.size() != 0 && stateList.get(i).transitions.get(0).transExp.equals(valid_input.get(j)))
                {
                    if (!state.get(j).equals("-"))
                    {
                        String addBehind = ","+"q" + stateList.get(i).transitions.get(0).next_state.getStateId();
                        state.get(j).concat(addBehind);
                    } else
                    {
                        state.set(j, "q" + stateList.get(i).transitions.get(0).next_state.getStateId());
                    }
                }
            }

            // find matched states and regular expressions again for next transition list
            for (int j = 0; j < valid_input.size(); j++)
            {
                if (stateList.get(i).transitions.size() == 2 && stateList.get(i).transitions.get(1).transExp.equals(valid_input.get(j)))
                {
                    if (!state.get(j).equals("-"))
                    {
                        String addBehind = ","+"q" + stateList.get(i).transitions.get(1).next_state.getStateId();
                        state.set(j, state.get(j).concat(addBehind));
                    } else
                    {
                        state.set(j, "q" + stateList.get(i).transitions.get(1).next_state.getStateId());
                    }
                }
            }

            state.add(0, stateId);
            enfaTable.add(state);
        }
        valid_input.add(0,"   ");
        enfaTable.add(0,valid_input);
        return enfaTable;
    }
}
