package org.gab.estimateachers.app.services;

import org.gab.estimateachers.app.repositories.client.DormitoryRepository;
import org.gab.estimateachers.app.repositories.system.DormitoryEstimationRepository;
import org.gab.estimateachers.app.utilities.AWSUtilities;
import org.gab.estimateachers.app.utilities.FilesUtilities;
import org.gab.estimateachers.app.utilities.RegistrationType;
import org.gab.estimateachers.entities.client.Card;
import org.gab.estimateachers.entities.client.Dormitory;
import org.gab.estimateachers.entities.client.University;
import org.gab.estimateachers.entities.system.discussions.Discussion;
import org.gab.estimateachers.entities.system.estimations.DormitoryEstimation;
import org.gab.estimateachers.entities.system.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service("dormitoryService")
public class DormitoryService implements CardService<Dormitory>  {
    
    @Autowired
    @Qualifier("dormitoryRepository")
    private DormitoryRepository dormitoryRepository;
    
    @Autowired
    @Qualifier("filesUtilities")
    private FilesUtilities filesUtilities;
    
    @Autowired
    @Qualifier("dormitoryEstimationRepository")
    private DormitoryEstimationRepository dormitoryEstimationRepository;
    
    @Autowired
    @Qualifier("awsUtilities")
    private AWSUtilities awsUtilities;
    
    @Autowired
    @Qualifier("discussionService")
    private DiscussionService discussionService;
    
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
        
        Discussion discussion = new Discussion(dormitory);
        dormitory.setApproved(approved);
        dormitory.setDiscussion(discussion);
        discussionService.save(discussion);
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
    
    public List<Card> findByTitlePattern(String pattern) {
        
        return dormitoryRepository.findByTitlePattern(pattern)
                .stream().peek(d -> awsUtilities.loadFiles(d.getPhotos()))
                .map(Card.class::cast)
                .collect(Collectors.toList());
    }
    
    public Collection<? extends Card> findByListId(Set<Long> dormitoriesId) {
        
        return dormitoryRepository.findByListId(dormitoriesId).stream()
                .peek(d-> awsUtilities.loadFiles(d.getPhotos()))
                .collect(Collectors.toList());
    }
    
    public Collection<? extends Card> findByListIdAndPattern(Set<Long> dormitoriesId, String pattern) {
        
        return dormitoryRepository.findByListIdAndPattern(dormitoriesId, pattern).stream()
                .peek(d-> awsUtilities.loadFiles(d.getPhotos()))
                .collect(Collectors.toList());
    }
    
    public List<Card> findAllApproved() {
        
        return dormitoryRepository.findAllApproved()
                .stream().peek(d-> awsUtilities.loadFiles(d.getPhotos()))
                .map(Card.class::cast)
                .collect(Collectors.toList());
    }
    
    public List<Dormitory> findAll() {
        
        return dormitoryRepository.findAll().stream()
                .peek(d-> awsUtilities.loadFiles(d.getPhotos()))
                .collect(Collectors.toList());
    }
    
    public void deleteById(Long id) {
        
        Dormitory dormitory = dormitoryRepository.getOne(id);
        
        awsUtilities.deleteFiles(dormitory.getPhotos());
        dormitoryRepository.delete(dormitory);
    }
    
    public Dormitory findByTitle(String title) {
    
        Dormitory dormitory = dormitoryRepository.findByTitle(title);
    
        awsUtilities.loadFiles(dormitory.getPhotos());
        
        return dormitory;
    }
    
    public boolean existsByTitle(String title) {
        
        return dormitoryRepository.existsByTitle(title);
    }
    
    public void estimation(Long dormitoryId, User user, Integer cleaningRating, Integer roommatesRating, Integer capacityRating) {
        
        cleaningRating = Objects.isNull(cleaningRating) ? 0 : cleaningRating;
        roommatesRating = Objects.isNull(roommatesRating) ? 0 : roommatesRating;
        capacityRating = Objects.isNull(capacityRating) ? 0 : capacityRating;
    
        Dormitory dormitory = dormitoryRepository.getOne(dormitoryId);
        DormitoryEstimation estimation = new DormitoryEstimation(cleaningRating, roommatesRating, capacityRating, user);
        dormitory.estimation(estimation);
    
        dormitoryEstimationRepository.save(estimation);
        dormitoryRepository.save(dormitory);
    }
}
