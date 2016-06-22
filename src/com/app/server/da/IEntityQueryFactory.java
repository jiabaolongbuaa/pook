/**
 * 
 */
package com.app.server.da;

public interface IEntityQueryFactory {

	<E> IEntityQuery<E> createQuery(Class<E> clazz);
}
