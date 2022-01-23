package main;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class RuNCustomer {

    public static void main(String[] args) throws RemoteException, NotBoundException {
        Customer customer = new Customer();
        customer.startCustomer();

    }
}
