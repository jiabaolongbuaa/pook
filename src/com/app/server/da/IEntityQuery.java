/**
 * 
 */
package com.app.server.da;

import java.util.List;

public interface IEntityQuery<E> {
	/**
	 * Retrieve the total entity count.
	 * 
	 * @return
	 */
	int rowCount();

	/**
	 * Retrieve ONE entity only.
	 * 
	 * @return
	 */
	E get();

	/**
	 * Retrieve a list of entity.
	 * 
	 * @return
	 */
	List<E> list();

	/**
	 * Retrieve a list of entity. The retrieved entity count cannot exceed the
	 * given maxResults
	 * 
	 * @param maxResults
	 * @return
	 */
	List<E> list(int maxResults);

	/**
	 * Retrieve a list of entity from the the position of startPosition. The
	 * retrieved entity count cannot exceed the given maxResults
	 * 
	 * @param maxResults
	 * @return
	 */
	List<E> list(int startPosition, int maxResults);

	/**
	 * Apply an "in" constraint to the named property
	 * 
	 * @param property
	 * @param values
	 * @param ignoreNull
	 * @return
	 */
	IEntityQuery<E> in(String property, List<String> values, boolean ignoreNull);

	/**
	 * Sort the result by the given property.
	 * 
	 * @param property
	 * @param desc
	 * @return
	 */
	IEntityQuery<E> desc(String property, boolean desc);

	/**
	 * Apply an "equal" constraint to the named property
	 * 
	 * @param property
	 * @param value
	 * @param ignoreNull
	 *            Should this constraint be skipped if the given value is null?
	 * @return
	 */
	IEntityQuery<E> eq(String property, Object value, boolean ignoreNull);

	/**
	 * Apply an "greater or equal" constraint to the named property
	 * 
	 * @param property
	 * @param value
	 * @param ignoreNull
	 * @return
	 */
	IEntityQuery<E> ge(String property, Object value, boolean ignoreNull);

	/**
	 * Apply an "less or equal" constraint to the named property
	 * 
	 * @param property
	 * @param value
	 * @param ignoreNull
	 * @return
	 */
	IEntityQuery<E> le(String property, Object value, boolean ignoreNull);

	IEntityQuery<E> ne(String property, Object value, boolean ignoreNull);

	/**
	 * Apply an "like" constraint to the named property
	 * 
	 * @param property
	 * @param value
	 * @param ignoreNull
	 *            Should this constraint be skipped if the given value is null?
	 * @return
	 */
	IEntityQuery<E> like(String property, String value, boolean ignoreNull);

	/**
	 * Set properties to be fetched. Default is to fetch all entity properties
	 * from DB. The method can be invoked only once.
	 * 
	 * @param properties
	 * @return
	 */
	IEntityQuery<E> setFetchedProperties(String... properties);

	/**
	 * Apply a "between" constraint to the named property.
	 * 
	 * @param property
	 * @param low
	 * @param high
	 * @return
	 */
	IEntityQuery<E> between(String property, Object low, Object high);

	/**
	 * Apply an "equal" constraint to two properties
	 * 
	 * @param property
	 * @param otherProperty
	 * @return
	 */
	IEntityQuery<E> eqProperty(String property, String otherProperty);

	IEntityQuery<E> createAlias(String associationPath, String alias);

	IEntityQuery<E> isEmpty(String propertyName);

	IEntityQuery<E> isNull(String propertyName);

	IEntityQuery<E> isNotNull(String propertyName);

}
