package logic.services;

import java.util.Collection;

import data.NamedEntity;
import data.persistentEntities.State;
import data.repository.Repository;

public abstract class BasicDataAccessService <T extends NamedEntity>{

	private State dataState;
	
	protected abstract Repository<T> list();
	
	public BasicDataAccessService(State currentState){
		dataState = currentState;
	}
	
	protected int getCurrentId(){
		return dataState.getUniqueId();
	}
	
	public State getState(){
		return dataState;
	}
	
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
}
