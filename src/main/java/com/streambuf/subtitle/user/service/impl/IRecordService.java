package com.streambuf.subtitle.user.service.impl;

import com.streambuf.subtitle.user.model.vo.RecordVO;

import java.util.List;

public interface IRecordService {
    List<RecordVO> getRecordInfo(Long userId);
}
