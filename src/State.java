import java.util.ArrayList;

public class State
{
    int id;
    String trans_exp;
    ArrayList<Transition> transitions = new ArrayList<Transition>();
    ArrayList<String> accepted_input = new ArrayList<String>();

    State(int id)
    {
        this.id = id;
    }

    void add_trans_func(Transition trans_func)
    {
        this.transitions.add(trans_func);
    }

    void setStateId(int id)
    {
        this.id = id;
    }

    int getStateId()
    {
        return this.id;
    }

    void setTrans_exp(String tr)
    {
        this.trans_exp = tr;
    }

    void addTo_acceptedInput(String accept)
    {
        this.accepted_input.add(accept);
    }

    ArrayList<String> get_acceptedInput()
    {
        return this.accepted_input;
    }

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
