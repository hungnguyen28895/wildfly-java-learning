package org.learning.services;

import org.learning.entity.Account;

import javax.ejb.Remote;
import java.io.Serializable;

@Remote
public interface BankService<T extends Serializable>  {
//    public List<T> getAllEntity();
//
//    public T getEntityById(long id);
//
//    public boolean addEntity(T entity);
//
//    public boolean updateEntity(T entity);
//
//    public boolean deleteEntity(long id);
//
//    public void setType(Class<T> t);
//
//    public List<T> getListEntityByQuery(String queryString, Map<String, Object> parameters);

    Account checkLogin(String accountNo, String pinCode);

    boolean transfer(String accountNoFrom, String accountNoTo, float amount, String comment);

    Account getAccount(String accountNo);
}
