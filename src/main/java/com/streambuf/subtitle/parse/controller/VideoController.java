package com.streambuf.subtitle.parse.controller;

import com.streambuf.subtitle.common.controller.SuperController;
import com.streambuf.subtitle.common.enums.ErrorCodeEnum;
import com.streambuf.subtitle.common.responses.ApiResponses;
import com.streambuf.subtitle.exception.ApiException;
import com.streambuf.subtitle.parse.service.ParseVideoService;
import com.streambuf.subtitle.parse.service.UploadService;
import com.streambuf.subtitle.user.model.vo.UserLoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(value = "/api/video")
@Validated
public class VideoController extends SuperController {
    @Autowired
    private UploadService uploadService;
    @Autowired
    private ParseVideoService parseVideoService;

    private  final List<String> allowedTypes = new ArrayList<String>(Arrays.asList("flv", "mp4", "avi", "mov", "asf", "wmv", "rmvb", "rm", "swf"));

    @PostMapping("/upload")
    public ApiResponses<Long> upload(@RequestParam("file") MultipartFile file, @RequestHeader String type) throws IOException {
        if (allowedTypes.contains(type)) {
            Long id = uploadService.upload(file, type, currentUid());
            if (id != null) {
                return success(HttpStatus.OK, id);
            }else {
                throw new ApiException(ErrorCodeEnum.UPLOAD_ERROR);
            }
        }else {
           throw new ApiException(ErrorCodeEnum.NOT_SUPPORT_CONTENT_TYPE);
        }
    }

    @PostMapping("/rank")
    public ApiResponses<Integer> rank(@RequestParam Long fileId) {
        Integer rank = parseVideoService.getRank(fileId, currentUid());
        if (rank == null) {
            throw new ApiException(ErrorCodeEnum.FILE_NOT_FOUND);
        }else{
            return success(HttpStatus.OK, rank);
        }
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<?> download(@PathVariable String fileId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(String.valueOf(MediaType.APPLICATION_OCTET_STREAM)));
        byte[] bytes = parseVideoService.downloadFile(Long.valueOf(fileId), currentUid());
        return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
    }

    private boolean checkFileType(MultipartFile file) throws IOException {
        byte[] content = file.getBytes();
        InputStream is = new BufferedInputStream(new ByteArrayInputStream(content));
        String mimeType = URLConnection.guessContentTypeFromStream(is);
        if(allowedTypes.contains(mimeType))
            return true;
        else
            return false;
    }
}
