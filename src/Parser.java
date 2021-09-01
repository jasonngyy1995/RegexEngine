import java.util.ArrayList;

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
            next_char();
            return true;
        }
        return false;
    }

    boolean have_kleenPlus()
    {
        if ((regexSeq.get(char_index+1)).equals("+"))
        {
            next_char();
            return true;
        }
        return false;
    }

    // bracket handling
    void parse_bracket(ArrayList<ExpNode> tmpGrammarTree)
    {
        ArrayList<ExpNode> tmpStrInBracket = new ArrayList<ExpNode> ();

        // jump to next char after the open bracket
        next_char();
        current_char = regexSeq.get(char_index);
        mustbe_character(current_char);

        // until close bracket 
        while (!((regexSeq.get(char_index)).equals(")")))
        {
            // get the character after the open bracket
            current_char = regexSeq.get(char_index);
            parse_character(current_char, tmpStrInBracket);

            if (current_char.equals("|"))
            {
                parse_alternator(tmpStrInBracket);
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
            if (char_index == regexSeq.size() - 1)
            {
//                // If the end of string inside bracket is not a "+" or "*", mark the most bottom child as the end of the branch of tree
//                if (!tmpStrInBracket.get(tmpStrInBracket.size()-1).exp.equals("+") || !tmpStrInBracket.get(tmpStrInBracket.size()-1).exp.equals("*"))
//                {
//                    tmpStrInBracket.get(tmpStrInBracket.size()-1).haveChild = false;
//                }

                for (ExpNode node: tmpStrInBracket)
                {
                    tmpGrammarTree.add(node);
                }
            }
            // skip close bracket and read if there "*" or "+" after bracket
            else if (char_index < regexSeq.size())
            {
                // If the end of string inside bracket is not a "+" or "*", mark the most bottom child as the end of the branch of tree
//                if (!tmpStrInBracket.get(tmpStrInBracket.size()-1).exp.equals("+") || !tmpStrInBracket.get(tmpStrInBracket.size()-1).exp.equals("*"))
//                {
//                    tmpStrInBracket.get(tmpStrInBracket.size()-1).haveChild = false;
//                }

                next_char();
                current_char = regexSeq.get(char_index);
                if (current_char.equals("*"))
                {
                    ExpNode newKleeneStarNode = expNodeCreator.create_zeroOrMore_node(current_char);
                    newKleeneStarNode.children = tmpStrInBracket;
                    tmpGrammarTree.add(newKleeneStarNode);

                } else if (current_char.equals("+"))
                {
                    ExpNode newKleenePlusNode = expNodeCreator.create_oneOrMore_node(current_char);
                    newKleenePlusNode.children = tmpStrInBracket;
                    tmpGrammarTree.add(newKleenePlusNode);

                } else {
                    for (ExpNode node: tmpStrInBracket)
                    {
                        tmpGrammarTree.add(node);
                    }
                }
            }

        }

    }

    ExpNode parse_alternator(ArrayList<ExpNode> left_regex_child)
    {
        next_char();
        ExpNode newAltNode = expNodeCreator.create_Alternator_node("ALT");
        newAltNode.children = new ArrayList<ExpNode>();

        ExpNode newAltLeft = expNodeCreator.create_AltLeft_node();
        newAltLeft.children = left_regex_child;

        newAltNode.children.add(newAltLeft);

        ExpNode newAltRight;
        newAltRight = parse_regex();

        newAltNode.children.add(newAltRight);

        return newAltNode;
    }

    // parse character
    void parse_character(String currentChar, ArrayList<ExpNode> targeted_list)
    {
        // create a character node
        ExpNode newCharNode = expNodeCreator.create_character_node(currentChar);

        // if this character is the end of expression
        if (char_index+1 == regexSeq.size())
        {
            // The end of regex, mark the most bottom child as the end of the branch of tree
//            newCharNode.haveChild = false;
            targeted_list.add(newCharNode);

        // if kleenestar is found, create a kleenestar to which add the character node as a child
        } else if (have_kleenStar())
        {
            current_char = regexSeq.get(char_index);
            ExpNode newKleeneStarNode = expNodeCreator.create_zeroOrMore_node(current_char);

            // Inside a "*", mark the most bottom child as the end of the branch of tree
//            newCharNode.haveChild = false;

            newKleeneStarNode.children.add(newCharNode);
            targeted_list.add(newKleeneStarNode);

        // // if kleeneplus is found, create a kleeneplus to which add the character node as a child
        } else if (have_kleenPlus()) 
        {
            current_char = regexSeq.get(char_index);
            ExpNode newKleenePlusNode = expNodeCreator.create_oneOrMore_node(current_char);

            // Inside a "+", mark the most bottom child as the end of the branch of tree
//            newCharNode.haveChild = false;

            newKleenePlusNode.children.add(newCharNode);
            targeted_list.add(newKleenePlusNode);

        } else
        {   
            targeted_list.add(newCharNode);
        }
    }

    // read every exp
    ExpNode parse_regex()
    {
        ArrayList<ExpNode> tmpGrammarTree = new ArrayList<ExpNode> ();
        while (char_index < regexSeq.size() && !(regexSeq.get(char_index).equals("|")))
        {
            current_char = regexSeq.get(char_index);
            if (char_index == 0)
            {
                mustbe_characterOrOpenBracket(current_char);
            }
            
            if (current_char.equals("("))
            {
                parse_bracket(tmpGrammarTree);

            } else 
            {   
                parse_character(current_char, tmpGrammarTree);
            }

            next_char();
        }

         if (char_index < regexSeq.size() && regexSeq.get(char_index).equals("|"))
         {
             // if meets alternator, and the end of parsed regex is not "+" or "*", mark the most bottom child as the end of the branch of tree
//             if (!tmpGrammarTree.get(tmpGrammarTree.size()-1).exp.equals("+") || !tmpGrammarTree.get(tmpGrammarTree.size()-1).exp.equals("*"))
//             {
//                 tmpGrammarTree.get(tmpGrammarTree.size()-1).haveChild = false;
//             }

             ExpNode alt = parse_alternator(tmpGrammarTree);
             return alt;
         }

        ExpNode parsed_tree = expNodeCreator.create_regex_node();
        parsed_tree.children = tmpGrammarTree;

        return parsed_tree;
    }

    void start_parsing(ArrayList<String> regex)
    {
        // store the regex input from RegexEngine
        regexSeq = regex;

        ExpNode regex_node = parse_regex();
        grammarTree.add(regex_node);

    }
    
}
