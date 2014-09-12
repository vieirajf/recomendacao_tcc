package br.ce.qxa.ufc.repositorio;

import java.util.List;
import java.util.Map;

import br.ce.qxa.ufc.enumeration.QueryType;


public interface GenericRepository<T> {
	
	public abstract void save(T entity);
	
	public abstract void update(T entity);

	public abstract void delete(T entity);

	public abstract T find(Class<T> entityClass, Object id);

	public abstract List<T> find(Class<T> entityClass);

	public abstract List<T> find(Class<T> entityClass, int firstResult, int maxResults);

	public abstract List<T> find(String queryName,
			Map<String, Object> namedParams);

	/**
	 * 
	 * @param type
	 * @param query
	 * @param namedParams
	 * @return
	 */
	public abstract List<T> find(QueryType type, String query,
			Map<String, Object> namedParams);

	public abstract List<T> find(String queryName,
			Map<String, Object> namedParams, int firstResult, int maxResults);

	/**
	 * 
	 * @param type
	 * @param query
	 * @param namedParams
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	public abstract List<T> find(QueryType type, String query,
			Map<String, Object> namedParams, int firstResult, int maxResults);

	/**
	 * 
	 * @param query
	 * @param namedParams
	 * @return
	 */
	public abstract T findFirst(String query, Map<String, Object> namedParams);

	/**
	 * 
	 * @param query
	 * @param namedParams
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	public abstract T findFirst(String query, Map<String, Object> namedParams,
			int firstResult, int maxResults);

	/**
	 * 
	 * @param type
	 * @param query
	 * @param namedParams
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	public abstract T findFirst(QueryType type, String query,
			Map<String, Object> namedParams, int firstResult, int maxResults);
}
