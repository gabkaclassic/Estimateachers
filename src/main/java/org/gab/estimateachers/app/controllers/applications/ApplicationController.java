package org.gab.estimateachers.app.controllers.applications;

import org.gab.estimateachers.app.services.*;
import org.gab.estimateachers.app.utilities.ApplicationsUtilities;
import org.gab.estimateachers.app.utilities.ListsUtilities;
import org.gab.estimateachers.entities.client.University;
import org.gab.estimateachers.entities.system.applications.CreatingCardApplication;
import org.gab.estimateachers.entities.system.applications.RegistrationApplication;
import org.gab.estimateachers.entities.system.applications.Request;
import org.gab.estimateachers.entities.system.applications.RequestType;
import org.gab.estimateachers.entities.system.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping("/applications")
public class ApplicationController {
    
    @Autowired
    @Qualifier("applicationsUtilities")
    private ApplicationsUtilities applicationsUtilities;
    
    @Autowired
    @Qualifier("listsUtilities")
    private ListsUtilities listUtilities;
    
    @Autowired
    @Qualifier("registrationApplicationService")
    private RegistrationApplicationService registrationApplicationService;
    
    @Autowired
    @Qualifier("creatingCardApplicationService")
    private CreatingCardApplicationService creatingCardApplicationService;
    
    @Autowired
    @Qualifier("requestService")
    private RequestService requestService;
    
    @Autowired
    @Qualifier("universityService")
    private UniversityService universityService;
    
    @Autowired
    @Qualifier("teacherService")
    private TeacherService teacherService;
    
    @Autowired
    @Qualifier("facultyService")
    private FacultyService facultyService;
    
    @Autowired
    @Qualifier("dormitoryService")
    private DormitoryService dormitoryService;
    
    @GetMapping(value = {
            "/requests/reject/{id}",
            "/requests/success/{id}",
            "/reject/card/{id}",
            "/reject/registry/{id}"
    })
    public String plug(HttpServletRequest request) {
        
        return "redirect:" + request.getHeader("Referer");
    }
    
    @GetMapping("/users")
    public String newUserApplications(Model model) {
        
        model.addAttribute("applications", listUtilities.getRegistrationApplicationList());
        model.addAttribute("type", "registry");
        
        return "/applications";
    }
    
    @GetMapping("/cards")
    public String newCardApplications(Model model) {
        
        model.addAttribute("applications", listUtilities.getCreatingCardApplicationList());
        model.addAttribute("type", "card");
        
        return "/applications";
    }
    
    @GetMapping("/approving/{id}")
    public String approvingCard(@PathVariable(name = "id") Long applicationId,
                                Model model) {
    
        CreatingCardApplication application = creatingCardApplicationService.findById(applicationId);
        
        if(application.isViewed()) {
            
            model.addAttribute("application", application);
            
            return "/application_is_viewed";
        }
        
        application.setViewed(true);
        creatingCardApplicationService.save(application);
        Long cardId = application.getCardId();
        
        switch (application.getCardType()) {
            
            case UNIVERSITY -> model.addAttribute("card", universityService.findById(cardId));
            case DORMITORY -> model.addAttribute("card", dormitoryService.findById(cardId));
            case TEACHER -> model.addAttribute("card", teacherService.findById(cardId));
            case FACULTY -> model.addAttribute("card", facultyService.findById(cardId));
        }
        
        model.addAttribute("cardType", application.getCardType().toString());
        model.addAttribute("application", application);
        
        return "/processing_new_card";
    }
    
    @PostMapping("/approving")
    public String approvingCardSave(@RequestParam(name = "id") Long applicationId,
                                Model model) {
        
        creatingCardApplicationService.approve(applicationId);
        
        return "redirect:/applications/cards";
    }
    
    @GetMapping("/processing/first/{id}")
    public String processingApplicationFirstStep(@PathVariable(name = "id") Long applicationId,
                                                 Model model) {
            
        RegistrationApplication application = registrationApplicationService.findById(applicationId);
        
        if(application.isViewed()) {
            
            model.addAttribute("application", application);
            
            return "/application_is_viewed";
        }
        application.setViewed(true);
        registrationApplicationService.save(application);
        model.addAttribute("universities", listUtilities.getUniversitiesAbbreviationsList());
        model.addAttribute("application", application);
        model.addAttribute("student", application.getStudent());
        
        return "/process_application_first";
    }
    
