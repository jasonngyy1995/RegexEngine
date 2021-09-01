import java.util.ArrayList;

public class TablePrinter
{
    ExpScanner expScanner = new ExpScanner();

    // get the first row of the table
    ArrayList<String> get_first_row(ArrayList<String> regex)
    {
        ArrayList<String> first_row = new ArrayList<String>();
        first_row.add("  ");
        first_row.add("epsilon  ");

        for (int i = 0; i < regex.size(); i++)
        {
            if (!regex.get(i).equals("(") && !regex.get(i).equals(")") && !regex.get(i).equals("|") && !regex.get(i).equals("+") && !regex.get(i).equals("*"))
            {
                first_row.add(regex.get(i));
            }
        }

        first_row.add("  other");

        return first_row;
    }

    ArrayList<ArrayList<String>> create_table(ENFAGraph graph, ArrayList<String> regex)
    {
        ArrayList<ArrayList<String>> enfaTable = new ArrayList<ArrayList<String>>();
        ArrayList<String> valid_input = get_first_row(regex);
        ArrayList<State> stateList = graph.getStatesList();

        enfaTable.add(valid_input);

        for (int i = 0; i < valid_input.size(); i++){
            System.out.print(valid_input.get(i)+" ");
        }

//        for (int i = 0; i < stateList.size(); i++)
//        {
//            ArrayList<String> state = new ArrayList<String>();
//
//            for (int a = 0; a < valid_input.size(); a++)
//            {
//                state.add(" ");
//            }
//
//            for (int j = 0; j < valid_input.size(); j++)
//            {
//                if (i == 0) {
//                    state.add(">q" + stateList.get(i).id);
//                    if (stateList.get(i).transitions.get(0).transExp.equals(valid_input.get(j))) {
//                        state.add(j, "q" + stateList.get(i).transitions.get(0).next_state.id);
//                    }
//
//                } else if (i == stateList.size() - 1) {
//                    state.add(0, "*q" + stateList.get(i).id);
//
//                } else {
//                    state.add("q" + stateList.get(i).id);
//                    if (stateList.get(i).transitions.get(0).transExp.equals(valid_input.get(j))) {
//                        state.add(j, "q" + stateList.get(i).transitions.get(0).next_state.id);
//                    }
//                }
//
//                enfaTable.add(state);
//            }
//        }
        return enfaTable;
    }
}
