package org.learning.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Account")
public class Account implements Serializable {

    @Column(name = "AccountNo")
    @Id
    @NotNull
    @NotBlank
    private String accountNo;

    @Column(name = "AccountName")
    private String accountName;

    @Column(name = "PinCode")
    @NotNull
    @NotBlank
    private String pinCode;

    @Column(name = "Balance")
    @NotNull
    private float balance;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "account", fetch = FetchType.EAGER)
    private List<Transaction> transactions = new ArrayList<>();

    public String getAccountNo() {
        return accountNo;
    }

    public String getPinCode() {
        return pinCode;
    }

    public String getAccountName() {
        return accountName;
    }

    public float getBalance() {
        return balance;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }
    public void setBalance(float balance) {
        this.balance = balance;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
        transaction.setAccount(this);
    }

    public void removeTransaction(Transaction transaction) {
        transactions.remove(transaction);
        transaction.setAccount(null);
    }
}