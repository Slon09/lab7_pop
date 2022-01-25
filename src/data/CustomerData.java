package data;

import interfaces.INotification;

import java.io.Serializable;

public class CustomerData implements Serializable {
    private static final long serialVersionUID = 1L;
    public String name;
    public INotification broadcast;

    public CustomerData(String name, INotification broadcaster){
        this.name = name;
        this.broadcast = broadcaster;
    }
}