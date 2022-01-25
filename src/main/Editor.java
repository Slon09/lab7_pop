package main;

import data.CustomerData;
import gui.Terminal;
import interfaces.IConfiguration;
import interfaces.IRegistration;

import java.io.*;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;


public class Editor {

    private IConfiguration server = BroadcasterImpl.getINSTANCE();

    private Terminal terminal;
    private String name = "";

    boolean exitCondition = true;


    public Editor() throws RemoteException {
        terminal = new Terminal("Editor:" + name);
    }

    public void startEditor() throws Exception{
        Registry registry = LocateRegistry.getRegistry("localhost", 4040);
        server = (IConfiguration)  registry.lookup("ConfigServer");
    }

    public Terminal getTerminal() {
        return terminal;
    }

    protected void react(String cmd) throws Exception {
        String[] tab = cmd.split("; ");
        ArrayList<String> args = new ArrayList<String>(Arrays.asList(Arrays.copyOfRange(tab, 1, tab.length)));
        switch(tab[0].toLowerCase()) {
            case "":
                break;
            case "exit":
                exitCondition = false;
                terminal.clearCommand();
                break;
            case "addnews":
                if (args.size() == 0) {
                    terminal.errMsg("Nie podano newsa!");
                } else if (args.size() == 2) {
                    if (args.get(0).equals("path")) {
                        try {
                            File file = new File(args.get(1));
                            BufferedReader br = new BufferedReader(new FileReader(file));
                            StringBuilder sb = new StringBuilder("");
                            String st;
                            while ((st = br.readLine()) != null) {
                                sb.append(st);
                            }
                            addNews(sb.toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else if (args.get(0).equals("news")) {
                        addNews(args.get(1));
                    } else {
                        terminal.errMsg("Nie rozpoznano argumentu 0");
                    }
                } else {
                    terminal.errMsg("Niepoprawna ilość argumentów");
                }
                terminal.clearCommand();
                break;
            case "removeNews":

                if (args.size() == 0) {
                    terminal.errMsg("Nie podano argumentu!");
                }else if(args.size() == 1) {
                    if(server.removeNews(Integer.parseInt(args.get(0)))){
                        terminal.setMsg("Pomyślnie usunięto newsa!");
                    }else{
                        terminal.errMsg("Nie udało się usunąć newsa!");
                    }
                }else {
                    terminal.errMsg("Niepoprawna ilość argumentów");
                }

                terminal.clearCommand();
            break;

            case "getcustomers":

                if(args.size() == 0){
                    getCustomers();
                }else{
                    terminal.errMsg("Niepoprawna liczba argumentów!");
                }

            terminal.clearCommand();
            break;
            default:
                terminal.setMsg("Niznane polecenie: " + tab[0]);
                terminal.clearCommand();
            break;
        }

    }

    private void addNews(String news){
        try{
            if(server.addNews(news)){
                terminal.setMsg("Pomyślnie dodano newsa!");
            }else{
                terminal.setMsg("Nie udało się dodać newsa!");
            }
        }catch(RemoteException e){
            e.printStackTrace();
        }
    }


    private void getCustomers() throws RemoteException {
        CustomerData[] customers = server.getCustomers();

        terminal.setMsg("Lista subskrybentów: ");
        for(CustomerData x: customers){
            terminal.setMsg("-" + x.name);
        }
    }
}
