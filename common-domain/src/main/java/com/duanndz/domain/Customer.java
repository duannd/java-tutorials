package com.duanndz.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

/**
 * duan.nguyen
 * Datetime 5/7/20 16:35
 */
@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
@ToString(of = {"id", "name", "email", "phoneNumber"})
@Entity
@Table(name = "customer")
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private String email;
    @Column(name = "phone_number")
    private String phoneNumber;

}
