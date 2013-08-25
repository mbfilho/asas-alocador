package presentation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.AbstractListModel;



public class OrderedJListModel<T extends Comparable<T>> extends AbstractListModel<T>{
	private static final long serialVersionUID = -7473915893149369589L;
	private List<T> elements;
	
	
	public OrderedJListModel(){
		elements = new ArrayList<T>();
	}
	
	public List<T> getAllPairs(){
		return elements;
	}
	
	public void clear(){
		elements.clear();
	}
	
	public <E> Collection<E> getAllDataElements(){
		List<E> elements = new ArrayList<E>();
		for(T pair : getAllPairs()){
			elements.add(((NamedPair<E>) pair).data);
		}
		return elements;
	}
	
	public void addInOrder(T a){
		int idx = 0;
		while(idx < elements.size() && elements.get(idx).compareTo(a) < 0)
			++idx;
		elements.add(idx, a);
		fireIntervalAdded(this, idx, idx);
	}
	
	public void remove(int idx){
		elements.remove(idx);
		fireIntervalRemoved(this, idx, idx);
	}
	
	public void remove(T pairToRemove){
		for(int i = 0; i < elements.size(); ++i){
			if(elements.get(i) == pairToRemove){
				remove(i);
				break;
			}
		}
	}
	
	public int getSize() {
		return elements.size();
	}

	public T getElementAt(int index) {
		return elements.get(index);
	}
}
