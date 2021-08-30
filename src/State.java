import java.util.ArrayList;

public class State
{
    int id;
    ArrayList<Transition> transitions;

    State(int id)
    {
        this.id = id;
    }

    void add_trans_func(Transition trans_func)
    {
        this.transitions.add(trans_func);
    }
}
