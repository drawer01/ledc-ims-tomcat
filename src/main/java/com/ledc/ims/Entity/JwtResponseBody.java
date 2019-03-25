package com.ledc.ims.Entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
@Entity
@Data
@Table(name="jwtResponseBody")
public class JwtResponseBody implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "status")
    private String status;
    @Column(name = "msg")
    private String msg;
    @Column(name = "result")
    private Integer result;
    @Column(name = "jwtToken")
    private String jwtToken;
}
