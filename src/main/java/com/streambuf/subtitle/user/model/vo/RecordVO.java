package com.streambuf.subtitle.user.model.vo;

import com.streambuf.subtitle.common.convert.Convert;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class RecordVO extends Convert implements Serializable {
    private Long id;
    Integer fileSize;
    Integer status;
    Date uploadTm;
    Date finishTm;
}
