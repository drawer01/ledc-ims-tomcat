package com.ledc.ims.Entity;

import lombok.Data;
import javax.persistence.*;

/**
 * @author Sellin.chu
 * @version 1.0.0
 *
 * <p>用户实体类</p>
 */
@Entity
@Data
@Table(name="user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role;

    // getter and setter...


}