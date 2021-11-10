package carsharing.terminal.states;

import carsharing.DbUtil;
import carsharing.terminal.TerminalContext;

import java.util.ArrayList;
import java.util.List;

public class CustomerMenuState implements State {
    private TerminalContext terminal = TerminalContext.getInstance();

    private List<String> customers = new ArrayList();


    private static CustomerMenuState customerMenuState = new CustomerMenuState();
    private CustomerMenuState(){

    }

    public static CustomerMenuState getInstance(){
        if (customerMenuState == null) {
            customerMenuState = new CustomerMenuState();
        }
        return customerMenuState;
    }

    @Override
    public boolean printMenu() {
        customers = DbUtil.getAllCustomers();
        if (customers.size() != 0) {
            int[] number = {1};
            System.out.println("Customer list:");
            customers.forEach(c -> System.out.println(number[0]++ + ". " + c));
            System.out.println("0. Exit");
            return true;
        } else {
            System.out.println("The customer list is empty!");
            TerminalContext.getInstance().setTerminalState(MainMenuState.getInstance());
            return false;
        }

    }

    @Override
    public void doAction(int actionType) {
        if (actionType == 0) {
            TerminalContext.getInstance().setTerminalState(MainMenuState.getInstance());
        } else {
            TerminalContext.getInstance().setTerminalState(new CustomerState(customers.get(actionType - 1)));
        }
    }
}
