import java.io.*;  
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.IOException;

enum ChildType {
    CHARACTER,
    ALT,
    KSTAR,
    KPLUS,
    BRACKET
}

public class ExpChild 
{   
    ChildType childType;
    String exp;
    ExpChild  point_back;
    ExpChild point_next;

    ExpChild(ChildType type_name, String exp_str)
    {
        this.childType = type_name;
        this.exp = exp_str;
    }
}