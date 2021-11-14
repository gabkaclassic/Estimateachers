package org.gab.estimateachers.app.services;

import org.gab.estimateachers.app.repositories.client.DormitoryRepository;
import org.gab.estimateachers.app.utilities.FilesUtilities;
import org.gab.estimateachers.app.utilities.RegistrationType;
import org.gab.estimateachers.entities.client.Dormitory;
import org.gab.estimateachers.entities.system.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("dormitoryService")
public class DormitoryService implements org.gab.estimateachers.app.services.Service<Dormitory>  {
    
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
    
    public void create(Dormitory dormitory) {
        
        dormitory.addPhoto(filesUtilities.registrationFile(null, RegistrationType.BUILDING));
        
        save(dormitory);
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
}
