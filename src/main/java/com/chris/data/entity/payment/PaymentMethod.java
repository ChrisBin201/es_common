package com.chris.data.entity.payment;

import com.chris.data.entity.Auditable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name="payment_method")
public class PaymentMethod extends Auditable<String, PaymentMethod> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String description;

    @Min(0)
    @Max(1)
    private double fee;

    @Enumerated(EnumType.STRING)
    private PaymentType type;

    public enum  PaymentType{
        PAYPAL
    }
}
