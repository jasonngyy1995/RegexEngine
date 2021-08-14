import java.util.ArrayList;

public class Parser {

    private class exp_block 
    {   
        ArrayList<exp_block > point_back;
        ArrayList<exp_block > point_next;
    }

    // store all blocks
    ArrayList<exp_block > expressionTree;
    
    // reg_exp ::= a single item of 'a-Z', '0-9', ' '
    void parse_regExp()
    {

    }

    // kleeneStar ::= reg_exp'*' | expInBracket'*'
    void parse_kleeneStar()
    {

    }

    // kleenePlus ::= reg_exp'+' | expInBracket'+'
    void parse_kleenePlus()
    {

    }

    // ExpInBracket ::= '(' reg_exp | kleeneStar | kleenePlus ')'
    void parse_ExpInBracket()
    {

    }

    // Alternator ::= reg_exp or expInBracket '|' reg_exp or expInBracket
    void parse_Alternator()
    {

    }
  
    // read next exp
    void next_exp() 
    {

    }
    
   




}
