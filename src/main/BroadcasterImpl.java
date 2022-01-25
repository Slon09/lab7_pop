package main;

import data.CustomerData;
import data.NewsData;
import gui.Terminal;
import interfaces.IConfiguration;
import interfaces.INotification;
import interfaces.IRegistration;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

/*
    Server
 */
public class BroadcasterImpl implements IRegistration, IConfiguration {

    Map<String, INotification> subscribers = new TreeMap<>();
    Stack<NewsData> newsList = new Stack<>();

    INotification notification;

    private boolean runCondition = true;
    private Terminal terminal;
    private static BroadcasterImpl INSTANCE = null;

    public static BroadcasterImpl getINSTANCE() throws RemoteException{
        if(INSTANCE == null)
            INSTANCE = new BroadcasterImpl();
        return INSTANCE;
    }

    private BroadcasterImpl() throws RemoteException {
       UnicastRemoteObject.exportObject(this,0);

    }

    void setTerminal(){
        if(terminal == null){
            terminal = new Terminal("Broadcaster");
        }
    }

    @Override
    public boolean addNews(String news) throws RemoteException {
        try {
            newsList.push(new NewsData(news));
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean removeNews(int id) throws RemoteException {
        boolean found = false;
        for(NewsData x: newsList){
            if(x.getId() == id){
                found = true;
                newsList.remove(x);
                break;
            }
        }
        return found;
    }

    @Override
    public CustomerData[] getCustomers() throws RemoteException {
        CustomerData[] arr = new CustomerData[subscribers.size()];
        ArrayList<CustomerData> list = new ArrayList();
        for(String name : subscribers.keySet()){
            list.add(new CustomerData(name, subscribers.get(name)));
        }
        return list.toArray(arr);

    }

    @Override
    public boolean register(String name, INotification broadcast) throws Exception {
        System.out.println("register func");
        if(isRegistered(name)){
            System.out.println("Użytkownik jest już zarejestrowany");
            throw new Exception("Użytkownik jest już zarejestrowany");
        }else{
            subscribers.put(name, broadcast);
            System.out.println("dodano użytkownika");
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

    boolean notifyCustomers(){
        for(String x : subscribers.keySet()){
            try {
                subscribers.get(x).notify(newsList.peek());
            }catch (Exception e){
                terminal.setMsg(e.toString());
                return false;
            }
        }
        return true;
    }

    public boolean isRunning() {
        return runCondition;
    }

    public Terminal getTerminal() {
        return terminal;
    }

    protected void react(String cmd) throws Exception {
        String[] tab = cmd.split(" ");
        ArrayList<String> args = new ArrayList<String>(Arrays.asList(Arrays.copyOfRange(tab, 1, tab.length)));
        switch(tab[0].toLowerCase()){
            case "":
                break;
            case "exit":
                runCondition = false;
                terminal.clearCommand();
                break;

            case "notify":

                if(notifyCustomers()){
                    terminal.setMsg("Pomyślnie przesłano powiadomienia!");
                }else{
                    terminal.setMsg("Nie udało się wysłać wszystkich powiadomień!");
                }

                terminal.clearCommand();
                break;
            default:
                terminal.setMsg("Niznane polecenie: " + tab[0]);
                terminal.clearCommand();
                break;
        }

    }

}
