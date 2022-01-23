package data.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import data.data.NewsData;

public interface INotification extends Remote {
  void notify(NewsData newsdata) throws RemoteException;
}
