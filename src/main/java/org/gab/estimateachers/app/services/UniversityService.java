package org.gab.estimateachers.app.services;

import org.gab.estimateachers.app.repositories.client.UniversityRepository;
import org.gab.estimateachers.app.repositories.system.UniversityEstimationRepository;
import org.gab.estimateachers.app.utilities.FilesUtilities;
import org.gab.estimateachers.app.utilities.RegistrationType;
import org.gab.estimateachers.entities.client.Card;
import org.gab.estimateachers.entities.client.University;
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
        
        university.setApproved(approved);
        save(university);
        
        return university;
    }
    
    public List<Card> findAllApproved() {
        
        return universityRepository.findAllApproved()
                .stream()
                .map(Card.class::cast)
                .collect(Collectors.toList());
    }
    
    public List<University> findAll() {
        
        return universityRepository.findAll();
    }
    
    public void deleteById(Long id) {
        
        universityRepository.deleteById(id);
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
        
        return universityRepository.findByTitlePattern(pattern)
                .stream()
                .map(Card.class::cast)
                .collect(Collectors.toList());
    }
    
    public Collection<? extends Card> findByListId(Set<Long> universitiesId) {
        
        return universityRepository.findByListId(universitiesId);
    }
    
    public Collection<? extends Card> findByListIdAndPattern(Set<Long> universitiesId, String pattern) {
        
        return universityRepository.findByListIdAndPattern(universitiesId, pattern);
    }
    
    public List<Card> findByAbbreviationPattern(String pattern) {
        
        return universityRepository.findByAbbreviationPattern(pattern)
                .stream()
                .map(Card.class::cast)
                .collect(Collectors.toList());
    }
    
    public List<Card> findByPattern(String pattern) {
        
        return universityRepository.findByPattern(pattern)
                .stream()
                .map(Card.class::cast)
                .collect(Collectors.toList());
    }
    
    public University findByAbbreviation(String abbreviation) {
        
        return universityRepository.findByAbbreviation(abbreviation);
    }
    
    public List<University> findByAbbreviations(Set<String> abbreviations) {
       
        return universityRepository.findAllByAbbreviation(abbreviations);
    }
    
    public University findById(Long id) {
        
        return universityRepository.getOne(id);
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