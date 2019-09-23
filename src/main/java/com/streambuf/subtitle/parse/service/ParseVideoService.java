package com.streambuf.subtitle.parse.service;

import com.streambuf.subtitle.common.enums.ErrorCodeEnum;
import com.streambuf.subtitle.exception.ApiException;
import com.streambuf.subtitle.user.model.po.Record;
import com.streambuf.subtitle.user.repository.RecordRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.Date;
import java.util.Iterator;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

@Service
public class ParseVideoService implements IParseVideoService {
    private static final String PATH = "/file/upload/";
    private static final Logger LOGGER = LoggerFactory.getLogger(ParseVideoService.class);
    private static final Integer WAIT = 2;
    private static final Integer SUCCESS = 1;
    @Autowired
    private RecordRepo recordRepo;

    private static LinkedBlockingQueue<String> video2handleQueue = new LinkedBlockingQueue<>();

    private static ConcurrentHashMap<Long, String> idNameMap = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<String, Long> nameIdMap = new ConcurrentHashMap<>();

    private static ThreadPoolTaskExecutor threadPoolTaskExecutor;

    static {
        threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(2);
        threadPoolTaskExecutor.setMaxPoolSize(4);
        threadPoolTaskExecutor.setQueueCapacity(100);
        threadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        threadPoolTaskExecutor.initialize();
    }

    public synchronized void addQueue(Long id, String fileName) {
        video2handleQueue.offer(fileName);
        idNameMap.put(id, fileName);
        nameIdMap.put(fileName, id);
    }

    @PostConstruct
    public void keepHandling() {
        while (threadPoolTaskExecutor.getActiveCount() < 2) {
            LOGGER.info("threadPoolTaskExecutor.ActiveCount：", threadPoolTaskExecutor.getActiveCount());
            threadPoolTaskExecutor.submit(new ParseVideoTask());
        }
    }

    @Override
    public Integer getRank(Long fileId, Long userId) {
        Optional<Record> optional = recordRepo.findByIdAndUserId(fileId, userId);
        if (!optional.isPresent()) {
            throw new ApiException(ErrorCodeEnum.UNAUTHORIZED);
        }else {
            String fileName = idNameMap.get(fileId);
            if (fileName == null){
                LOGGER.error("find file is null, file id :{}", fileId);
                throw new ApiException(ErrorCodeEnum.FILE_NOT_FOUND);
            }
            Iterator<String> iterator = video2handleQueue.iterator();
            int count = 0;
            while (iterator.hasNext()){
                count++;
                if (fileName.equals(iterator.next())){
                    return count;
                }
            }
            return null;
        }
    }

    @Override
    public byte[] downloadFile(Long fileId, Long uid) {
        Optional<Record> optional = recordRepo.findById(fileId);
        if(!optional.isPresent())
            throw new ApiException(ErrorCodeEnum.FILE_NOT_FOUND);
        System.out.println(optional.get().getFileName());
        String subtitleFileName = optional.get().getFileName().split("\\.")[0].concat(".srt");
        File file = new File(PATH.concat(subtitleFileName));
        BufferedInputStream bufin = null;
        try {
            bufin = new BufferedInputStream(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            LOGGER.error("文件未找到 : {}", optional.get().getFileName() ,e);
            throw new ApiException(ErrorCodeEnum.FILE_NOT_FOUND);
        }
        int buffSize = 1024;
        ByteArrayOutputStream out = new ByteArrayOutputStream(buffSize);
        byte[] temp = new byte[buffSize];
        int size = 0;
        try {
            while ((size = bufin.read(temp)) != -1) {
                out.write(temp, 0, size);
            }
            bufin.close();
        } catch (IOException e) {
            LOGGER.error("读取文件失败, {}", optional.get().getFileName() ,e);
        }
        return out.toByteArray();
    }

    class ParseVideoTask implements Runnable {
        @Override
        public void run() {
            try {
                String fileName = video2handleQueue.take();
                LOGGER.info("queue length : {}", video2handleQueue.size());
                String command = "autosub -S zh-CN -D zh-CN ".concat(PATH).concat(fileName);
                LOGGER.info("shell command : {}", command);
                Process process = Runtime.getRuntime().exec(command);
                long timestamp = System.currentTimeMillis();
                int status = process.waitFor();
                if(status != 0){
                    LOGGER.error("fileName : {} , Failed to call shell's command and the return status's is: {}", fileName, status);
                } else {
                    LOGGER.info("parse {} fileName successfully, time consuming(s): {}", fileName, (System.currentTimeMillis() - timestamp) / 60);
                    Optional<Record> optional = recordRepo.findById(nameIdMap.get(fileName));
                    if (optional.isPresent()){
                        Record record = optional.get();
                        record.setStatus(SUCCESS);
                        record.setFinishTm(new Date());
                        recordRepo.save(record);
                    }
                    Runtime.getRuntime().exec("rm ".concat(PATH).concat(fileName).concat(" -f"));
                    LOGGER.info("delete video : {}  successfully", fileName);
                }
            } catch (Exception e) {
                LOGGER.error("threadId : {} parse error", Thread.currentThread().getId(), e);
            } finally {
                keepHandling();
            }
        }
    }
}
