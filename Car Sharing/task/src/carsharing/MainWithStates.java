package carsharing;

import carsharing.terminal.TerminalContext;
import carsharing.terminal.states.MainMenuState;
import carsharing.terminal.states.TerminationState;

import java.util.Scanner;

public class MainWithStates {
    public static void main(String[] args) {
        if (args.length != 0 && args[0].equals("-databaseFileName")) {
            DbUtil.setDatabaseFileName(args[1]);
        }
        DbUtil.createTables();
        TerminalContext terminalContext = TerminalContext.getInstance();
        terminalContext.setTerminalState(MainMenuState.getInstance());
        Scanner scanner = new Scanner(System.in);

        while (!(terminalContext.getTerminalState() instanceof TerminationState)) {
            if (terminalContext.printMenu()) {
                terminalContext.doAction(Integer.parseInt(scanner.nextLine()));
            }
        }
    }
}
