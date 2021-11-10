package carsharing.terminal.states;

public interface State {
    boolean printMenu();
    void doAction(int actionType);
}
