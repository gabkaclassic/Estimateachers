package org.gab.estimateachers.app.services;

import org.gab.estimateachers.app.repositories.client.DormitoryRepository;
import org.gab.estimateachers.app.utilities.FilesUtilities;
import org.gab.estimateachers.app.utilities.RegistrationType;
import org.gab.estimateachers.entities.client.Card;
import org.gab.estimateachers.entities.client.Dormitory;
import org.gab.estimateachers.entities.client.University;
import org.gab.estimateachers.entities.system.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service("dormitoryService")
public class DormitoryService implements CardService<Dormitory>  {
    
    @Autowired
    @Qualifier("dormitoryRepository")
    private DormitoryRepository dormitoryRepository;
    
    @Autowired
    @Qualifier("filesUtilities")
    private FilesUtilities filesUtilities;
    
    public Dormitory findById(Long id) {
        
        return dormitoryRepository.getOne(id);
    }
    
    public void save(Dormitory dormitory) {
        
        dormitoryRepository.save(dormitory);
    }
    
    public Dormitory create(String dormitoryTitle, University university, Set<MultipartFile> files, boolean approved) {
        
        Dormitory dormitory = new Dormitory(dormitoryTitle, university);
        if(Objects.isNull(files) || files.isEmpty())
            dormitory.addPhoto(filesUtilities.registrationFile(null, RegistrationType.BUILDING));
        else
            files.stream().map(f -> filesUtilities.registrationFile(f, RegistrationType.BUILDING)).forEach(dormitory::addPhoto);
        dormitory.setApproved(approved);
        save(dormitory);
        return dormitory;
    }
    
    public void edit(Long id, String title, Set<MultipartFile> files) {
        
        Dormitory card = dormitoryRepository.getOne(id);
        card.setTitle(title);
        files.stream()
                .map(f -> filesUtilities.registrationFile(f, RegistrationType.OTHER))
                .forEach(card::addPhoto);
    
        dormitoryRepository.save(card);
    }
    
    public List<Dormitory> findByTitlePattern(String pattern) {
        
        return dormitoryRepository.findByTitlePattern(pattern);
    }
    
    public List<Dormitory> findAllApproved() {
        
        return dormitoryRepository.findAllApproved();
    }
    
    public List<Dormitory> findAll() {
        
        return dormitoryRepository.findAll();
    }
    
    public void deleteById(Long id) {
        
        dormitoryRepository.deleteById(id);
    }
    
    public Dormitory findByTitle(String title) {
        
        return dormitoryRepository.findByTitle(title);
    }
    
    public boolean existsByTitle(String title) {
        
        return dormitoryRepository.existsByTitle(title);
    }
}
