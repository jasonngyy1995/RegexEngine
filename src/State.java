import java.util.ArrayList;

public class State
{
    int id;
    String trans_exp;
    ArrayList<Transition> transitions = new ArrayList<Transition>();
    ArrayList<String> accepted_input = new ArrayList<String>();

    // constructor
    State(int id)
    {
        this.id = id;
    }

    // function to add transition functions
    void add_trans_func(Transition trans_func)
    {
        this.transitions.add(trans_func);
    }

    // id setter
    void setStateId(int id)
    {
        this.id = id;
    }

    // id getter
    int getStateId()
    {
        return this.id;
    }

    // transExp setter
    void setTrans_exp(String tr)
    {
        this.trans_exp = tr;
    }

    // function to add accepted input
    void addTo_acceptedInput(String accept)
    {
        this.accepted_input.add(accept);
    }

    // accepted input getter
    ArrayList<String> get_acceptedInput()
    {
        return this.accepted_input;
    }

    // check if input matches transition
    ArrayList<State> get_inputMatched_next_state(String str_input)
    {
        ArrayList<State> nextState = new ArrayList<State>();

        for (int i = 0; i < transitions.size(); i++)
        {
            if (transitions.get(i).transExp.equals(str_input))
            {
                nextState.add(transitions.get(i).next_state);
            }
        }
        return nextState;
    }


}
