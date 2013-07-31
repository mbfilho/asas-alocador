package classEditor;

public class NamedPair <T>{
	public String name;
	public T data;
	
	public NamedPair(String name, T obj){
		this.name = name;
		data = obj;
	}
	
	public String toString(){
		return name;
	}
}
