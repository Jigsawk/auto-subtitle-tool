package com.streambuf.subtitle.user.model.po;

import com.streambuf.subtitle.common.convert.Convert;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name="record")
@NoArgsConstructor
public class Record extends Convert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    Long userId;
    String fileName;
    Integer fileSize;
    Integer status;
    Date uploadTm;
    Date finishTm;

    public Record(Long userId, String fileName, Integer fileSize, Integer status) {
        this.userId = userId;
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.status = status;
        this.uploadTm = new Date();
    }
}
