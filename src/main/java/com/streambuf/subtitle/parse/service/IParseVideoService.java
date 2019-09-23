package com.streambuf.subtitle.parse.service;

public interface IParseVideoService {
    Integer getRank(Long fileId, Long userId);
    byte[] downloadFile(Long fileId, Long uid);
}
