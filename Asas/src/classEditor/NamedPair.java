package classEditor;

import basic.NamedEntity;

public class NamedPair <T> implements NamedEntity{
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
}
