package com.chris.data.entity.payment;

import com.chris.data.entity.Auditable;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name="account")
public class Account extends Auditable<String, Account> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String email;
    @Column(name="account_number")
    private String accountNumber;
    @Column(name = "user_id")
    private long userId;

    @ManyToOne
    @JoinColumn(name = "payment_method_id")
    private PaymentMethod paymentMethod;

}
