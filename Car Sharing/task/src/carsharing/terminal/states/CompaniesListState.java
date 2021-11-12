package carsharing.terminal.states;


import carsharing.terminal.TerminalContext;
import carsharing.DbUtil;

import java.util.ArrayList;
import java.util.List;

public class CompaniesListState implements State {
    private TerminalContext terminal = TerminalContext.getInstance();
    List<String> companies = new ArrayList<>();

    private static CompaniesListState companiesListState = new CompaniesListState();

    private CompaniesListState(){

    }

    public static CompaniesListState getInstance(){
        if (companiesListState == null) {
            companiesListState = new CompaniesListState();
        }
        return companiesListState;
    }

    @Override
    public boolean printMenu() {
        companies = DbUtil.getAllCompanies();
        if (companies.size() != 0) {
            int[] number = {1};
            System.out.println("Choose a company:");
            companies.forEach(c -> System.out.println(number[0]++ + ". " + c));
            System.out.println("0. Back");
            return true;
        } else {
            System.out.println("The company list is empty!");
            terminal.setTerminalState(ManagerMenuState.getInstance());
            return false;
        }
    }

    @Override
    public void doAction(int actionType) {

        if (actionType == 0) {
            if (terminal.getCurrentCustomer() == null) {
                terminal.setTerminalState(ManagerMenuState.getInstance());
            } else {
                terminal.setTerminalState(new CustomerState(terminal.getCurrentCustomer()));
            }
        } else {
            CompanyMenuState companyMenuState = new CompanyMenuState(companies.get(actionType - 1));
            if (terminal.getCurrentCustomer() != null) {
                CarListState carListState = new CarListState();
                carListState.currentCompany = companies.get(actionType - 1);
                terminal.setTerminalState(new CarListState());
            } else {
                terminal.setTerminalState(companyMenuState);
            }


        }

    }

}
