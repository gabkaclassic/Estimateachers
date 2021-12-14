package org.gab.estimateachers.app.controllers.applications;

import org.gab.estimateachers.app.services.*;
import org.gab.estimateachers.app.utilities.ApplicationsUtilities;
import org.gab.estimateachers.app.utilities.ListsUtilities;
import org.gab.estimateachers.entities.client.University;
import org.gab.estimateachers.entities.system.CreatingCardApplication;
import org.gab.estimateachers.entities.system.RegistrationApplication;
import org.gab.estimateachers.entities.system.RequestType;
import org.gab.estimateachers.entities.system.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
        
        CreatingCardApplication application = creatingCardApplicationService.findById(applicationId);
        
        switch (application.getCardType()) {
            
            case UNIVERSITY -> universityService.approve(application.getCardId());
            case DORMITORY -> dormitoryService.approve(application.getCardId());
            case TEACHER -> teacherService.approve(application.getCardId());
            case FACULTY -> facultyService.approve(application.getCardId());
        }
        
        creatingCardApplicationService.deleteById(applicationId);
        
        return "redirect:/applications/cards";
    }
    
    @GetMapping("/processing/first/{id}")
    public String processingApplicationFirstStep(@PathVariable(name = "id") Long applicationId,
                                                 Model model) {
        
        RegistrationApplication application = registrationApplicationService.findById(applicationId);
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
    public String rejectRegistry(@PathVariable(name = "id") Long applicationId) {
        
        registrationApplicationService.deleteById(applicationId);
        
        return "redirect:/applications/users";
    }
    
    @PostMapping("/reject/card/{id}")
    public String rejectCard(@PathVariable(name = "id") Long applicationId) {
        
        creatingCardApplicationService.deleteById(applicationId);
        
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
                              Model model) {
        
        List<String> remarks = new ArrayList<>();
        applicationsUtilities.checkRequestData(textRequest, type, remarks);

        if(!remarks.isEmpty()) {
            model.addAttribute("remarks", remarks);
            model.addAttribute("text", textRequest);
        }
        else
            requestService.create(user, dateSending, textRequest, type);
        
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
        
        model.addAttribute("request", requestService.findById(requestId));
        
        return "/request";
    }
    
    @PostMapping("/requests/success/{id}")
    public String successRequest(@PathVariable("id") Long requestId,
                             Model model) {
        
        RequestType type = requestService.findById(requestId).getRequestType();
        requestService.deleteById(requestId);
    
        switch (type) {
            case CHANGING_CARDS -> { return "redirect:/applications/requests/cards";}
            case OPERATIONS_SERVICE -> { return "redirect:/applications/requests/service"; }
            default -> { return "redirect:/"; }
        }
    }
    
    @PostMapping("/requests/reject/{id}")
    public String rejectRequest(@PathVariable("id") Long requestId,
                                @RequestParam("reason") String reason,
                                 Model model) {
        
        RequestType type = requestService.findById(requestId).getRequestType();
        requestService.deleteById(requestId);
        
        switch (type) {
            case CHANGING_CARDS -> { return "redirect:/applications/requests/cards";}
            case OPERATIONS_SERVICE -> { return "redirect:/applications/requests/service"; }
            default -> { return "redirect:/"; }
        }
    }
    
    
}