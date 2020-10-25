package com.epam.newsportal.domain.repository;

import com.epam.newsportal.domain.entity.AbstractEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public abstract class AbstractRepository<Entity extends AbstractEntity> {

    @PersistenceContext
    protected EntityManager entityManager;

    protected abstract Class<Entity> getEntityClass();

    public Entity find(String id) {
        return entityManager.find(getEntityClass(), id);
    }

    public List<Entity> findAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Entity> criteriaQuery = criteriaBuilder.createQuery(getEntityClass());
        Root<Entity> from = criteriaQuery.from(getEntityClass());
        criteriaQuery.select(from);
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
