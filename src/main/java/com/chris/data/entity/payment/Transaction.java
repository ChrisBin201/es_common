package com.chris.data.entity.payment;

import com.chris.data.entity.Auditable;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@Entity
@Table(name="transaction")
@ToString
public class Transaction extends Auditable<String, Transaction> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private double amount;
    private String currency;
    @Column(name="payment_fee")
    private double paymentFee;
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    @ManyToOne
    @JoinColumn(name="payment_method_id")
    private PaymentMethod paymentMethod;

//    @Column(name="from_user_id")
//    private long fromUserId;
//
//    @Column(name="to_user_id")
//    private long toUserId;

    @ManyToOne
    @JoinColumn(name="from_account_id")
    private Account fromAccount;

    @ManyToOne
    @JoinColumn(name="to_account_id")
    private Account toAccount;


    public enum TransactionStatus{
        PENDING,
        SUCCESS,
        FAILED
    }
}
