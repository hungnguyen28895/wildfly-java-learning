package org.learning.services;

import org.learning.entity.Account;
import org.learning.entity.Transaction;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Stateless(name = "EmployeeBeanEJB")
@LocalBean

public class BankBean implements BankService {

    private final EntityManager entityManager;

    public BankBean() {
        EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("java-learningPersistenceUnit");
        entityManager = managerFactory.createEntityManager();
    }

    @Override
    public Account checkLogin(String accountNo, String pinCode){
        var account = getAccount(accountNo);
        if (account != null && account.getPinCode().equals(pinCode)) {
            return account;
        }

        return null;
    }

    @Override
    public boolean transfer(String accountNoFrom, String accountNoTo, float amount, String comment) {
        var transferAccount = getAccount(accountNoFrom);
        var receiveAccount = getAccount(accountNoTo);

        transferAccount.setBalance(transferAccount.getBalance() - amount);
        receiveAccount.setBalance(receiveAccount.getBalance() + amount);

        var transferTransaction = new Transaction();
        transferTransaction.setAmount(amount);
        transferTransaction.setTranDate(new Date());
        transferTransaction.setComment(comment);
        transferTransaction.setAccountNo(accountNoTo);

        transferAccount.addTransaction(transferTransaction);

        try {
            entityManager.getTransaction().begin();
            entityManager.merge(transferAccount);
            entityManager.merge(receiveAccount);
            entityManager.getTransaction().commit();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
            return false;
        }
    }

    private List<Account> getAllEntity() {
        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Account> cq = builder.createQuery(Account.class);
        Root<Account> root = cq.from(Account.class);
        cq.select(root);
        return entityManager.createQuery(cq).getResultList();
    }

    @Override
    public Account getAccount(String accountNo) {

        return this.entityManager.find(Account.class, accountNo);
    }

//    private boolean addEntity(T entity) {
//        try {
//            entityManager.getTransaction().begin();
//            entityManager.persist(entity);
//            entityManager.getTransaction().commit();
//            return true;
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            entityManager.getTransaction().rollback();
//            return false;
//        }
//    }
//
//    private boolean updateEntity(T entity) {
//
//        try {
//            entityManager.getTransaction().begin();
//            entityManager.merge(entity);
//            entityManager.getTransaction().commit();
//            return true;
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            entityManager.getTransaction().rollback();
//            return false;
//        }
//    }
//
//    private boolean deleteEntity(long id) {
//        try {
//            entityManager.getTransaction().begin();
//            entityManager.remove(getEntityById(id));
//            entityManager.getTransaction().commit();
//            return true;
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            entityManager.getTransaction().rollback();
//            return false;
//        }
//    }

//    private List<T> getListEntityByQuery(String queryString, Map<String, Object> parameters) {
//        TypedQuery<T> query = this.entityManager.createQuery(queryString, this.type);
//        for (Map.Entry<String, Object> entry : parameters.entrySet()) {
//            query.setParameter(entry.getKey(), entry.getValue());
//        }
//        return query.getResultList();
//    }
}
