package main;

import data.CustomerData;
import data.NewsData;
import gui.Terminal;
import interfaces.INotification;
import interfaces.IRegistration;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;

public class Customer implements INotification {

    private static final String serverIP = "192.168.1.5";
    private IRegistration serverBroadcast = BroadcasterImpl.getINSTANCE();

    private CustomerData customerData;
    private int pesel;
    private String name = Integer.toString(pesel);
    private String password;

    boolean exitCondition = true;

    private Terminal terminal;

    public Customer() throws Exception {
        UnicastRemoteObject.exportObject(this,0);
        terminal = new Terminal("Customer: " + name);


    }

    public Customer(String name) throws RemoteException {
        super();
        customerData.name = name;
        customerData.broadcast = this;
    }


    public void startCustomer() throws Exception {
        Registry registry = LocateRegistry.getRegistry("localhost",3030);
        serverBroadcast = (IRegistration) registry.lookup("RegistryServer");
        //registry(this);
    }



    @Override
    public void notify(NewsData newsdata) throws RemoteException {
        terminal.setMsg("\n-----------------------------");
        terminal.setMsg("Powiadomienie:");
        terminal.setMsg(newsdata.getDate().toString());
        terminal.setMsg(newsdata.getNews());
        terminal.setMsg("-----------------------------\n");
    }

    public void registry() throws Exception {
        try {
            if(customerData == null){
                terminal.errMsg("Brak argumentów potrzebnych do rejestracji!");
            }else{
                if(serverBroadcast.register(customerData.name, this));
                terminal.setMsg("Zarejestrowano pomyślnie!");
            }
        }catch (Exception e){
            terminal.errMsg(e.toString());
        }


    }
    public void registry(String name){
        try{
            if(customerData == null) {
                terminal.setMsg("Próba rejestracji");
                customerData = new CustomerData(name, (INotification) this);
                if (serverBroadcast.register(name, this)) {
                    terminal.setMsg("Zarejestrowano pomyślnie!");
                }else{
                    terminal.setMsg("Niepowodznie!");
                }
            }else{
                registry();
            }
        }catch(Exception e){
            terminal.errMsg(e.toString());
        }

    }

    public void unregister(String name){
       try{
           if(serverBroadcast.unregister(name)){
               terminal.setMsg("Pomyślnie anulowano subskrypcje!");
           }else{
               terminal.setMsg("Coś poszło nie tak!");
           }
       }catch(Exception e){
           terminal.errMsg(e.toString());
       }


    }

    public Terminal getTerminal(){
        return terminal;
    }


    protected void react(String cmd) throws Exception {
        String[] tab = cmd.split(" ");
        ArrayList<String> args = new ArrayList<String>(Arrays.asList(Arrays.copyOfRange(tab, 1, tab.length)));
        switch(tab[0].toLowerCase()){
            case "":
                break;
            case "exit":
                exitCondition = false;
                terminal.clearCommand();
                break;
            case "register":
                if(args.size() == 0 ){
                    registry();
                } else{
                    registry(args.get(0));
                }
                terminal.clearCommand();
            break;

            case "unregister":
                if(customerData != null){
                    unregister(customerData.name);

                }else if(args.size() == 0){
                    terminal.errMsg("Nie podano argumentu!");
                }else{
                    unregister(args.get(0));
                }
                terminal.clearCommand();
            break;
            case "name":
                if(args.size() == 0){
                    terminal.errMsg("Nie podano argumentu!");
                }else{
                    setName(args.get(0));
                    terminal.setMsg("Pomyślnie ustawiono nick na: " + args.get(0));
                }
                terminal.clearCommand();
            break;
            default:
                terminal.setMsg("Niznane polecenie: " + tab[0]);
                terminal.clearCommand();
            break;
        }

    }


    private void setName(String name){
        if(customerData == null){
            customerData = new CustomerData(name, this);
        }else{
            customerData.name = name;
        }
    }
}
