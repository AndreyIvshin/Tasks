package com.epam.newsportal.service;

import com.epam.newsportal.mapper.AbstractMapper;
import com.epam.newsportal.model.entity.AbstractEntity;
import com.epam.newsportal.model.transfer.AbstractTransfer;
import com.epam.newsportal.repository.AbstractRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractService<Entity extends AbstractEntity, Transfer extends AbstractTransfer,
        Repository extends AbstractRepository<Entity>, Mapper extends AbstractMapper<Entity, Transfer>> {
    @Autowired
    protected Repository repository;
    @Autowired
    protected Mapper mapper;

    public Transfer find(Long id) {
        return mapper.map(repository.find(id));
    }

    public List<Transfer> findAll() {
        return repository.findAll().stream().map(mapper::map).collect(Collectors.toList());
    }

    public void create(Transfer transfer) {
        repository.create(mapper.map(transfer));
    }

    public Entity update(Transfer transfer) {
        return repository.update(mapper.map(transfer));
    }

    public void remove(Transfer transfer) {
        repository.remove(mapper.map(transfer));
    }
}
