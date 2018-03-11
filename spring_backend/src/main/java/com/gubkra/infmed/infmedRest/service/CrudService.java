package com.gubkra.infmed.infmedRest.service;

/**
 * Created by Olaf on 2018-03-11.
 */
public interface CrudService<T> {

    T addItem(T item);
    void removeItem(T item);

    Iterable<T> findAll();
}
