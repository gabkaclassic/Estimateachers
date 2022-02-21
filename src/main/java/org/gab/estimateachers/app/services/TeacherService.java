package org.gab.estimateachers.app.services;

import org.gab.estimateachers.app.repositories.client.TeacherRepository;
import org.gab.estimateachers.app.repositories.system.TeacherEstimationRepository;
import org.gab.estimateachers.app.utilities.AWSUtilities;
import org.gab.estimateachers.app.utilities.FilesUtilities;
import org.gab.estimateachers.app.utilities.RegistrationType;
import org.gab.estimateachers.entities.client.Card;
import org.gab.estimateachers.entities.client.Faculty;
import org.gab.estimateachers.entities.client.Teacher;
import org.gab.estimateachers.entities.client.University;
import org.gab.estimateachers.entities.system.estimations.TeacherEstimation;
import org.gab.estimateachers.entities.system.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@Service("teacherService")
public class TeacherService implements CardService<Teacher> {
    
    @Autowired
    @Qualifier("teacherRepository")
    private TeacherRepository teacherRepository;
    
    @Autowired
    @Qualifier("universityService")
    private UniversityService universityService;
    
    @Autowired
    @Qualifier("facultyService")
    private FacultyService facultyService;
    
    @Autowired
    @Qualifier("filesUtilities")
    private FilesUtilities filesUtilities;
    
    @Autowired
    @Qualifier("awsUtilities")
    private AWSUtilities awsUtilities;
    
    @Autowired
    @Qualifier("teacherEstimationRepository")
    private TeacherEstimationRepository teacherEstimationRepository;
    
    public Teacher findById(Long id) {
    
        Teacher teacher = teacherRepository.getOne(id);
    
        awsUtilities.loadFiles(teacher.getPhotos());
    
        return teacher;
    }
    
    public void save(Teacher teacher) {
        
        teacherRepository.save(teacher);
    }
    
    public List<Card> findAllApproved() {
        
        return teacherRepository.findAllApproved().stream()
                .peek(t -> awsUtilities.loadFiles(t.getPhotos()))
                .map(Card.class::cast)
                .collect(Collectors.toList());
    }
    
    public List<Teacher> findAll() {
        
        return teacherRepository.findAll().stream()
                .peek(t -> awsUtilities.loadFiles(t.getPhotos()))
                .collect(Collectors.toList());
    }
    
    public void deleteById(Long id) {
        
        Teacher teacher = teacherRepository.getOne(id);
    
        awsUtilities.loadFiles(teacher.getPhotos());
        
        teacherRepository.delete(teacher);
    }
    
    public boolean existsByTitle(String title) {
        
        return teacherRepository.existsByTitle(title);
    }
    
    public Teacher create(String firstname,
                          String lastname,
                          String patronymic,
                          String excuses,
                          Set<String> universitiesAbbreviation,
                          Set<String> facultiesTitles,
                          Set<MultipartFile> files,
                          boolean approved) {
        
        List<University> universities = universityService.findByAbbreviations(universitiesAbbreviation);
        List<Faculty> faculties = facultyService.findByTitles(facultiesTitles);
        Set<String> excusesList = Arrays.stream(excuses.split(";")).collect(Collectors.toSet());
        Teacher teacher = new Teacher(firstname, lastname, patronymic, excusesList);
        teacher.setApproved(approved);
        teacher.setUniversities(Set.copyOf(universities));
        teacher.setFaculties(Set.copyOf(faculties));
        if(Objects.isNull(files) || files.isEmpty())
            teacher.addPhoto(filesUtilities.registrationFile(null, RegistrationType.PEOPLE));
        else
            files.stream().map(f -> filesUtilities.registrationFile(f, RegistrationType.PEOPLE)).forEach(teacher::addPhoto);
    
        save(teacher);
        faculties.forEach(f -> f.addTeacher(teacher));
        facultyService.saveAll(faculties);
        universities.forEach(f -> f.addTeacher(teacher));
        universityService.saveAll(universities);
        
        return teacher;
    }
    
    public void edit(Long id, String title, Set<MultipartFile> files, String excuses) {
        
        Teacher card = teacherRepository.getOne(id);
        Set<String> excusesList = Arrays.stream(excuses.split(";")).collect(Collectors.toSet());
        card.setTitle(title);
        card.setExcuses(excusesList);
        files.stream()
                .map(f -> filesUtilities.registrationFile(f, RegistrationType.OTHER))
                .forEach(card::addPhoto);
    
        teacherRepository.save(card);
    }
    
    public void edit(Long id, String title, Set<MultipartFile> files) {
    
        Teacher card = teacherRepository.getOne(id);
        card.setTitle(title);
        files.stream()
                .map(f -> filesUtilities.registrationFile(f, RegistrationType.OTHER))
                .forEach(card::addPhoto);
    
        teacherRepository.save(card);
    }
    
    public List<Card> findByTitlePattern(String pattern) {
        
        return teacherRepository.findByTitlePattern(pattern).stream()
                .peek(t -> awsUtilities.loadFiles(t.getPhotos()))
                .map(Card.class::cast)
                .collect(Collectors.toList());
    }
    
    public Collection<? extends Card> findByListId(Set<Long> teachersId) {
        
        return teacherRepository.findByListId(teachersId).stream()
                .peek(t -> awsUtilities.loadFiles(t.getPhotos()))
                .collect(Collectors.toList());
    }
    
    public Collection<? extends Card> findByListIdAndPattern(Set<Long> teachersId, String pattern) {
        
        return teacherRepository.findByListIdAndPattern(teachersId, pattern).stream()
                .peek(t -> awsUtilities.loadFiles(t.getPhotos()))
                .collect(Collectors.toList());
    }
    
    public List<String> getTitles() {
        
        return teacherRepository.getTitles();
    }
    
    public List<Teacher> findByTitles(Set<String> teachersTitles) {
        
        return teacherRepository.findByTitles(teachersTitles).stream()
                .peek(t -> awsUtilities.loadFiles(t.getPhotos()))
                .collect(Collectors.toList());
    }
    
    public void estimation(Long teacherId, User user, Integer freebiesRating, Integer exactingRating, Integer severityRating) {
    
    
        freebiesRating = Objects.isNull(freebiesRating) ? 0 : freebiesRating;
        exactingRating = Objects.isNull(exactingRating) ? 0 : exactingRating;
        severityRating = Objects.isNull(severityRating) ? 0 : severityRating;
    
        Teacher teacher = teacherRepository.getOne(teacherId);
        TeacherEstimation estimation = new TeacherEstimation(freebiesRating, exactingRating, severityRating, user);
        teacher.estimation(estimation);
    
        teacherEstimationRepository.save(estimation);
        teacherRepository.save(teacher);
    }
}
