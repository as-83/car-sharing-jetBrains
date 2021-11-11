package carsharing.terminal.states;


import carsharing.DbUtil;
import carsharing.terminal.TerminalContext;

import java.util.ArrayList;
import java.util.List;

public class CarListState implements State {
    String currentCompany ;
    List<String> cars = new ArrayList<>();
    private TerminalContext terminal = TerminalContext.getInstance();

    public CarListState(){

    }
    public CarListState(String company){
        currentCompany = company;
    }


    @Override
    public boolean printMenu() {
        cars = DbUtil.getCompanyCars(currentCompany);
        if (cars.size() != 0) {
            int[] number = {1};
            if (terminal.getCurrentCustomer() == null) {
                System.out.println("Car list:");
            } else {
                System.out.println("Choose a car:");
            }
            cars.forEach(c -> System.out.println(number[0]++ + ". " + c));
            if (terminal.getCurrentCustomer() != null) {
                return true;
            }

        } else {
            System.out.println("The car list is empty!");

        }
        terminal.setTerminalState(new CompanyMenuState(currentCompany));
        return false;
    }

    @Override
    public void doAction(int actionType) {
        if (actionType == 0) {
            TerminalContext.getInstance().setTerminalState(CompaniesListState.getInstance());
        } else {
            if (DbUtil.rentCar(terminal.getCurrentCustomer(), currentCompany, cars.get(actionType - 1))) {
                System.out.println("You rented '" + cars.get(actionType -1) + "'" );
            }
            terminal.setTerminalState(ListOfCustomersState.getInstance());
        }

    }

}
