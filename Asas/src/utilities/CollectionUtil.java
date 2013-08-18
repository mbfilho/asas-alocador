package utilities;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

public class CollectionUtil {
	public static <T> Vector<T> intersectLists(Collection<T> listA, Collection<T> listB){
		Vector<T> resp = new Vector<T>();
		for(T a : listA) if(listB.contains(a)) resp.add(a);
		return resp;
	}
	
	public static <T> boolean equalsWithoutOrder(Collection<T> listA, Collection<T> listB){
		return listA.containsAll(listB) && listB.containsAll(listA);
	}
	
	public static <T> T firstOrDefault(Collection<T> theCollection, T defaultValue){
		if(theCollection.isEmpty()) return defaultValue;
		return theCollection.iterator().next();
	}
	
	public static <T> T firstOrDefault(Collection<T> theCollection){
		return firstOrDefault(theCollection, null);
	}
	
	public static <T> List<T> createList(T ... args){
		List<T> list = new LinkedList<T>();
		for(T a : args) list.add(a);
		return list;
	}
}
