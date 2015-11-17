package com.geva.manningcatcher.beans;

public class New<T> {

	private boolean isNew;
	
	private T object;
	
	public New(Class<T> type) {
		try {
			object = type.newInstance();
		} catch (InstantiationException e) {
			object = null;
			e.printStackTrace();
		} catch(IllegalAccessException e){
			object = null;
			e.printStackTrace();		
		}
	}
	
	public boolean isNewOffer() {
		return isNew;
	}
	
	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}
	
	public T getObject() {
		return object;
	}

	public void setObject(T object) {
		this.object = object;
	}
	
}
