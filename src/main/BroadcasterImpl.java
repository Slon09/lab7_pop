package main;

import data.CustomerData;
import interfaces.IConfiguration;
import interfaces.INotification;
import interfaces.IRegistration;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/*
    Server
 */
public class BroadcasterImpl extends UnicastRemoteObject implements IRegistration, IConfiguration {

    Map<String, INotification> subscribers = new TreeMap<>();


    INotification notification;

    private static BroadcasterImpl INSTANCE = null;

    public static BroadcasterImpl getINSTANCE() throws RemoteException{
        if(INSTANCE == null)
            INSTANCE = new BroadcasterImpl();
        return INSTANCE;
    }

    private BroadcasterImpl() throws RemoteException {
//        //super(6060);
//        IRegistration registration = (IRegistration) UnicastRemoteObject.exportObject(this, 4040);
//        IConfiguration configuration = (IConfiguration) UnicastRemoteObject.exportObject(this, 3030);
//
//        try{
//            Registry registryCustomer = LocateRegistry.getRegistry(4040);
//            registryCustomer.bind("Registration", registration);
//
//            Registry registryEditor = LocateRegistry.getRegistry(3030);
//            registryEditor.bind("Configuration", configuration);
//        }catch(AlreadyBoundException e){
//            System.err.println("Server Exception: " + e.toString());
//            e.printStackTrace();
//        }

    }


    @Override
    public int addNews(String news) throws RemoteException {
        return 0;
    }

    @Override
    public boolean removeNews(int id) throws RemoteException {
        return false;
    }

    @Override
    public CustomerData[] getCustomers() throws RemoteException {
        return (CustomerData[]) subscribers.values().toArray();
    }

    @Override
    public boolean register(String name, INotification broadcast) throws Exception {
        if(isRegistered(name)){
            System.err.println("Użytkownik jest już zarejestrowany");
            return false;
        }else{
            subscribers.put(name, broadcast);
            return true;
        }

    }

    @Override
    public boolean unregister(String name) throws RemoteException {
        if(isRegistered(name)){
            subscribers.remove(name);
            return true;
        }
        return false;
    }

    private boolean isRegistered(String name){
        if(subscribers.get(name) == null) return false;
        return true;
    }
}
