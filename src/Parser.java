import java.io.*;  
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.IOException;

public class Parser {

    ExpNodeCreator expNodeCreator = new ExpNodeCreator();
    int char_index = 0;
    String current_char;

    ArrayList<ExpNode> grammarTree = new ArrayList<ExpNode> ();
    ArrayList<String> regexSeq = new ArrayList<String> ();

    void next_char()
    {   
        if (char_index < regexSeq.size())
        {
            char_index++;
        }
    }

    void mustbe_characterOrOpenBracket(String _char)
    {
        if (_char.equals(")") || _char.equals("*") || _char.equals("+") || _char.equals("|"))
        {
            System.out.println("Invalid input - Regular expression can only starts with alphabets, numbers of spaces");
            System.exit(1);
        }
    }

    void mustbe_character(String _char)
    {
        if (_char.equals("(") || _char.equals(")") || _char.equals("*") || _char.equals("+") || _char.equals("|"))
        {
            System.out.println("Invalid input - Can only start with character inside the bracket, no nested bracket.");
            System.exit(1);
        }
    }

    boolean have_kleenStar()
    {
        if ((regexSeq.get(char_index+1)).equals("*"))
        {
            return true;
        }
        return false;
    }

    boolean have_kleenPlus()
    {
        if ((regexSeq.get(char_index+1)).equals("+"))
        {
            return true;
        }
        return false;
    }

    // create start node
    void create_start_node() 
    {
        ExpNode start_state = new ExpNode(NodeType.START_STATE, "START_NODE");
        grammarTree.add(start_state);
    }
    
    // bracket handling
    void parse_bracket()
    {   
        ArrayList<ExpNode> tmpStrInBracket = new ArrayList<ExpNode> ();
        // skip the open bracket
        next_char();
        String current_char = regexSeq.get(char_index);

        // until close bracket 
        while (!(current_char.equals(")")))
        {
            mustbe_character(current_char);
            parse_character(current_char, tmpStrInBracket);

            if (current_char.equals("|"))
            {
                parse_alternator(current_char, tmpStrInBracket);
            }

            // cannot find close bracket at the end of regular expression -> error
            if (char_index == regexSeq.size() - 1)
            {
                if (!(current_char.equals(")")))
                {
                    System.out.println("Invalid input - Close bracket missed.");
                    System.exit(1);
                }
            }
        }   

        // when close bracket read
        if (current_char.equals(")"))
        {   
            // skip close bracket and read if there "*" or "+" after bracket
            next_char();
            if (current_char.equals("*"))
            {   
                ExpNode newKleeneStarNode = expNodeCreator.create_zeroOrMore_node(current_char);
                newKleeneStarNode.children = tmpStrInBracket;
                grammarTree.add(newKleeneStarNode);

            } else if (current_char.equals("+"))
            {
                ExpNode newKleenePlusNode = expNodeCreator.create_oneOrMore_node(current_char);
                newKleenePlusNode.children = tmpStrInBracket;
                grammarTree.add(newKleenePlusNode);

            } else {
                for (ExpNode node: tmpStrInBracket)
                {
                    grammarTree.add(node);
                }
            }
        }

    }

    void parse_alternator(String currentChar, ArrayList<ExpNode> targeted_list)
    {   
        ExpNode newAltNode = expNodeCreator.create_Alternator_node(currentChar);
        targeted_list.add(newAltNode);
    }

    // parse character
    void parse_character(String currentChar, ArrayList<ExpNode> targeted_list)
    {
        // create a character node
        ExpNode newCharNode = expNodeCreator.create_character_node(currentChar);

        // if this character is the end of expression
        if (char_index+1 == regexSeq.size())
        {
            targeted_list.add(newCharNode);

        // if kleenestar is found, create a kleenestar to which add the character node as a child
        } else if (have_kleenStar())
        {
            next_char();
            current_char = regexSeq.get(char_index);
            ExpNode newKleeneStarNode = expNodeCreator.create_zeroOrMore_node(current_char);
            newKleeneStarNode.children.add(newCharNode);
            targeted_list.add(newKleeneStarNode);
            // skip the star
            next_char();

        // // if kleeneplus is found, create a kleeneplus to which add the character node as a child
        } else if (have_kleenPlus()) 
        {
            next_char();
            current_char = regexSeq.get(char_index);
            ExpNode newKleenePlusNode = expNodeCreator.create_oneOrMore_node(current_char);
            newKleenePlusNode.children.add(newCharNode);
            targeted_list.add(newKleenePlusNode);
            // skip the plus
            next_char();

        } else
        {   
            targeted_list.add(newCharNode);
        }
    }

    // read every exp
    void parse_regex(ArrayList<String> regex) 
    {   
        System.out.println("test");
        regexSeq = regex;
        // for (int i = 0; i < regexSeq.size(); i++)
        // {
        //     System.out.printf("%s ", regexSeq.get(i));
        // }   
        while (char_index < regexSeq.size())
        {   
            current_char = regexSeq.get(char_index);
            if (char_index == 0)
            {
                create_start_node();
                mustbe_characterOrOpenBracket(current_char);
            }
            
            if (current_char.equals("("))
            {   
                parse_bracket();

            } else if (current_char.equals("|"))
            {
                parse_alternator(current_char, grammarTree);
            } else 
            {   
                parse_character(current_char, grammarTree);
            }
            next_char();
        }

        for (int i = 0; i < grammarTree.size(); i++)
        {
            System.out.printf("%s ", grammarTree.get(i).exp);
        }
    }
    
}
