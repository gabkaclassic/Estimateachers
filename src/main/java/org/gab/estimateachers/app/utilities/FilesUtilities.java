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
    
    @Value("${upload.filename.default.people}")
    private String defaultFilenamePeople;
    
    @Value("${upload.filename.default.buildings}")
    private String defaultFilenameBuildings;
    
    @Value("${upload.path}")
    private String uploadPath;
    
    @PostConstruct
    public void createDirectories() {
        
        createDirectory("classpath:".concat(uploadPath));
    }
    
    public String registrationFile(MultipartFile file, RegistrationType type) {
        
        String defaultFilename = switch (type) {
            case PEOPLE -> defaultFilenamePeople;
            case BUILDING -> defaultFilenameBuildings;
            case OTHER -> "";
        };
        
        String filename = getFilename(file, defaultFilename);
        
        try {
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
    
    private String getFilename(MultipartFile file, String defaultFilename) {
        
        if(defaultFilename.isEmpty())
            return uploadPath
                    .concat(UUID.randomUUID().toString());
        else {
            
            if(Objects.isNull(file))
                return uploadPath
                        .concat(defaultFilename);
            else
                return uploadPath
                        .concat(UUID.randomUUID().toString())
                        .concat(file.getOriginalFilename());
        }
    }
}
