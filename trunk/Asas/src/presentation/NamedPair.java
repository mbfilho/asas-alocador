package presentation;

import data.NamedEntity;

public class NamedPair <T> implements NamedEntity, Comparable<NamedPair<T>>{
	public String name;
	public T data;
	
	public NamedPair(String name, T obj){
		this.name = name;
		data = obj;
	}
	
	public String toString(){
		return name;
	}

	public String getName() {
		return name;
	}

	@SuppressWarnings("unchecked")
	public int compareTo(NamedPair<T> other) {
		if(data instanceof Comparable){
			return ((Comparable<T>) data).compareTo(other.data);
		}
		return getName().compareTo(other.getName());
	}
}
