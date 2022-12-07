package com.dmj.Netview.utils;

import java.io.File;
import java.io.FileOutputStream;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;


@Component
public class DataBucketUtil {

    public File convertFile(MultipartFile file) {

        try {
            if (file.getOriginalFilename() == null) {
                System.out.println("Original file name is null");
            }
            File convertedFile = new File(file.getOriginalFilename());
            FileOutputStream outputStream = new FileOutputStream(convertedFile);
            outputStream.write(file.getBytes());
            outputStream.close();
            return convertedFile;
        } catch (Exception e) {
            System.out.println("An error has occurred while converting the file");
        }
        return null;
    }

}
