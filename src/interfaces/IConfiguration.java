package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import data.CustomerData;

public interface IConfiguration extends Remote {
    int addNews(String news) throws RemoteException;
    boolean removeNews(int id) throws RemoteException;
    CustomerData[] getCustomers() throws RemoteException;
}