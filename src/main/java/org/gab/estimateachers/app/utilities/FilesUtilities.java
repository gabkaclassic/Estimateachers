package org.gab.estimateachers.app.utilities;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Component("filesUtilities")
public class FilesUtilities {
    
    @Value("${upload.filename.default.people}")
    private String defaultFilenamePeople;
    
    @Value("${upload.filename.default.buildings}")
    private String defaultFilenameBuildings;
    
    @Value("${upload.filename.default.other}")
    private String defaultFilenameOther;
    
    @Value("${upload.path}")
    private String uploadPath;
    
    @Autowired
    @Qualifier("awsUtilities")
    private AWSUtilities awsUtilities;
    
    private String separator;
    
    @PostConstruct
    public void createDirectories() {
    
        separator = System.getProperty("file.separator");
        createDirectory("classpath:".concat(uploadPath));
    }
    
    public String registrationFile(MultipartFile file, RegistrationType type) {
        
        String defaultFilename = switch (type) {
            case PEOPLE -> defaultFilenamePeople;
            case BUILDING -> defaultFilenameBuildings;
            case OTHER -> defaultFilenameOther;
        };
        
        String filename = getFilename(file, defaultFilename);
        File file1 = new File(filename);
        try {
            if(Objects.nonNull(file))
                file.transferTo((file1 = new File(filename)));
            else {
                
                file1.createNewFile();
            }
        } catch (IOException e) {
            log.warn(String.format("Exception transfer file. Exception: %s, reason: %s, stack trace: %s",
                    e.getMessage(), e.getCause(), Arrays.toString(e.getStackTrace())));
        }
        
        awsUtilities.saveFile(file1);
        file1.deleteOnExit();
        
        return file1.getName();
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
                return uploadPath.concat(separator)
                        .concat(UUID.randomUUID().toString()).concat(Objects.requireNonNull(file.getOriginalFilename()));
        }
    }
}
