package org.gab.estimateachers.app.services;

import org.gab.estimateachers.app.repositories.system.DiscussionRepository;
import org.gab.estimateachers.entities.system.discussions.Discussion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("discussionService")
public class DiscussionService implements org.gab.estimateachers.app.services.Service<Discussion> {
    
    @Autowired
    @Qualifier("discussionRepository")
    private DiscussionRepository discussionRepository;
    
    public Discussion findById(Long id) {
        
        return discussionRepository.getOne(id);
    }
    
    public void save(Discussion discussion) {
        
        discussionRepository.save(discussion);
    }
    
    public List<Discussion> findAll() {
        
        return discussionRepository.findAll();
    }
    
    public void deleteById(Long id) {
        
        discussionRepository.deleteById(id);
    }
}
