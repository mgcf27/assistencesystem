package com.miguel.assistencesystem.infrastructure.persistence;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;




/**
 * Base DAO with generic CRUDs
 * @param <T> Entity type
 * @param <ID> Id type
 */

@Repository
public abstract class BaseDAO<T,ID>{
	
	@PersistenceContext
    protected EntityManager em;
	
	private final Class<T> entityClass;
	
	protected BaseDAO(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

    public void persist(T entity) {
        em.persist(entity);
    }

    public T merge(T entity) {
        return em.merge(entity);
    }

    public void remove(T entity) {
        T managed = em.merge(entity);
        em.remove(managed);
    }

    public Optional<T> findById(ID id) {
        return Optional.ofNullable(em.find(entityClass, id));
    }

    public List<T> findAll() {
        return em.createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e", entityClass)
                .getResultList();
    }

    public long count() {
        return em.createQuery("SELECT COUNT(e) FROM " + entityClass.getSimpleName() + " e", Long.class)
                .getSingleResult();
    }

	public void removeById(ID id) {
	        T entity = em.find(entityClass, id);
	        if (entity != null) {
	            em.remove(entity);
	        }
	    }




    
    /**
     * register counter
     */

}