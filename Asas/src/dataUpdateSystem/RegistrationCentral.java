package dataUpdateSystem;

import java.util.HashSet;

public class RegistrationCentral {

	private static HashSet<Updatable> registeredCustomers = new HashSet<Updatable>();
	
	public static void register(Updatable customer){
		registeredCustomers.add(customer);
	}
	
	public static void houveUpdate(){
		System.out.println("Houve update: " + registeredCustomers.size());
		for(Updatable up : registeredCustomers)
			up.onDataUpdate(null);
	}
}
