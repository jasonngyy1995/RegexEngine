import java.util.ArrayList;

public class TableCreator
{
    ArrayList<ExpNode> grammar_tree;
    ArrayList<String> regex;
    ArrayList<String> first_row;

    TableCreator(ArrayList<ExpNode> input_tree, ArrayList<String> input_regex)
    {
        this.grammar_tree = input_tree;
        this.regex = input_regex;
    }

    ArrayList<String> get_first_row()
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

        first_row.add("other");

        return first_row;
    }


}
