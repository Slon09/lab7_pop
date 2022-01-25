package main;

import gui.Terminal;

import java.awt.event.WindowEvent;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class RunCustomer {

    public static void main(String[] args) throws Exception {
        Customer customer = new Customer();
        customer.startCustomer();


        while(customer.exitCondition){
            customer.react(customer.getTerminal().getCommand());
        }

        customer.getTerminal().dispatchEvent(new WindowEvent(customer.getTerminal(), WindowEvent.WINDOW_CLOSING));
    }
}
