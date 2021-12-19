package org.gab.estimateachers.app.services;

import org.gab.estimateachers.app.repositories.client.FacultyRepository;
import org.gab.estimateachers.app.utilities.FilesUtilities;
import org.gab.estimateachers.app.utilities.RegistrationType;
import org.gab.estimateachers.entities.client.Faculty;
import org.gab.estimateachers.entities.client.Teacher;
import org.gab.estimateachers.entities.client.University;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.Set;

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
    
    public Faculty findById(Long id) {
        
        return facultyRepository.getOne(id);
    }
    
    public void save(Faculty faculty) {
        
        facultyRepository.save(faculty);
    }
    
    public List<Faculty> findAll() {
        
        return facultyRepository.findAll();
    }
    
    public void deleteById(Long id) {
        
        facultyRepository.deleteById(id);
    }
    
    public Faculty findByTitleAndUniversity(String title, University university) {
        
        return facultyRepository.findByTitleAndUniversity(title, university);
    }
    
    public List<Faculty> findByTitles(Set<String> facultiesTitles) {
        
        return facultyRepository.findAllByTitle(facultiesTitles);
    }
    
    public void edit(Long id, String title, Set<MultipartFile> files) {
        
        Faculty card = facultyRepository.getOne(id);
        card.setTitle(title);
        files.stream()
                .map(f -> filesUtilities.registrationFile(f, RegistrationType.OTHER))
                .forEach(card::addPhoto);
    
        facultyRepository.save(card);
    }
    
    public Faculty create(String facultyTitle, University university, Set<String> teachersTitles, Set<MultipartFile> files, boolean approved) {
        
        Faculty faculty = new Faculty(facultyTitle, university);
        university.addFaculty(faculty);
        if(Objects.isNull(files) || files.isEmpty())
            faculty.addPhoto(filesUtilities.registrationFile(null, RegistrationType.OTHER));
        else
            files.stream().map(f -> filesUtilities.registrationFile(f, RegistrationType.OTHER)).forEach(faculty::addPhoto);
        faculty.setApproved(approved);
        save(faculty);
        universityService.save(university);
        List<Teacher> teachers = teacherService.findByTitles(teachersTitles);
        teachers.stream().peek(t -> t.addFaculty(faculty)).forEach(teacherService::save);
        
        return faculty;
    }
    
    public boolean existsByTitle(String title) {
        
        return facultyRepository.existsByTitle(title);
    }
}
