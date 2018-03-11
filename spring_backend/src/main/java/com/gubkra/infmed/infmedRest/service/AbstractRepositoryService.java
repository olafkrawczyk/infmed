package com.gubkra.infmed.infmedRest.service;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by Olaf on 2018-03-11.
 */

public abstract class AbstractRepositoryService<T, ID> implements CrudService<T>{

    protected CrudRepository<T, ID> repository;

    public T addItem(T item) {
        return this.repository.save(item);
    }

    public void removeItem(T item) {
        this.repository.delete(item);
    }

    public Iterable<T> findAll() {
        return this.repository.findAll();
    }
}
