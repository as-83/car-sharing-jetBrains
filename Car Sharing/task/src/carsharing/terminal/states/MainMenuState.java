package carsharing.terminal.states;


import carsharing.terminal.TerminalContext;

public class MainMenuState implements State {
    private TerminalContext terminal = TerminalContext.getInstance();
    private ManagerMenuState managerMenuState = ManagerMenuState.getInstance();
    private CustomerMenuState customerMenuState = CustomerMenuState.getInstance();

    private static MainMenuState mainMenuState = new MainMenuState();
    private MainMenuState(){

    }

    public static MainMenuState getInstance(){
        if (mainMenuState == null) {
            mainMenuState = new MainMenuState();
        }
        return mainMenuState;
    }

    @Override
    public boolean printMenu() {
        System.out.println(
                "1. Log in as a manager\n" +
                "2. Log in as a customer\n" +
                "3. Create a customer\n" +
                "0. Exit"
        );
        return true;
    }

    @Override
    public void doAction(int actionType) {

        switch (actionType) {
            case 1: terminal.setTerminalState(managerMenuState); break;
            case 2: terminal.setTerminalState(customerMenuState); break;
            case 3: terminal.setTerminalState(CreateCustomerMenuState.getInstance()); break;
            case 0: terminal.setTerminalState(new TerminationState()); break;
            default: break;

        }

    }
}
