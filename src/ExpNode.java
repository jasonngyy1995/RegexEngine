import java.io.*;  
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.IOException;

enum NodeType {
    REGEX,
    CHARACTER,
    ALT,
    ALT_LEFT,
    KSTAR,
    KPLUS
}

public class ExpNode 
{   
    NodeType nodeType;
    String exp;
    ArrayList<ExpNode> children;

    ExpNode(NodeType type_name, String exp_str)
    {
        this.nodeType = type_name;
        this.exp = exp_str;
    }
}