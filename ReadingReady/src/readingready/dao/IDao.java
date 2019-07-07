/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package readingready.dao;

import java.util.List;

/**
 *
 * @author Hannah Saliot
 */
public interface IDao<T> {
    void create(T t);
    void update(T t);
    void delete(Class<T> entityType, int id);
    T find(Class<T> entityType, int id);
    List<T> findAll(Class<T> entityType);
}
