import java.util.ArrayList;

public class InputChecker
{
    ArrayList<State> available_states = new ArrayList<State>();

    // function to check if the state already exists in available_states
    boolean check_if_state_exist(State state)
    {
        for (State existing_state: this.available_states)
        {
            if (existing_state.getStateId() == state.getStateId())
            {
                return true;
            }
        }
        return false;
    }

    // function to get state with epsilon transition
    ArrayList<State> searchStateWithEpsilonTransition(State current_state)
    {
        ArrayList<State> newList_of_epsilonState = new ArrayList<State>();
        ArrayList<State> StateWithEpsilonTransition = current_state.get_inputMatched_next_state("epsilon");
        for (State state: StateWithEpsilonTransition)
        {
            if (check_if_state_exist(state) == false)
            {
                newList_of_epsilonState.add(state);
            }
        }
        return newList_of_epsilonState;
    }

    // function to add all states with epsilon transition to available_states
    void add_StateWithEpsilonTransition_to_available()
    {
        ArrayList<State> StateWithEpsilon = new ArrayList<State>();
        for (int i = 0; i < available_states.size(); i++)
        {
            ArrayList<State> epsilonTransition_from_currentAvailableState = searchStateWithEpsilonTransition(available_states.get(i));
            StateWithEpsilon.addAll(epsilonTransition_from_currentAvailableState);

            this.available_states.addAll(epsilonTransition_from_currentAvailableState);
        }

        // check if above returned states have epsilon transition as well
        while (StateWithEpsilon.size() > 0)
        {
            ArrayList<State> previous_state_with_epsilon = StateWithEpsilon;
            StateWithEpsilon = new ArrayList<State>();

            for (int i = 0; i < previous_state_with_epsilon.size(); i++)
            {
                ArrayList<State> epsilonTransition_from_currentAvaState = searchStateWithEpsilonTransition(previous_state_with_epsilon.get(i));
                StateWithEpsilon.addAll(epsilonTransition_from_currentAvaState);

                this.available_states.addAll(epsilonTransition_from_currentAvaState);
            }

        }
    }

    // start input checking and creating available_states at the start state
    void init_available_state(State init_state)
    {
        this.available_states = new ArrayList<State>();
        this.available_states.add(init_state);

        add_StateWithEpsilonTransition_to_available();
    }

    // update the available_states when processing input
    void get_available_states(String str_input)
    {
        for (int i = available_states.size()-1; i>=0; i--)
        {
            if (available_states.get(i).get_acceptedInput().contains(str_input))
            {
                ArrayList<Transition> tmp_tran = available_states.get(i).transitions;
                for (int j = 0; j < tmp_tran.size(); j++)
                {
                    if (tmp_tran.get(j).transExp.equals(str_input))
                    {
                        available_states.add(tmp_tran.get(j).next_state);
                    }
                }
            }
            available_states.remove(i);
        }
        add_StateWithEpsilonTransition_to_available();
    }

    // to check if the current available state transits to the accepting state
    boolean reachAcceptingState(String str_input, int accepting_state_id, State init_state)
    {
        // when "enter" button with no string input
        if (str_input.equals(""))
        {
            init_available_state(init_state);
        } else
        {
            get_available_states(str_input);
        }

        for (int i = 0; i < available_states.size(); i++)
        {
            if (available_states.get(i).getStateId() == accepting_state_id)
            {
                return true;
            }
        }
        return false;
    }
}
