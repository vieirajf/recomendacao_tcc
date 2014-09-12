package br.ce.qxa.ufc.service.impl;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.transaction.annotation.Transactional;

import br.ce.qxa.ufc.enumeration.QueryType;
import br.ce.qxa.ufc.repositorio.GenericRepository;
import br.ce.qxa.ufc.service.GenericService;

@Named
public class GenericServiceImpl<T> implements GenericService<T> {

	@Inject
	private GenericRepository<T> genericRepository;
	
	@Transactional
	public void save(T entity) {
		genericRepository.save(entity);

	}

	@Transactional
	public void update(T entity) {
		genericRepository.update(entity);

	}

	@Transactional
	public T find(Class<T> entityClass, Object id) {
		return (T) genericRepository.find(entityClass, id);

	}

	@Transactional
	public List<T> find(Class<T> entityClass) {
		List<T> l = genericRepository.find(entityClass);
		return l;
	}
	
	@Transactional
	public void delete(T entity) {
		genericRepository.delete(entity);

	}

	public List<T> find(QueryType type, String query,
			Map<String, Object> namedParams) {
		return genericRepository.find(type, query, namedParams);
	}
}
