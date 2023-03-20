package org.learning.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "Transactions")
public class Transaction implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "TranDate")
    private Date tranDate;

    @Column(name = "Amount")
    @NotNull
    private float amount;

    @Column(name = "Comment")
    @NotNull
    @NotBlank
    private String comment;

    @Column(name = "AccountNo")
    @NotNull
    @NotBlank
    private String accountNo;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTranDate(Date tranDate) {
        this.tranDate = tranDate;
    }
    public void setAmount(float amount) {
        this.amount = amount;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }


    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transaction)) return false;
        return id != null && id.equals(((Transaction) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
