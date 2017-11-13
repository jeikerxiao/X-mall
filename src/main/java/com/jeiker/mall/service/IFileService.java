package com.jeiker.mall.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by geely
 */
public interface IFileService {

    /**
     * 上传文件
     *
     * @param file
     * @param path
     * @return
     */
    String upload(MultipartFile file, String path);
}
