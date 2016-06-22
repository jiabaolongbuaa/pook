package com.app.server.da;

public interface IEntityPersist {
	
	public void save(Object object);
	
	public void update(Object object);
	
	public void saveOrUpdate(Object object);
	 
	public void remove(Object object);
}
