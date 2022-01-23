package data.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import data.data.CustomerData;

public interface IConfiguration extends Remote {
  int addNews(String news) throws RemoteException;
  boolean removeNews(int id) throws RemoteException;
  CustomerData[] getCustomers() throws RemoteException; 
}
