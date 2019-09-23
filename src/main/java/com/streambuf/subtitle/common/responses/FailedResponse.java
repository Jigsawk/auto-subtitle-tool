package com.streambuf.subtitle.common.responses;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@ToString
@Builder
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class FailedResponse extends ApiResponses {

    private static final long serialVersionUID = 1L;
    /**
     * http 状态码
     */
    private Integer status;
    /**
     * 错误描述
     */
    private String msg;
    /**
     * 异常信息
     */
    private String exception;
    /**
     * 当前时间戳
     */
    private LocalDateTime time;

}
