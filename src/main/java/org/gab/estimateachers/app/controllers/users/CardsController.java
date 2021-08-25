package org.gab.estimateachers.app.controllers.users;

import org.gab.estimateachers.app.services.DormitoryService;
import org.gab.estimateachers.app.services.FacultyService;
import org.gab.estimateachers.app.services.TeacherService;
import org.gab.estimateachers.app.services.UniversityService;
import org.gab.estimateachers.entities.client.Dormitory;
import org.gab.estimateachers.entities.client.Faculty;
import org.gab.estimateachers.entities.client.Teacher;
import org.gab.estimateachers.entities.client.University;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/cards")
@PreAuthorize("hasAuthority('ADMIN')")
public class CardsController {
    
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
    
    @PostMapping("/add/university")
    public String addUniversity(@RequestParam("title") String universityTitle) {
        
        universityService.create(new University(universityTitle));
        
        return "/process_application_first";
    }
    
    @PostMapping("/add/dormitory")
    public String addDormitory(@RequestParam("title") String dormitoryTitle,
                               @RequestParam("universityId") Long universityId) {
        
        
        dormitoryService.create(new Dormitory(dormitoryTitle, universityService.findById(universityId)));
        
        return "/process_application_first";
    }
    
    @PostMapping("/add/faculty")
    public String addFaculty(@RequestParam("title") String facultyTitle,
                             @RequestParam("universityId") Long universityId) {
        
        facultyService.save(new Faculty(facultyTitle, universityService.findById(universityId)));
        
        return "/process_application_first";
    }
    
    @PostMapping("/add/teacher")
    public String addTeacher(@RequestParam("firstname") String firstname,
                             @RequestParam("lastname") String lastname) {
        
        teacherService.create(new Teacher(firstname, lastname));
        
        return "/process_application_first";
    }
}
