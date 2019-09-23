package com.streambuf.subtitle.parse.service;

import com.streambuf.subtitle.common.enums.ErrorCodeEnum;
import com.streambuf.subtitle.exception.ApiException;
import com.streambuf.subtitle.user.model.po.Record;
import com.streambuf.subtitle.user.repository.RecordRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class UploadService implements IUploadService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UploadService.class);
    private static final String PATH = "/file/upload/";
    private static final Integer WAIT = 2;
    private static final Integer SUCCESS = 1;
    private static final Integer LIMIT = 2;

    @Autowired
    private ParseVideoService parseVideoService;
    @Autowired
    private RecordRepo recordRepo;

    @Override
    public Long upload(MultipartFile file, String type, long userId) {
        if (!checkLimit(userId))
            throw new ApiException(ErrorCodeEnum.UPLOAD_RATE_LIMIT);
        String fileName = userId + "-" + System.currentTimeMillis() + "." + type;
        BufferedInputStream input = null;
        BufferedOutputStream output = null;
        try {
            input = new BufferedInputStream(file.getInputStream());
        } catch (IOException e) {
            LOGGER.error("upload error user id : {}, type : {}", userId, type, e);
            throw new ApiException(ErrorCodeEnum.UPLOAD_ERROR);
        }
        try {
            output = new BufferedOutputStream(new FileOutputStream(PATH + fileName));
        } catch (FileNotFoundException e) {
            LOGGER.error("upload error user id : {}, type : {}", userId, type, e);
            throw new ApiException(ErrorCodeEnum.UPLOAD_ERROR);
        }
        int by;
        byte[] ts = new byte[1024];
        try {
            while ((by = input.read(ts)) != -1) {
                output.write(ts, 0, by);
            }
            input.close();
            output.close();
        } catch (IOException e) {
            LOGGER.error("upload error user id : {}, type : {}", userId, type, e);
            throw new ApiException(ErrorCodeEnum.UPLOAD_ERROR);
        }
        Record record = recordRepo.save(new Record(userId, fileName, (int)(file.getSize() / (1024*1024)), WAIT));
        parseVideoService.addQueue(record.getId(), fileName);
        return record.getId();
    }
    private boolean checkLimit(long userId) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
        int times = recordRepo.countTodayUploadTimes(userId, dateFormat.format(new Date()));
        if (times >= LIMIT) {
            return false;
        } else {
            return true;
        }
    }
}
