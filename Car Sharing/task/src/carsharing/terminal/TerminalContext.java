package carsharing.terminal;

import carsharing.terminal.states.MainMenuState;
import carsharing.terminal.states.State;

public final class TerminalContext implements State {
    private static  TerminalContext terminalContext = new TerminalContext();
    private State terminalState;

    private TerminalContext() {

    }

    public static  TerminalContext getInstance() {
        if (terminalContext == null) {
            terminalContext = new TerminalContext();
        }
        return terminalContext;
    }

    public State getTerminalState() {
        return terminalState;
    }

    public void setTerminalState(State terminalState) {
        this.terminalState = terminalState;
    }


    @Override
    public boolean printMenu() {
        return terminalState.printMenu();
    }

    @Override
    public void doAction(int actionType) {
        terminalState.doAction(actionType);
    }
}
