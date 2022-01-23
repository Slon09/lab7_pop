package main;

import data.NewsData;
import gui.Terminal;
import interfaces.INotification;
import interfaces.IRegistration;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Customer implements INotification {

    private static final String serverIP = "127.0.0.1";
    private IRegistration serverBroadcast = BroadcasterImpl.getINSTANCE();

    private int pesel;
    private String name = Integer.toString(pesel);
    private String password;


    private Terminal terminal;

    public Customer() throws RemoteException {
        UnicastRemoteObject.exportObject(this,4040);
        terminal = new Terminal("Customer: " + name);
    }


    public void startCustomer() throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(serverIP, 1099);
        serverBroadcast = (IRegistration) registry.lookup("Registration");
    }

    @Override
    public void notify(NewsData newsdata) throws RemoteException {
        System.out.println(newsdata.toString());
    }

    public void registry(Customer c) throws Exception {
        serverBroadcast.register(this.name, this);
    }

}
