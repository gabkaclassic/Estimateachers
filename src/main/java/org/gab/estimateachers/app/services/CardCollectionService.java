package org.gab.estimateachers.app.services;

import org.gab.estimateachers.app.repositories.system.CardCollectionRepository;
import org.gab.estimateachers.entities.client.Card;
import org.gab.estimateachers.entities.client.CardType;
import org.gab.estimateachers.entities.system.users.CardCollection;
import org.gab.estimateachers.entities.system.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("cardCollectionService")
public class CardCollectionService {
    
    @Autowired
    @Qualifier("cardCollectionRepository")
    private CardCollectionRepository cardCollectionRepository;
    
    @Autowired
    @Qualifier("dormitoryService")
    private DormitoryService dormitoryService;
    
    @Autowired
    @Qualifier("universityService")
    private UniversityService universityService;
    
    @Autowired
    @Qualifier("teacherService")
    private TeacherService teacherService;
    
    @Autowired
    @Qualifier("facultyService")
    private FacultyService facultyService;
    
    
    public List<Card> findCollectionByUser(User user) {
        
        List<CardCollection> collection = cardCollectionRepository.findCollectionByUser(user.getId());
        List<Card> cards = new ArrayList<>();
        Set<Long> universitiesId = new HashSet<>();
        Set<Long> dormitoriesId = new HashSet<>();
        Set<Long> facultiesId = new HashSet<>();
        Set<Long> teachersId = new HashSet<>();
        
        for(CardCollection c: collection) {
            
            switch (c.getCardType()) {
                case UNIVERSITY -> universitiesId.add(c.getCardId());
                case FACULTY -> facultiesId.add(c.getCardId());
                case TEACHER -> teachersId.add(c.getCardId());
                case DORMITORY -> dormitoriesId.add(c.getCardId());
                default -> {}
            }
        }
        
        cards.addAll(universityService.findByListId(universitiesId));
        cards.addAll(facultyService.findByListId(facultiesId));
        cards.addAll(dormitoryService.findByListId(dormitoriesId));
        cards.addAll(teacherService.findByListId(teachersId));
        
        return cards;
    }
    
    public List<Card> findCollectionByUserAndPattern(String pattern, User user) {
        
        List<CardCollection> collection = cardCollectionRepository.findCollectionByUser(user.getId());
        List<Card> cards = new ArrayList<>();
        Set<Long> universitiesId = new HashSet<>();
        Set<Long> dormitoriesId = new HashSet<>();
        Set<Long> facultiesId = new HashSet<>();
        Set<Long> teachersId = new HashSet<>();
        
        for(CardCollection c: collection) {
            
            switch (c.getCardType()) {
                case UNIVERSITY -> universitiesId.add(c.getCardId());
                case FACULTY -> facultiesId.add(c.getCardId());
                case TEACHER -> teachersId.add(c.getCardId());
                case DORMITORY -> dormitoriesId.add(c.getCardId());
                default -> {}
            }
        }
        
        cards.addAll(universityService.findByListIdAndPattern(universitiesId, pattern));
        cards.addAll(facultyService.findByListIdAndPattern(facultiesId, pattern));
        cards.addAll(dormitoryService.findByListIdAndPattern(dormitoriesId, pattern));
        cards.addAll(teacherService.findByListIdAndPattern(teachersId, pattern));
        
        return cards;
    }
    
    public void remove(User user, Long cardId, String cardType) {
        
        cardCollectionRepository.remove(user.getId(), cardId, CardType.valueOf(cardType));
    }
    
    public void create(User user, Long cardId, String cardType) {
        
        CardCollection collection = new CardCollection(user.getId(), cardId, cardType);
        cardCollectionRepository.save(collection);
    }
    
    public List<CardCollection> findCollectionByUserAndCardType(User user, String cardType) {
        
        return cardCollectionRepository.findByUserAndCardType(user.getId(), CardType.valueOf(cardType));
    }
}
