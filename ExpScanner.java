import java.io.*;  
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.IOException;

public class ExpScanner {

    // check if exp is an operator
    boolean checkOperator(char expression)
    {
        switch(expression)
        {
            case '(':
                return true;
            case ')':
                return true;
            case '|':
                return true;
            case '+':
                return true;
            case '*':
                return true;
            case ' ':
                return true;
            default:
                return false;
        }
    }

    // check if exp is an alphabet
    boolean checkAlphabet(char exp)
    {
        if ((exp >= 'a') || (exp <= 'z') || ((exp >= 'A') || (exp <= 'Z')))
        {
            return true;
        }
        return false;
    }

    boolean checkInt(char exp)
    {
        try {
            int tmp = Integer.parseInt(String.valueOf(exp));
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    boolean matchRule(String firstInput)
    {   
        boolean isOp, isAlp, isInt;
        for (char exp: firstInput.toCharArray())
        {   
            isOp = checkOperator(exp);
            isAlp = checkAlphabet(exp);
            isInt = checkInt(exp);

            if (isOp == true || isAlp == true || isInt == true)
            {
                return true;
            }
        }
        return false;
    }

    // take the first line of input
    void passFirstLine(String firstLineInput)
    {   
        boolean res;
        res = matchRule(firstLineInput);

        if (res == false)
        {
            System.out.println("Incorrect input format, input can only be a-Z, 0-9, |, *, +, spaces, bracket");
            System.exit(1);
        } else {
            System.out.println("Ready");
        }
    }
}
