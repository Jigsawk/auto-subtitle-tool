package com.streambuf.subtitle.user.service.impl;

import com.streambuf.subtitle.user.model.po.Record;
import com.streambuf.subtitle.user.model.vo.RecordVO;
import com.streambuf.subtitle.user.repository.RecordRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecordService implements IRecordService{
    @Autowired
    private RecordRepo recordRepo;

    @Override
    public List<RecordVO> getRecordInfo(Long userId) {
        List<Record> records = recordRepo.findAllByUserId(userId);
        List<RecordVO> recordVOS = records.stream().map( record -> {
           return record.convert(RecordVO.class);
        }).collect(Collectors.toList());
        return recordVOS;
    }
}
