package com.epam.newsportal.repository;

import com.epam.newsportal.model.AbstractEntity;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;

public abstract class AbstractRepository<Entity extends AbstractEntity, Id> {

    @PersistenceContext(unitName = "default")
    protected EntityManager entityManager;
    protected Class<Entity> entityClass;

    @PostConstruct
    protected void init() {
        ParameterizedType genericSuperclass = (ParameterizedType) this.getClass().getGenericSuperclass();
        entityClass = (Class<Entity>) genericSuperclass.getActualTypeArguments()[0];
    }

    public List<Entity> findAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Entity> criteriaQuery = criteriaBuilder.createQuery(entityClass);
        Root<Entity> root = criteriaQuery.from(entityClass);
        criteriaQuery.select(root);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    public Optional<Entity> find(Id id) {
        return Optional.ofNullable(entityManager.find(entityClass, id));
    }

    public Entity create(Entity entity) {
        entityManager.persist(entity);
        return entity;
    }

    public Entity update(Entity entity) {
        return entityManager.merge(entity);
    }

    public void delete(Id id) {
        entityManager.remove(find(id).get());
    }
}
