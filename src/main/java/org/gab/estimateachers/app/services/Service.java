package org.gab.estimateachers.app.services;

import java.util.List;

public interface Service<T> {
    
    T findById(Long id);
    
    void save(T object);
    
    List<T> findAll();
    
    void deleteById(Long id);
}
