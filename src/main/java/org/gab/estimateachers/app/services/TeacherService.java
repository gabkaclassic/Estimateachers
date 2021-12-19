package org.gab.estimateachers.app.services;

import org.gab.estimateachers.app.repositories.client.TeacherRepository;
import org.gab.estimateachers.app.utilities.FilesUtilities;
import org.gab.estimateachers.app.utilities.RegistrationType;
import org.gab.estimateachers.entities.client.Card;
import org.gab.estimateachers.entities.client.Faculty;
import org.gab.estimateachers.entities.client.Teacher;
import org.gab.estimateachers.entities.client.University;
import org.gab.estimateachers.entities.system.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service("teacherService")
public class TeacherService implements CardService<Teacher> {
    
    @Autowired
    @Qualifier("teacherRepository")
    TeacherRepository teacherRepository;
    
    @Autowired
    @Qualifier("universityService")
    UniversityService universityService;
    
    @Autowired
    @Qualifier("facultyService")
    FacultyService facultyService;
    
    @Autowired
    @Qualifier("filesUtilities")
    private FilesUtilities filesUtilities;
    
    public Teacher findById(Long id) {
        
        return teacherRepository.getOne(id);
    }
    
    public void save(Teacher teacher) {
        
        teacherRepository.save(teacher);
    }
    
//    public void create(Teacher teacher, List<University> universities, List<Faculty> faculties) {
//
//
//    }
    
    public List<Teacher> findAll() {
        
        return teacherRepository.findAll();
    }
    
    public void deleteById(Long id) {
        
        teacherRepository.deleteById(id);
    }
    
    public boolean existsByTitle(String title) {
        
        return teacherRepository.existsByTitle(title);
    }
    
    public Teacher create(String firstname,
                          String lastname,
                          String patronymic,
                          Set<String> universitiesAbbreviation,
                          Set<String> facultiesTitles,
                          Set<MultipartFile> files,
                          boolean approved) {
        
        List<University> universities = universityService.findByAbbreviations(universitiesAbbreviation);
        List<Faculty> faculties = facultyService.findByTitles(facultiesTitles);
        Teacher teacher = new Teacher(firstname, lastname, patronymic);
        teacher.setApproved(approved);
        teacher.setUniversities(Set.copyOf(universities));
        teacher.setFaculties(Set.copyOf(faculties));
        if(Objects.isNull(files) || files.isEmpty())
            teacher.addPhoto(filesUtilities.registrationFile(null, RegistrationType.PEOPLE));
        else
            files.stream().map(f -> filesUtilities.registrationFile(f, RegistrationType.PEOPLE)).forEach(teacher::addPhoto);
    
        save(teacher);
        universities.stream().peek(u -> u.addTeacher(teacher)).forEach(universityService::save);
        faculties.stream().peek(f -> f.addTeacher(teacher)).forEach(facultyService::save);
        
        return teacher;
    }
    
    public void edit(Long id, String title, Set<MultipartFile> files) {
        
        Teacher card = teacherRepository.getOne(id);
        card.setTitle(title);
        files.stream()
                .map(f -> filesUtilities.registrationFile(f, RegistrationType.OTHER))
                .forEach(card::addPhoto);
    
        teacherRepository.save(card);
    }
    
    public List<String> getTitles() {
        
        return teacherRepository.getTitles();
    }
    
    public List<Teacher> findByTitles(Set<String> teachersTitles) {
        
        return teacherRepository.findByTitles(teachersTitles);
    }
}
