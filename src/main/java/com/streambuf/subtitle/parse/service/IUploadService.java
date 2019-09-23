package com.streambuf.subtitle.parse.service;

import org.springframework.web.multipart.MultipartFile;

public interface IUploadService {
    Long upload(MultipartFile file, String type, long userId);
}
