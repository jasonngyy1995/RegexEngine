import java.util.ArrayList;

public class State
{
    int id;
    String trans_exp;
    ArrayList<Transition> transitions = new ArrayList<Transition>();

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
}
