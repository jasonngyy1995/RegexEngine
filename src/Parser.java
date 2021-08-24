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
    ArrayList<ExpNode> tmpStrInBracket = new ArrayList<ExpNode> ();

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

    // bracket handling
    void parse_bracket()
    {
        // jump to next char after the open bracket
        next_char();
        current_char = regexSeq.get(char_index);
        mustbe_character(current_char);

        // until close bracket 
        while (!((regexSeq.get(char_index)).equals(")")))
        {
            current_char = regexSeq.get(char_index);
            System.out.println(current_char);
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
            next_char();
        }   

        // when close bracket read
        if ((regexSeq.get(char_index)).equals(")"))
        {
            // skip close bracket and read if there "*" or "+" after bracket
            next_char();
            current_char = regexSeq.get(char_index);
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

        // // if kleeneplus is found, create a kleeneplus to which add the character node as a child
        } else if (have_kleenPlus()) 
        {
            next_char();
            current_char = regexSeq.get(char_index);
            ExpNode newKleenePlusNode = expNodeCreator.create_oneOrMore_node(current_char);
            newKleenePlusNode.children.add(newCharNode);
            targeted_list.add(newKleenePlusNode);

        } else
        {   
            targeted_list.add(newCharNode);
        }
    }

    // read every exp
    void parse_regex(ArrayList<String> regex) 
    {   
        System.out.println("test");
        // store the regex input from RegexEngine
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
            if (grammarTree.get(i).exp.equals("*") || grammarTree.get(i).exp.equals("+"))
            {
                System.out.printf("%s ", grammarTree.get(i).children);
            }
            System.out.printf("%s ", grammarTree.get(i).exp);
        }
    }
    
}
