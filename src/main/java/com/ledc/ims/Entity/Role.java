package com.ledc.ims.Entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name="1_role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;

    // getter and setter...
}
