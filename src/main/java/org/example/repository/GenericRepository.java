package org.example.repository;

import java.util.List;

public interface GenericRepository<T, ID> {
    T create(T t);

    T get(ID id);

    List<T> getAll();

    T update(T t);

    void delete(ID id);

}
