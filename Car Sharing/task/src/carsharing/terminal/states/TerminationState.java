package carsharing.terminal.states;


public class TerminationState implements State {


    @Override
    public boolean printMenu() {
        return true;
    }

    @Override
    public void doAction(int actionType) {

    }
}
