package org.gab.estimateachers.app.services;

import org.gab.estimateachers.app.repositories.client.FacultyRepository;
import org.gab.estimateachers.app.utilities.FilesUtilities;
import org.gab.estimateachers.app.utilities.RegistrationType;
import org.gab.estimateachers.entities.client.Faculty;
import org.gab.estimateachers.entities.client.University;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service("facultyService")
public class FacultyService implements CardService<Faculty> {
    
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
    
    public Faculty create(String facultyTitle, String universityAbbreviation) {
        
        University university = universityService.findByAbbreviation(universityAbbreviation);
        Faculty faculty = new Faculty(facultyTitle, university);
        university.addFaculty(faculty);
        faculty.addPhoto(filesUtilities.registrationFile(null, RegistrationType.OTHER));
        
        save(faculty);
        universityService.save(university);
        
        return faculty;
    }
    
    public boolean existsByTitle(String title) {
        
        return facultyRepository.existsByTitle(title);
    }
}
