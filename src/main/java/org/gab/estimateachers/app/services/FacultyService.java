package org.gab.estimateachers.app.services;

import org.gab.estimateachers.app.repositories.client.FacultyRepository;
import org.gab.estimateachers.app.repositories.system.FacultyEstimationRepository;
import org.gab.estimateachers.app.utilities.AWSUtilities;
import org.gab.estimateachers.app.utilities.FilesUtilities;
import org.gab.estimateachers.app.utilities.RegistrationType;
import org.gab.estimateachers.entities.client.Card;
import org.gab.estimateachers.entities.client.Faculty;
import org.gab.estimateachers.entities.client.Teacher;
import org.gab.estimateachers.entities.client.University;
import org.gab.estimateachers.entities.system.discussions.Discussion;
import org.gab.estimateachers.entities.system.estimations.FacultyEstimation;
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

@Service("facultyService")
public class FacultyService implements CardService<Faculty> {
    
    @Autowired
    @Qualifier("teacherService")
    private TeacherService teacherService;
    
    @Autowired
    @Qualifier("facultyRepository")
    private FacultyRepository facultyRepository;
    
    @Autowired
    @Qualifier("universityService")
    private UniversityService universityService;
    
    @Autowired
    @Qualifier("filesUtilities")
    private FilesUtilities filesUtilities;
    
    @Autowired
    @Qualifier("awsUtilities")
    private AWSUtilities awsUtilities;
    
    @Autowired
    @Qualifier("discussionService")
    private DiscussionService discussionService;
    
    @Autowired
    @Qualifier("facultyEstimationRepository")
    private FacultyEstimationRepository facultyEstimationRepository;
    
    public Faculty findById(Long id) {
    
        Faculty faculty = facultyRepository.getOne(id);
    
        awsUtilities.loadFiles(faculty.getPhotos());
        
        return faculty;
    }
    
    public void save(Faculty faculty) {
        
        facultyRepository.save(faculty);
    }
    
    public List<Card> findAllApproved() {
        
        return facultyRepository.findAllApproved().stream()
                .peek(f -> awsUtilities.loadFiles(f.getPhotos()))
                .map(Card.class::cast)
                .collect(Collectors.toList());
    }
    
    public List<Faculty> findAll() {
        
        return facultyRepository.findAll().stream()
                .peek(f -> awsUtilities.loadFiles(f.getPhotos()))
                .collect(Collectors.toList());
    }
    
    public void deleteById(Long id) {
    
        Faculty faculty = facultyRepository.getOne(id);
    
        awsUtilities.loadFiles(faculty.getPhotos());
        
        facultyRepository.delete(faculty);
    }
    
    public Faculty findByTitleAndUniversity(String title, University university) {
    
        Faculty faculty = facultyRepository.findByTitleAndUniversity(title, university);
    
        awsUtilities.loadFiles(faculty.getPhotos());
        
        return faculty;
    }
    
    public List<Faculty> findByTitles(Set<String> facultiesTitles) {
        
        return facultyRepository.findAllByTitle(facultiesTitles).stream()
                .peek(f -> awsUtilities.loadFiles(f.getPhotos()))
                .collect(Collectors.toList());
    }
    
    public void edit(Long id, String title, Set<MultipartFile> files) {
        
        Faculty card = facultyRepository.getOne(id);
        card.setTitle(title);
        files.stream()
                .map(f -> filesUtilities.registrationFile(f, RegistrationType.OTHER))
                .forEach(card::addPhoto);
    
        facultyRepository.save(card);
    }
    
    public List<Card> findByTitlePattern(String pattern) {
        
        return facultyRepository.findByTitlePattern(pattern).stream()
                .peek(f -> awsUtilities.loadFiles(f.getPhotos()))
                .map(Card.class::cast)
                .collect(Collectors.toList());
    }
    
    public Collection<? extends Card> findByListId(Set<Long> facultiesId) {
        
        return facultyRepository.findByListId(facultiesId).stream()
                .peek(f -> awsUtilities.loadFiles(f.getPhotos()))
                .collect(Collectors.toList());
    }
    
    public Collection<? extends Card> findByListIdAndPattern(Set<Long> facultiesId, String pattern) {
        
        return facultyRepository.findByListIdAndPattern(facultiesId, pattern).stream()
                .peek(f -> awsUtilities.loadFiles(f.getPhotos()))
                .collect(Collectors.toList());
    }
    
    public Faculty create(String facultyTitle, University university, Set<String> teachersTitles, Set<MultipartFile> files, boolean approved) {
        
        Faculty faculty = new Faculty(facultyTitle, university);
        university.addFaculty(faculty);
        if(Objects.isNull(files) || files.isEmpty())
            faculty.addPhoto(filesUtilities.registrationFile(null, RegistrationType.OTHER));
        else
            files.stream().map(f -> filesUtilities.registrationFile(f, RegistrationType.OTHER)).forEach(faculty::addPhoto);
        
        faculty.setApproved(approved);
        Discussion discussion = new Discussion(faculty);
        faculty.setDiscussion(discussion);
        save(faculty);
        discussionService.save(discussion);
        universityService.save(university);
        List<Teacher> teachers = teacherService.findByTitles(teachersTitles);
        teachers.stream().peek(t -> t.addFaculty(faculty)).forEach(teacherService::save);
        
        return faculty;
    }
    
    public boolean existsByTitle(String title) {
        
        return facultyRepository.existsByTitle(title);
    }
    
    public void saveAll(List<Faculty> faculties) {
        
        facultyRepository.saveAll(faculties);
    }
    
    public void estimation(Long facultyId, User user, Integer priceRating, Integer educationRating) {
    
        priceRating = Objects.isNull(priceRating) ? 0 : priceRating;
        educationRating = Objects.isNull(educationRating) ? 0 : educationRating;
    
        Faculty faculty = facultyRepository.getOne(facultyId);
        FacultyEstimation estimation = new FacultyEstimation(priceRating, educationRating, user);
        faculty.estimation(estimation);
    
        facultyEstimationRepository.save(estimation);
        facultyRepository.save(faculty);
    }
    
    public List<String> findAllTitles() {
        
        return facultyRepository.findAllTitle();
    }
}
