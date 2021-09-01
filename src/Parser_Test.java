import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

public class Parser_Test {

    Parser parser = new Parser();

    @Test
    public void testBasic()
    {
        // ab
        ArrayList<String> test_list = new ArrayList<String>();
        test_list.add("a");
        test_list.add("b");

        parser.start_parsing(test_list);
        assertEquals(parser.grammarTree.get(0).exp,"REGEX" );

        assertEquals(parser.grammarTree.get(0).children.get(0).exp, "a");
        assertEquals(parser.grammarTree.get(0).children.get(1).exp, "b");

    }

    @Test
    public void testBasicWithStar()
    {
        // ab*
        ArrayList<String> test_list = new ArrayList<String>();
        test_list.add("a");
        test_list.add("b");
        test_list.add("*");

        parser.start_parsing(test_list);
        assertEquals(parser.grammarTree.get(0).exp,"REGEX" );

        assertEquals(parser.grammarTree.get(0).children.get(0).exp, "a");

        assertEquals(parser.grammarTree.get(0).children.get(1).exp, "*");
        assertEquals(parser.grammarTree.get(0).children.get(1).children.get(0).exp, "b");
    }

    @Test
    public void testBasicWithPlus()
    {
        // 1 +
        ArrayList<String> test_list = new ArrayList<String>();
        test_list.add("1");
        test_list.add(" ");
        test_list.add("+");

        parser.start_parsing(test_list);
        assertEquals(parser.grammarTree.get(0).exp,"REGEX" );

        assertEquals(parser.grammarTree.get(0).children.get(0).exp, "1");

        assertEquals(parser.grammarTree.get(0).children.get(1).exp, "+");
        assertEquals(parser.grammarTree.get(0).children.get(1).children.get(0).exp, " ");
    }

    @Test
    public void testBracket()
    {
        // ab(cd)
        ArrayList<String> test_list = new ArrayList<String>();
        test_list.add("a");
        test_list.add("b");
        test_list.add("(");
        test_list.add("c");
        test_list.add("d");
        test_list.add(")");


        parser.start_parsing(test_list);
        assertEquals(parser.grammarTree.get(0).exp,"REGEX" );

        assertEquals(parser.grammarTree.get(0).children.get(0).exp, "a");
        assertEquals(parser.grammarTree.get(0).children.get(1).exp, "b");
        assertEquals(parser.grammarTree.get(0).children.get(2).exp, "c");
        assertEquals(parser.grammarTree.get(0).children.get(3).exp, "d");
    }

    @Test
    public void testBracketWithStar()
    {
        // ab(cd)*
        ArrayList<String> test_list = new ArrayList<String>();
        test_list.add("a");
        test_list.add("b");
        test_list.add("(");
        test_list.add("c");
        test_list.add("d");
        test_list.add(")");
        test_list.add("*");

        parser.start_parsing(test_list);

        assertEquals(parser.grammarTree.get(0).exp,"REGEX" );

        assertEquals(parser.grammarTree.get(0).children.get(0).exp, "a");
        assertEquals(parser.grammarTree.get(0).children.get(1).exp, "b");

        assertEquals(parser.grammarTree.get(0).children.get(2).exp, "*");
        assertEquals(parser.grammarTree.get(0).children.get(2).children.get(0).exp, "c");
        assertEquals(parser.grammarTree.get(0).children.get(2).children.get(1).exp, "d");
    }

    @Test
    public void testBracketWithPlus()
    {
        // ab(cd)+
        ArrayList<String> test_list = new ArrayList<String>();
        test_list.add("a");
        test_list.add("b");
        test_list.add("(");
        test_list.add("c");
        test_list.add("d");
        test_list.add(")");
        test_list.add("+");

        parser.start_parsing(test_list);

        assertEquals(parser.grammarTree.get(0).exp,"REGEX" );

        assertEquals(parser.grammarTree.get(0).children.get(0).exp, "a");
        assertEquals(parser.grammarTree.get(0).children.get(1).exp, "b");

        assertEquals(parser.grammarTree.get(0).children.get(2).exp, "+");
        assertEquals(parser.grammarTree.get(0).children.get(2).children.get(0).exp, "c");
        assertEquals(parser.grammarTree.get(0).children.get(2).children.get(1).exp, "d");
    }

    @Test
    public void testBasicAlt()
    {
        // a|b
        ArrayList<String> test_list = new ArrayList<String>();
        test_list.add("a");
        test_list.add("|");
        test_list.add("b");

        parser.start_parsing(test_list);

        assertEquals(parser.grammarTree.get(0).exp,"ALT" );

        assertEquals(parser.grammarTree.get(0).children.get(0).exp,"ALT_L" );
        assertEquals(parser.grammarTree.get(0).children.get(0).children.get(0).exp,"a" );

        assertEquals(parser.grammarTree.get(0).children.get(1).exp,"REGEX" );
        assertEquals(parser.grammarTree.get(0).children.get(1).children.get(0).exp,"b" );
    }

    @Test
    public void testTwoAlt()
    {
        // a|b|c
        ArrayList<String> test_list = new ArrayList<String>();
        test_list.add("a");
        test_list.add("|");
        test_list.add("b");
        test_list.add("|");
        test_list.add("c");

        parser.start_parsing(test_list);

        assertEquals(parser.grammarTree.get(0).exp,"ALT" );

        assertEquals(parser.grammarTree.get(0).children.get(0).exp,"ALT_L" );
        assertEquals(parser.grammarTree.get(0).children.get(0).children.get(0).exp,"a" );

        assertEquals(parser.grammarTree.get(0).children.get(1).exp,"ALT" );
        assertEquals(parser.grammarTree.get(0).children.get(1).children.get(0).exp,"ALT_L" );
        assertEquals(parser.grammarTree.get(0).children.get(1).children.get(1).exp,"REGEX" );

        assertEquals(parser.grammarTree.get(0).children.get(1).children.get(0).children.get(0).exp,"b" );
        assertEquals(parser.grammarTree.get(0).children.get(1).children.get(1).children.get(0).exp,"c" );
    }

    @Test
    public void testExampleCase()
    {
        // (ab)*|c+
        ArrayList<String> test_list = new ArrayList<String>();
        test_list.add("(");
        test_list.add("a");
        test_list.add("b");
        test_list.add(")");
        test_list.add("*");
        test_list.add("|");
        test_list.add("c");
        test_list.add("+");

        parser.start_parsing(test_list);

        assertEquals(parser.grammarTree.get(0).exp,"ALT" );

        assertEquals(parser.grammarTree.get(0).children.get(0).exp,"ALT_L" );
        assertEquals(parser.grammarTree.get(0).children.get(0).children.get(0).exp,"*" );
        assertEquals(parser.grammarTree.get(0).children.get(0).children.get(0).children.get(0).exp,"a" );
        assertEquals(parser.grammarTree.get(0).children.get(0).children.get(0).children.get(1).exp,"b" );

        assertEquals(parser.grammarTree.get(0).children.get(1).exp,"REGEX" );
        assertEquals(parser.grammarTree.get(0).children.get(1).children.get(0).exp,"+" );
        assertEquals(parser.grammarTree.get(0).children.get(1).children.get(0).children.get(0).exp,"c" );
    }

}
