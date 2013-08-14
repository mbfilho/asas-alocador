package dataUpdateSystem;

import java.util.HashSet;

public class RegistrationCentral {

	private static HashSet<Updatable> registeredCustomers = new HashSet<Updatable>();
	
	public static void register(Updatable customer){
		registeredCustomers.add(customer);
	}
	
	public static void unregister(Updatable customer){
		System.out.println("Unregistering: " + customer);
		registeredCustomers.remove(customer);
	}
	
	public static void houveUpdate(String desc){
		System.out.println("Houve: " + desc + "> " + registeredCustomers.size());
		for(Updatable up : registeredCustomers)
			up.onDataUpdate(null);
	}
}
