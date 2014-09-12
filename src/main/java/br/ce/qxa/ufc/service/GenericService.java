package br.ce.qxa.ufc.service;

import java.util.List;
import java.util.Map;

import br.ce.qxa.ufc.enumeration.QueryType;

public interface GenericService<T> {
	
	public abstract void save(T entity);

	public abstract void update(T entity);

	public abstract T find(Class<T> entityClass, Object id);

	public abstract List<T> find(Class<T> entityClass);

	public abstract void delete(T entity);
	
	public abstract List<T> find(QueryType type, String query,
			Map<String, Object> namedParams);

}
