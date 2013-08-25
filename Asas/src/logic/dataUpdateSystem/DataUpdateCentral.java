package logic.dataUpdateSystem;

import java.util.HashSet;

public class DataUpdateCentral {

	@SuppressWarnings("unchecked")
	private static HashSet<Updatable> registeredCustomers[] = new HashSet[]
	{
		new HashSet<Updatable>(), new HashSet<Updatable>()
	};
	
	public static int SERVICE_CUSTOMER = 0;
	
	public static void signIn(Updatable customer, CustomerType type){
		registeredCustomers[type.getPriority()].add(customer);
	}
	
	public static void signOut(Updatable customer){
		System.out.println("Unregistering: " + customer);
		for(int i = 0; i < CustomerType.TYPES_COUNT; ++i)
			registeredCustomers[i].remove(customer);
	}
	
	public static void registerUpdate(String desc){
		for(HashSet<Updatable> set : registeredCustomers){
			System.out.println("Nesta prioridade: " + set.size());
			for(Updatable up : set)
				up.onDataUpdate(null);
		}
	}
}
