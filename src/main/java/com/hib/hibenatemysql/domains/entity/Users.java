package com.hib.hibenatemysql.domains.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@Table(name = "users")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Users implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(length = 20)
    private String userId;
    @Column(length = 20)
    private String firstName;
    @Column(length = 20)
    private String middleName;
    @Column(length = 20)
    private String lastName;
    @Column(length = 10)
    private String role;
    @Column(length = 100)
    private String password;
    private String token;


}
