import java.util.ArrayList;

public class TablePrinter
{
    ExpScanner expScanner = new ExpScanner();

    // get the first row of the table
    ArrayList<String> get_first_row(ArrayList<String> regex)
    {
        ArrayList<String> first_row = new ArrayList<String>();
        first_row.add("epsilon");

        for (int i = 0; i < regex.size(); i++)
        {
            if (!regex.get(i).equals("(") && !regex.get(i).equals(")") && !regex.get(i).equals("|") && !regex.get(i).equals("+") && !regex.get(i).equals("*"))
            {
                first_row.add(regex.get(i));
            }
        }

        first_row.add(" other");

        return first_row;
    }

    ArrayList<ArrayList<String>> create_table(ENFAGraph graph, ArrayList<String> regex)
    {
        // e-NFA table to return
        ArrayList<ArrayList<String>> enfaTable = new ArrayList<ArrayList<String>>();

        ArrayList<String> valid_input = get_first_row(regex);
        ArrayList<State> stateList = graph.getStatesList();

        for (int i = 0; i < stateList.size(); i++)
        {
            ArrayList<String> state = new ArrayList<String>();
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

            for (int j = 0; j < valid_input.size(); j++)
            {
                if (stateList.get(i).transitions.size() != 0 && stateList.get(i).transitions.get(0).transExp.equals(valid_input.get(j))) {
                    state.add(j, "q" + stateList.get(i).transitions.get(0).next_state.getStateId());
                } else
                {
                    state.add(j, "-");
                }
            }
            state.add(0, stateId);
            enfaTable.add(state);
        }
        valid_input.add(0,"    ");
        enfaTable.add(0,valid_input);
        return enfaTable;
    }
}
