package com.streambuf.subtitle.user.model.po;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name="invitation_code")
@NoArgsConstructor
public class InvitationCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;
    private Integer status;
    private Long userId;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTm;

    public InvitationCode(String code, Integer status) {
        this.code = code;
        this.status = status;
        this.createTm = new Date();
    }
}
