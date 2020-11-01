package com.epam.newsportal.service;

import com.epam.newsportal.model.entity.AbstractEntity;
import com.epam.newsportal.repository.AbstractRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public abstract class AbstractService<Entity extends AbstractEntity, Repository extends AbstractRepository<Entity>> {

    @Autowired
    protected Repository repository;

    public Entity find(Long id) {
        return repository.find(id);
    }

    public List<Entity> findAll() {
        return repository.findAll();
    }

    public void create(Entity entity) {
        repository.create(entity);
    }

    public Entity update(Entity entity) {
        return repository.update(entity);
    }

    public void remove(Entity entity) {
        repository.remove(entity);
    }
}
