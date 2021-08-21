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
    
    @Value("${upload.path.student}")
    private String studentUploadPath;
    @Value("${upload.path.university}")
    private String universityUploadPath;
    @Value("${upload.path.dormitory}")
    private String dormitoryUploadPath;
    @Value("${upload.path.teacher}")
    private String teacherUploadPath;
    @Value("${upload.path.default}")
    private String defaultUploadPath;
    
    @PostConstruct
    public void method() {
    
        createDirectory(defaultUploadPath);
        createDirectory(studentUploadPath);
        createDirectory(dormitoryUploadPath);
        createDirectory(universityUploadPath);
        createDirectory(teacherUploadPath);
    }
    
    public String studentRegistrationFile(MultipartFile file) {
        
        String filename = getFilename(file, studentUploadPath);
    
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
        
        String result = (fileIsNull ? defaultUploadPath : uploadPath)
                .concat(UUID.randomUUID().toString());
        
        if(fileIsNull)
            return result;
        
        return result.concat(file.getOriginalFilename());
    }
}
