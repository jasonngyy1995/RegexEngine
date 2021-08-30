public class Transition
{
    String transExp;
    State next_state;

    Transition (String node_exp, State next_state)
    {
        this.transExp = node_exp;
        this.next_state = next_state;
    }

}

