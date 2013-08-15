package dataUpdateSystem;

import java.util.HashSet;

public class RegistrationCentral {

	private static HashSet<Updatable> registeredCustomers = new HashSet<Updatable>();
	
	public static void signIn(Updatable customer){
		registeredCustomers.add(customer);
	}
	
	public static void signOut(Updatable customer){
		System.out.println("Unregistering: " + customer);
		registeredCustomers.remove(customer);
	}
	
	public static void registerUpdate(String desc){
		System.out.println("Houve: " + desc + "> " + registeredCustomers.size());
		for(Updatable up : registeredCustomers)
			up.onDataUpdate(null);
	}
}
