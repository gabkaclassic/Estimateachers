package org.gab.estimateachers.app.services;

import org.gab.estimateachers.app.repositories.client.DormitoryRepository;
import org.gab.estimateachers.entities.client.Dormitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("dormitoryService")
public class DormitoryService {
    
    @Autowired
    @Qualifier("dormitoryRepository")
    private DormitoryRepository dormitoryRepository;
    
    public void save(Dormitory dormitory) {
        
        dormitoryRepository.save(dormitory);
    }
    
    public Dormitory findByTitle(String title) {
        
        return dormitoryRepository.findByTitle(title);
    }
}
