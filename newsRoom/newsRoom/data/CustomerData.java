package data.data;

import java.io.Serializable;

import data.interfaces.INotification;

public class CustomerData implements Serializable {
	private static final long serialVersionUID = 1L;
	public String name;
	public INotification broadcast;
}
