/**
 * 
 */
package org.zengsource.util.dao;

/**
 * @author Shaoning Zeng
 * @param <E>
 * @since 7.0
 */
public interface DaoInterface<E, F> {

	public E insert(E entity);
	
	public E update(E entity);
	
	public void delete(E entity);

	public E queryById(F id);
	
	public E queryUnique(String name, Object value);
}
