package com.streambuf.subtitle.user.model.po;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="user")
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String nickname;
    private String phone;
    private String email;
    private Integer status;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTm;

    public User(String username, String password, String nickname, String phone, String email, Integer status) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.phone = phone;
        this.email = email;
        this.status = status;
        this.createTm = new Date();
    }
}
