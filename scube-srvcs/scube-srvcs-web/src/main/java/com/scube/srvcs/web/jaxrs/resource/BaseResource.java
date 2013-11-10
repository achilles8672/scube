/**
 * @author Copyright (c) 2010 by Oracle. All Rights Reserved
 *  
 */
package com.scube.srvcs.web.jaxrs.resource;

import java.util.List;

import javax.persistence.EntityManager;

public abstract class BaseResource<T> {
  private Class<T> entityClass;

  public BaseResource(Class<T> entityClass) {
    this.entityClass = entityClass;
  }

  protected abstract EntityManager getEntityManager();

  public void create(T entity, String id) {
    if (getEntityManager().find(entityClass, id) == null) {
      getEntityManager().persist(entity);
    } else {
      throw new RuntimeException(entityClass + " with id[" + id + "] exists!");
    }
  }

  public T update(T entity, String id) {
    if (getEntityManager().find(entityClass, id) == null) {
      throw new RuntimeException(entityClass + " with id[" + id + "] not found!");
    } else {
      entity = getEntityManager().merge(entity);
    }
    return entity;
  }

  public List<T> delete(String id) {
    T entity = getEntityManager().find(entityClass, id);
    if (entity == null) {
      throw new RuntimeException(entityClass + " with id[" + id + "] not found!");
    } else {
      getEntityManager().remove(entity);
    }
    return findAll();
  }

  public List<T> viewAll() {
    return findAll();
  }

  @SuppressWarnings("unchecked")
  private List<T> findAll() {
    String className = entityClass.getName();
    String queryClass = className.substring(className.lastIndexOf(".") + 1);

    return getEntityManager().createQuery("SELECT i FROM " + queryClass + " i")
        .getResultList();
  }
}
