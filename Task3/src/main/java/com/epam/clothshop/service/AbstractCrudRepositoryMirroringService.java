package com.epam.clothshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public abstract class AbstractCrudRepositoryMirroringService<T, ID, R extends CrudRepository<T, ID>> {

    @Autowired
    protected R r;

    public <S extends T> S save(S var1) {
        return r.save(var1);
    }

    public <S extends T> Iterable<S> saveAll(Iterable<S> var1) {
        return r.saveAll(var1);
    }

    public Optional<T> findById(ID var1) {
        return r.findById(var1);
    }

    public boolean existsById(ID var1) {
        return r.existsById(var1);
    }

    public Iterable<T> findAll() {
        return r.findAll();
    }

    public Iterable<T> findAllById(Iterable<ID> var1) {
        return r.findAllById(var1);
    }

    public long count() {
        return r.count();
    }

    public void deleteById(ID var1) {
        r.deleteById(var1);
    }

    public void delete(T var1) {
        r.delete(var1);
    }

    public void deleteAll(Iterable<? extends T> var1) {
        r.deleteAll(var1);
    }

    public void deleteAll() {
        r.deleteAll();
    }
}
