import org.junit.Test;
import java.util.ArrayList;
import static org.junit.Assert.assertEquals;

public class TableConverter_Test
{
    TableConverter tableConverter = new TableConverter();

    @Test
    public void test_getFirstRow()
    {
        ArrayList<String> test_regex = new ArrayList<String>();
        test_regex.add("a");
        test_regex.add("b");
        test_regex.add("(");
        test_regex.add("c");
        test_regex.add("d");
        test_regex.add(")");
        test_regex.add("*");

        ArrayList<String> first_row;

        first_row = tableConverter.get_first_row(test_regex);
        assertEquals(first_row.get(0),"epsilon" );
        assertEquals(first_row.get(1),"a" );
        assertEquals(first_row.get(2),"b" );
        assertEquals(first_row.get(3),"c" );
        assertEquals(first_row.get(4),"d" );
        assertEquals(first_row.get(5)," other" );
    }

    @Test
    public void test_create_table()
    {
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

        TableConverter tableConverter = new TableConverter();
        ArrayList<ArrayList<String>> test_table = tableConverter.create_table(test_graph, test_list);

        // test the table size
        assertEquals(test_table.size(), 6);

        // test the first row of table
        assertEquals(test_table.get(0).size(), 5);
        assertEquals(test_table.get(0).get(0), "   ");
        assertEquals(test_table.get(0).get(1), "epsilon");
        assertEquals(test_table.get(0).get(2), "a");
        assertEquals(test_table.get(0).get(3), "b");
        assertEquals(test_table.get(0).get(4), " other");

        // test the table result of single state in each box
        assertEquals(test_table.get(1).get(1), "-");
        assertEquals(test_table.get(1).get(2), "q1");
        assertEquals(test_table.get(1).get(3), "-");
        assertEquals(test_table.get(1).get(4), "-");

        // test the table result of two states in a box
        assertEquals(test_table.get(4).get(1), "q2,q4");
        assertEquals(test_table.get(4).get(2), "-");
        assertEquals(test_table.get(4).get(3), "-");
        assertEquals(test_table.get(4).get(4), "-");
    }

}
