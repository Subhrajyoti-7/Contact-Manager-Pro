package com.scm.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.scm.helper.AppConstants;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private Cloudinary cloudinary;

    @Override
    public String uploadImage(MultipartFile uploadFile, String fileName) {
        // Implement image upload logic here
        byte[] data;
        try {
            data = new byte[uploadFile.getInputStream().available()];
            uploadFile.getInputStream().read(data);
            cloudinary.uploader().upload(data, ObjectUtils.asMap("public_id", fileName));
            return getUrlFromPublicId(fileName);
        } catch (IOException ex) {
            return null;
        }
    }

    @Override
    public String getUrlFromPublicId(String publicId) {
        return cloudinary
                .url()
                .transformation(
                        new Transformation<>()
                                .width(AppConstants.CONTACT_IMAGE_WIDTH)
                                .height(AppConstants.CONTACT_IMAGE_HEIGHT)
                                .crop("fill")
                                .gravity("face"))
                .generate(publicId);
    }

}
