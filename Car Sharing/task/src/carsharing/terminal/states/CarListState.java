package carsharing.terminal.states;


import carsharing.DbUtil;
import carsharing.terminal.TerminalContext;

import java.util.ArrayList;
import java.util.List;

public class CarListState implements State {
    String currentCompany ;
    private String currentCustomer;
    List<String> cars = new ArrayList<>();

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
            System.out.println("Car list:");
            cars.forEach(c -> System.out.println(number[0]++ + ". " + c));
            if (currentCustomer != null) {
                return true;
            }

        } else {
            System.out.println("The car list is empty!");

        }
        TerminalContext.getInstance().setTerminalState(new CompanyMenuState(currentCompany));
        return false;
    }

    @Override
    public void doAction(int actionType) {
        if (actionType == 0) {
            TerminalContext.getInstance().setTerminalState(CompaniesListState.getInstance());
        } else {
            if (DbUtil.getCar(currentCustomer, currentCompany, cars.get(actionType - 1))) {
                System.out.println();
            }
        }

    }

    public void currentCustomer(String currentCustomer) {
        this.currentCustomer = currentCustomer;
    }
}
