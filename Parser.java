import java.io.*;  
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.IOException;

public class Parser {

    // reg_exp ::= a single item of 'a-Z', '0-9', ' '
    ExpChild parse_character(String character)
    {
        ExpChild newChild = new ExpChild(ChildType.CHARACTER, character);
        return newChild;
    }

    // kleeneStar ::= reg_exp'*' | expInBracket'*'
    ExpChild parse_kleeneStar(String character)
    {
        ExpChild newChild = new ExpChild(ChildType.KSTAR, character);
        return newChild;
    }

    // kleenePlus ::= reg_exp'+' | expInBracket'+'
    ExpChild parse_kleenePlus(String character)
    {
        ExpChild newChild = new ExpChild(ChildType.KPLUS, character);
        return newChild;
    }

    // ExpInBracket ::= '(' char | kleeneStar | kleenePlus ')'
    ExpChild parse_ExpInBracket(String character)
    {
        ExpChild newChild = new ExpChild(ChildType.BRACKET, character);
        return newChild;
    }

    // Alternator ::= char or charsInBracket '|' char or charsInBracket
    ExpChild parse_Alternator(String character)
    {
        // create alternator tree
        ExpChild newChild = new ExpChild(ChildType.ALT, character);
        return newChild;
    }
  
    // read next exp
    void next_exp(String RegexGrammar) 
    {
        for (int i = 0; i < RegexGrammar.length()-1; i++) {
            
        }
    }
    
   




}
