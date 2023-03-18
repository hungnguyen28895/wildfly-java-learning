package org.learning.services;

import javax.ejb.Remote;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Remote
public interface EntityService<T extends Serializable>  {
    public List<T> getAllEntity();

    public T getEntityById(long id);

    public boolean addEntity(T entity);

    public boolean updateEntity(T entity);

    public boolean deleteEntity(long id);

    public void setType(Class<T> t);

    public List<T> getListEntityByQuery(String queryString, Map<String, Object> parameters);
}
