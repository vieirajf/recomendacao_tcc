package br.ce.qxa.ufc.repositorio.jpa;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

import br.ce.qxa.ufc.enumeration.QueryType;
import br.ce.qxa.ufc.repositorio.GenericRepository;

@Named
public class GenericRepositoryImpl<T> implements GenericRepository<T>{

	@PersistenceContext
	protected EntityManager em;

	@Transactional
	public void save(T entity) {
		this.em.persist(entity);
	}

	@Transactional
	public void update(T entity) {
		this.em.merge(entity);
	}

	@Transactional
	public void delete(T entity) {
		em.remove(em.merge(entity));
	}

	public T find(Class<T> entityClass, Object id) {
		T result = null;
		result = em.find(entityClass, id);
		return result;
	}

	public List<T> find(Class<T> entityClass) {
		return find(entityClass, -1, -1);

	}

	@SuppressWarnings("unchecked")
	public List<T> find(Class<T> entityClass, int firstResult, int maxResults) {
		List<T> result = null;
		Query q = em.createQuery("select obj from "
				+ entityClass.getSimpleName() + " obj");
		if (firstResult >= 0 && maxResults >= 0) {
			q = q.setFirstResult(firstResult).setMaxResults(maxResults);
		}
		result = q.getResultList();
		return result;
	}

	public List<T> find(String queryName, Map<String, Object> namedParams) {
		return find(QueryType.NAMED, queryName, namedParams);
	}

	public List<T> find(QueryType type, String query,
			Map<String, Object> namedParams) {
		return find(type, query, namedParams, -1, -1);
	}

	public List<T> find(String queryName, Map<String, Object> namedParams,
			int firstResult, int maxResults) {
		return find(QueryType.NAMED, queryName, namedParams, firstResult,
				maxResults);
	}

	@SuppressWarnings("unchecked")
	public List<T> find(QueryType type, String query,
			Map<String, Object> namedParams, int firstResult, int maxResults) {
		List<T> result = null;
		Query q;
		if (type == QueryType.JPQL) {
			q = em.createQuery(query);
		} else if (type == QueryType.NATIVE) {
			q = em.createNativeQuery(query);
		} else if (type == QueryType.NAMED) {
			q = em.createNamedQuery(query);
		} else {
			throw new IllegalArgumentException("Tipo de Query inválido: "
					+ type);
		}

		if (namedParams != null) {
			Set<String> keys = namedParams.keySet();
			for (String key : keys) {
				q.setParameter(key, namedParams.get(key));
			}
		}

		if (firstResult >= 0 && maxResults >= 0) {
			q = q.setFirstResult(firstResult).setMaxResults(maxResults);
		}

		result = q.getResultList();

		return result;
	}

	public T findFirst(String query, Map<String, Object> namedParams) {
		return findFirst(query, namedParams, -1, -1);
	}
	

	public T findFirst(String query, Map<String, Object> namedParams,
			int firstResult, int maxResults) {
		return findFirst(QueryType.NAMED, query, namedParams, firstResult,
				maxResults);
	}

	public T findFirst(QueryType type, String query,
			Map<String, Object> namedParams, int firstResult, int maxResults) {

		List<T> result = find(type, query, namedParams, firstResult, maxResults);
		return result == null || result.size() == 0 ? null : result.get(0);
	}

}
