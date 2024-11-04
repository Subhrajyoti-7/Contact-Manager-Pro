package com.scm.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    public String uploadImage(MultipartFile uploadFile, String filename);

    public String getUrlFromPublicId(String publicId);

}
