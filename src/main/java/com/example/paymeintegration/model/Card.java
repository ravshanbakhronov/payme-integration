package com.example.paymeintegration.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "CARD")
public class Card implements Serializable {
    @Id
    @Column(name = "ID", insertable = true)
    private String number;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User user;

    @Column(name = "CURRENCY_CODE")
    private String currencyCode;

    @Column(name = "CARD_NAME")
    private String cardName;

    @Column(name = "EXPIRE_DATE")
    private String expireDate;

    @Column(name = "TOKEN",length = 1024)
    private String token;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "RECURRENT")
    private Boolean recurrent;

    @Column(name = "VERIFY")
    private Boolean verify;



}
