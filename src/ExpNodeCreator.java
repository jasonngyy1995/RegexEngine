import java.util.ArrayList;

public class ExpNodeCreator {

    ExpNode create_regex_node()
    {
        // create alternator tree
        ExpNode newNode = new ExpNode(NodeType.REGEX, "REGEX");
        return newNode;
    }
    
    // reg_exp ::= a single item of 'a-Z', '0-9', ' '
    ExpNode create_character_node(String character)
    {
        ExpNode newNode = new ExpNode(NodeType.CHARACTER, character);
        return newNode;
    }

    // kleeneStar ::= reg_exp'*' | expInBracket'*'
    ExpNode create_zeroOrMore_node(String character)
    {
        ExpNode newNode = new ExpNode(NodeType.KSTAR, character);
        newNode.children = new ArrayList<ExpNode>();
        return newNode;
    }

    // kleenePlus ::= reg_exp'+' | expInBracket'+'
    ExpNode create_oneOrMore_node(String character)
    {
        ExpNode newNode = new ExpNode(NodeType.KPLUS, character);
        newNode.children = new ArrayList<ExpNode>();
        return newNode;
    }

    // Alternator ::= char or charsInBracket '|' char or charsInBracket
    ExpNode create_Alternator_node(String character)
    {
        // create alternator tree
        ExpNode newNode = new ExpNode(NodeType.ALT, character);
        return newNode;
    }

    ExpNode create_AltLeft_node()
    {
        // create alternator tree
        ExpNode newNode = new ExpNode(NodeType.ALT_LEFT, "ALT_L");
        return newNode;
    }
}
