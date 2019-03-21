package com.ledc.ims.Entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

// This tells Hibernate to make a table out of this class
@Entity
@Data
@Table(name="t_User")
public class User {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    private String username;
    private int age;
    private Date ctm;

}