package org.gab.estimateachers.app.utilities;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

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
            if(Objects.nonNull(file))
                file.transferTo(new File(filename));
            else {
                File file1 = new File(filename);
                file1.createNewFile();
            }
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
                        .concat(Objects.requireNonNull(file.getOriginalFilename()));
        }
    }
}
