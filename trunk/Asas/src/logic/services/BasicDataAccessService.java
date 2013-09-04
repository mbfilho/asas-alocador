package logic.services;

import java.util.Collection;

import data.NamedEntity;
import data.repository.Repository;

public abstract class BasicDataAccessService <T extends NamedEntity>{

	protected int getCurrentId(){
		return StateService.getInstance().getCurrentState().getUniqueId();
	}
	
	protected abstract Repository<T> list();
	
	public void update(T entity){
		list().update(entity);
	}
	
	public T getByName(String name){
		return list().get(name);
	}
	
	//TODO: retornar lista imutável
	public Collection<T> all(){
		return list().all();
	}
	
	public void add(T entity){
		list().addInOrder(entity);
	}
	
	public void remove(T entity){
		list().remove(entity);
	}
}
