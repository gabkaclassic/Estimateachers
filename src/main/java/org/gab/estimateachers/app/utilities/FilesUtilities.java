package org.gab.estimateachers.app.utilities;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Component("filesUtilities")
public class FilesUtilities {
    
    @Value("${upload.filename.default}")
    private String defaultFilename;
    
    @Value("${upload.path.user}")
    private String userUploadPath;
    @Value("${upload.path.application}")
    private String applicationUploadPath;
    @Value("${upload.path.message}")
    private String messageUploadPath;
    @Value("${upload.path.university}")
    private String universityUploadPath;
    @Value("${upload.path.dormitory}")
    private String dormitoryUploadPath;
    @Value("${upload.path.teacher}")
    private String teacherUploadPath;
    
    @PostConstruct
    public void method() {
        
        createDirectory(userUploadPath);
        createDirectory(dormitoryUploadPath);
        createDirectory(universityUploadPath);
        createDirectory(teacherUploadPath);
        createDirectory(applicationUploadPath);
        createDirectory(messageUploadPath);
    }
    
    public String registrationFile(MultipartFile file, RegistrationType type) {
        
        String uploadPath = switch (type) {
            case USER -> userUploadPath;
            case MESSAGE -> messageUploadPath;
            case APPLICATION -> applicationUploadPath;
        };
        
        String filename = getFilename(file, uploadPath);
        
        try {  //TO DO logging
            file.transferTo(new File(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        return filename;
    }
    
    private void createDirectory(String uploadPath) {
        
        File directory = new File(uploadPath);
        
        if(!directory.exists())
            directory.mkdir();
    }
    
    private String getFilename(MultipartFile file, String uploadPath) {
        
        boolean fileIsNull = Objects.isNull(file);
        
        String result = uploadPath
                .concat(fileIsNull && !uploadPath.equals(applicationUploadPath) && !uploadPath.equals(messageUploadPath) ?
                        defaultFilename : UUID.randomUUID().toString());
        
        if(fileIsNull)
            return result;
        
        return result.concat(file.getOriginalFilename());
    }
}
