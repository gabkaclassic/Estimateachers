package org.gab.estimateachers.app.services;

import org.gab.estimateachers.entities.client.Card;
import org.gab.estimateachers.entities.system.users.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface CardService<T extends Card> extends Service<T> {
    
    boolean existsByTitle(String title);
    
    default void approve(Long id) {
        
        T card = findById(id);
        card.setApproved(true);
        save(card);
    }
    
    void edit(Long id, String title, Set<MultipartFile> files);
    
    List<Card> findByTitlePattern(String pattern);
    
    Collection<? extends Card> findByListId(Set<Long> universitiesId);
    
    Collection<? extends Card> findByListIdAndPattern(Set<Long> id, String pattern);
}