package org.gab.estimateachers.app.services;

import org.gab.estimateachers.app.repositories.client.UniversityRepository;
import org.gab.estimateachers.app.repositories.system.UniversityEstimationRepository;
import org.gab.estimateachers.app.utilities.AWSUtilities;
import org.gab.estimateachers.app.utilities.FilesUtilities;
import org.gab.estimateachers.app.utilities.RegistrationType;
import org.gab.estimateachers.entities.client.Card;
import org.gab.estimateachers.entities.client.University;
import org.gab.estimateachers.entities.system.discussions.Discussion;
import org.gab.estimateachers.entities.system.estimations.UniversityEstimation;
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

@Service("universityService")
public class UniversityService implements CardService<University> {
    
    @Autowired
    @Qualifier("universityRepository")
    private UniversityRepository<Long> universityRepository;
    
    @Autowired
    @Qualifier("universityEstimationRepository")
    private UniversityEstimationRepository universityEstimationRepository;
    
    @Autowired
    @Qualifier("filesUtilities")
    private FilesUtilities filesUtilities;
    
    @Autowired
    @Qualifier("awsUtilities")
    private AWSUtilities awsUtilities;
    
    @Autowired
    @Qualifier("discussionService")
    private DiscussionService discussionService;
    
    public void save(University university) {
        
        universityRepository.save(university);
    }
    
    public University create(String universityTitle,
                             Boolean bachelor,
                             Boolean magistracy,
                             Boolean specialty,
                             Set<MultipartFile> files,
                             boolean approved) {
        
        University university = new University(universityTitle, bachelor, magistracy, specialty);
        if(Objects.isNull(files) || files.isEmpty())
            university.addPhoto(filesUtilities.registrationFile(null, RegistrationType.BUILDING));
        else
            files.stream().map(f -> filesUtilities.registrationFile(f, RegistrationType.BUILDING)).forEach(university::addPhoto);
        
        Discussion discussion = new Discussion(university);
        university.setApproved(approved);
        university.setDiscussion(discussion);
        save(university);
        discussionService.save(discussion);
        
        return university;
    }
    
    public List<Card> findAllApproved() {
        
        return universityRepository.findAllApproved().stream()
                .peek(u -> awsUtilities.loadFiles(u.getPhotos()))
                .map(Card.class::cast)
                .collect(Collectors.toList());
    }
    
    public List<University> findAll() {
        
        return universityRepository.findAll().stream()
                .peek(u -> awsUtilities.loadFiles(u.getPhotos()))
                .collect(Collectors.toList());
    }
    
    public void deleteById(Long id) {
        
        University university = universityRepository.getOne(id);
        awsUtilities.deleteFiles(university.getPhotos());
        
        universityRepository.delete(university);
    }
    
    public boolean existsByTitle(String title) {
        
        return universityRepository.existsByTitle(title);
    }
    
    public void edit(Long id, String title, Set<MultipartFile> files) {
    
        University card = universityRepository.getOne(id);
        card.setTitle(title);
        files.stream()
                .map(f -> filesUtilities.registrationFile(f, RegistrationType.OTHER))
                .forEach(card::addPhoto);
    
        universityRepository.save(card);
    }
    
    public List<Card> findByTitlePattern(String pattern) {
        
        return universityRepository.findByTitlePattern(pattern).stream()
                .peek(u -> awsUtilities.loadFiles(u.getPhotos()))
                .map(Card.class::cast)
                .collect(Collectors.toList());
    }
    
    public Collection<? extends Card> findByListId(Set<Long> universitiesId) {
        
        return universityRepository.findByListId(universitiesId).stream()
                .peek(u -> awsUtilities.loadFiles(u.getPhotos()))
                .collect(Collectors.toList());
    }
    
    public Collection<? extends Card> findByListIdAndPattern(Set<Long> universitiesId, String pattern) {
        
        return universityRepository.findByListIdAndPattern(universitiesId, pattern).stream()
                .peek(u -> awsUtilities.loadFiles(u.getPhotos()))
                .collect(Collectors.toList());
    }
    
    public List<Card> findByAbbreviationPattern(String pattern) {
        
        return universityRepository.findByAbbreviationPattern(pattern).stream()
                .peek(u -> awsUtilities.loadFiles(u.getPhotos()))
                .map(Card.class::cast)
                .collect(Collectors.toList());
    }
    
    public List<Card> findByPattern(String pattern) {
        
        return universityRepository.findByPattern(pattern).stream()
                .peek(u -> awsUtilities.loadFiles(u.getPhotos()))
                .map(Card.class::cast)
                .collect(Collectors.toList());
    }
    
    public University findByAbbreviation(String abbreviation) {
        
        University university = universityRepository.findByAbbreviation(abbreviation);
        
        awsUtilities.loadFiles(university.getPhotos());
        
        return university;
    }
    
    public List<University> findByAbbreviations(Set<String> abbreviations) {
       
        return universityRepository.findAllByAbbreviation(abbreviations).stream()
                .peek(u -> awsUtilities.loadFiles(u.getPhotos()))
                .collect(Collectors.toList());
    }
    
    public University findById(Long id) {
        
        University university = universityRepository.getOne(id);
    
        awsUtilities.loadFiles(university.getPhotos());
    
        return university;
    }
    
    public void saveAll(List<University> universities) {
        
        universityRepository.saveAll(universities);
    }
    
    public void estimation(Long universityId, User user, Integer priceRating, Integer complexityRating, Integer utilityRating) {
    
        priceRating = Objects.isNull(priceRating) ? 0 : priceRating;
        complexityRating = Objects.isNull(complexityRating) ? 0 : complexityRating;
        utilityRating = Objects.isNull(utilityRating) ? 0 : utilityRating;
        
        University university = universityRepository.getOne(universityId);
        UniversityEstimation estimation = new UniversityEstimation(priceRating, complexityRating, utilityRating, user);
        university.estimation(estimation);
        
        universityEstimationRepository.save(estimation);
        universityRepository.save(university);
    }
    
    public List<String> findAllAbbreviationApproved() {
        
        return universityRepository.findAllAbbreviationApproved();
    }
}