    @PostMapping("/processing/first/{id}")
    public String processingApplicationFirstStepSave(@PathVariable(name = "id") Long applicationId,
                                                     @RequestParam("course") Integer course,
                                                     @RequestParam("university") String abbreviationUniversity,
                                                     Model model) {
        
        University university = universityService.findByAbbreviation(abbreviationUniversity);
        model.addAttribute("dormitories", listUtilities.convertToTitlesList(university.getDormitories()));
        model.addAttribute("faculties", listUtilities.convertToTitlesList(university.getFaculties()));
        model.addAttribute("university", university);
        model.addAttribute("course", course);
        
        return processingApplicationSecondStep(applicationId, model);
    }
    
    @GetMapping("/processing/second/{id}")
    public String processingApplicationSecondStep(@PathVariable(name = "id") Long applicationId,
                                                  Model model) {
        
        RegistrationApplication application = registrationApplicationService.findById(applicationId);
        
        model.addAttribute("application", application);
        model.addAttribute("student", application.getStudent());
        
        return "/process_application_second";
    }
    
    @PostMapping("/processing/second/{id}")
    public String processingApplicationSecondStepSave(@PathVariable(name = "id") Long applicationId,
                                                      @RequestParam("faculty") String facultyTitle,
                                                      @RequestParam("universityId") Long universityId,
                                                      @RequestParam(value = "dormitory", required = false) String dormitoryTitle,
                                                      @RequestParam("course") Integer course,
                                                      Model model) {
        
        registrationApplicationService.apply(applicationId, universityId, facultyTitle, dormitoryTitle, course);
        
        return "redirect:/applications/users";
    }
    
    @PostMapping("/reject/registry/{id}")
    public String rejectRegistry(@PathVariable(name = "id") Long applicationId,
                                 @RequestParam("reason") String reason) {
        
        registrationApplicationService.reject(applicationId, reason);
        
        return "redirect:/applications/users";
    }
    
    @PostMapping("/reject/card/{id}")
    public String rejectCard(@PathVariable(name = "id") Long applicationId,
                             @RequestParam("reason") String reason) {
        
        creatingCardApplicationService.reject(applicationId, reason);
        
        return "redirect:/applications/cards";
    }
    
    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/requests")
    public String menuRequest() {
        
        return "/add_request_menu";
    }
    
    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/requests")
    public String saveRequest(@AuthenticationPrincipal User user,
                              @RequestParam("text") String textRequest,
                              @RequestParam("date") String dateSending,
                              @RequestParam(value = "type", required = false) String type,
                              @RequestParam(value = "files", required = false) Set<MultipartFile> files,
                              Model model) {
        
        List<String> remarks = new ArrayList<>();
        applicationsUtilities.checkRequestData(textRequest, type, remarks);
        
        if(!remarks.isEmpty()) {
            model.addAttribute("remarks", remarks);
            model.addAttribute("text", textRequest);
        }
        else
            requestService.create(user, dateSending, textRequest, type, files);
        
        return "/add_request_menu";
    }
    
    @GetMapping("/requests/cards")
    public String listCardRequest(Model model) {
        
        model.addAttribute("applications", listUtilities.getCardRequestList());
        model.addAttribute("type", "request");
        
        return "/applications";
    }
    
    @GetMapping("/requests/service")
    public String listServiceRequest(Model model) {
    
        model.addAttribute("applications", listUtilities.getServiceRequestList());
        model.addAttribute("type", "request");
        
        return "/applications";
    }
    
    @GetMapping("/requests/{id}")
    public String getRequest(@PathVariable("id") Long requestId,
                             Model model) {
        
        Request request = requestService.findById(requestId);
        if(request.isViewed()) {
            
            model.addAttribute("application", request);
    
            return "/application_is_viewed";
        }
        request.setViewed(true);
        requestService.save(request);
        model.addAttribute("request", request);
        model.addAttribute("numbers", listUtilities.getNumbers(request.getPhotos()));
        
        return "/request";
    }
    
    @PostMapping("/requests/success/{id}")
    public String successRequest(@PathVariable("id") Long requestId,
                             Model model) {
    
        Request request = requestService.findById(requestId);
        RequestType type = request.getRequestType();
        requestService.apply(request);
    
        return switch (type) {
            case CHANGING_CARDS -> "redirect:/applications/requests/cards";
            case OPERATIONS_SERVICE -> "redirect:/applications/requests/service";
        };
    }
    
    @PostMapping("/requests/reject/{id}")
    public String rejectRequest(@PathVariable("id") Long requestId,
                                @RequestParam("reason") String reason,
                                 Model model) {
        
        RequestType type = requestService.findById(requestId).getRequestType();
        requestService.reject(requestId, reason);
        
        return switch (type) {
            case CHANGING_CARDS -> "redirect:/applications/requests/cards";
            case OPERATIONS_SERVICE -> "redirect:/applications/requests/service";
        };
    }
}
