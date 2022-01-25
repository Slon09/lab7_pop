package main;

import gui.Terminal;
import interfaces.IConfiguration;
import interfaces.INotification;
import interfaces.IRegistration;

import javax.imageio.plugins.tiff.BaselineTIFFTagSet;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RunBroadcaster {


    public static void main(String[] args) throws RemoteException, AlreadyBoundException {
        IRegistration registrationServer = (IRegistration) BroadcasterImpl.getINSTANCE();
        Registry registryCustomer = LocateRegistry.createRegistry(3030);
        IConfiguration configurationServer = (IConfiguration) BroadcasterImpl.getINSTANCE();
        Registry registryEditor = LocateRegistry.createRegistry(4040);
        registryEditor.bind("ConfigServer", configurationServer);
        registryCustomer.bind("RegistryServer", registrationServer);

        try {
            runBroadcaster();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void runBroadcaster() throws Exception {
        BroadcasterImpl b =  BroadcasterImpl.getINSTANCE();
        b.setTerminal();
        while(b.isRunning()){
            b.react(b.getTerminal().getCommand());
        }
    }

}
