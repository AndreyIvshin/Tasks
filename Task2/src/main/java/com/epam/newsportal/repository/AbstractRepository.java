package com.epam.newsportal.repository;

import com.epam.newsportal.model.entity.AbstractEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public abstract class AbstractRepository<Entity extends AbstractEntity> {

    @PersistenceContext
    protected EntityManager entityManager;

    protected Class<Entity> entityClass;

    public AbstractRepository() {
        this.entityClass = getEntityClass();
    }

    protected abstract Class<Entity> getEntityClass();

    public Entity find(Long id) {
        return entityManager.find(entityClass, id);
    }

    public List<Entity> findAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Entity> criteriaQuery = criteriaBuilder.createQuery(entityClass);
        Root<Entity> root = criteriaQuery.from(entityClass);
        criteriaQuery.select(root);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    public void create(Entity entity) {
        entityManager.persist(entity);
    }

    public Entity update(Entity entity) {
        return entityManager.merge(entity);
    }

    public void remove(Entity entity) {
        entityManager.remove(entity);
    }
}
