package com.example.darts.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class CloudinaryService {
    private final Cloudinary cloudinary;

    public String uploadImage(MultipartFile multipartFile) {
        try {
            File file = File
                    .createTempFile("temp-file", multipartFile.getOriginalFilename());
            multipartFile.transferTo(file);

            return cloudinary.uploader()
                    .upload(file,
                            ObjectUtils.emptyMap()).get("url").toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
