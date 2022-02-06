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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping("/applications")
public class ApplicationController extends org.gab.estimateachers.app.controllers.Controller {
    
    @Value("${spring.mail.username}")
    private String supportEmail;
    
    protected final String ERROR_MESSAGE = """
            Error occurred: %s
            Reason: %s
            Error occurred. To prevent this from happening again, please help our service: send this message in the form of a screenshot/copied text,
            along with the current time and, if possible, the actions that you performed before this error occurred, to our employee at the email address: %s \n
            Thank you for helping our service develop. Please go to the start page of the service.
            """;
    
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
    @Retryable(maxAttempts = 5, value = Exception.class, backoff = @Backoff(delay = 300, multiplier = 1.5))
    public String plug(HttpServletRequest request) {
        
        return "redirect:" + request.getHeader("Referer");
    }
    
    @GetMapping("/users")
    @Retryable(maxAttempts = 5, value = Exception.class, backoff = @Backoff(delay = 300, multiplier = 1.5))
    public String newUserApplications(Model model) {
        
        model.addAttribute("applications", listUtilities.getRegistrationApplicationList());
        model.addAttribute("type", "registry");
        
        return "/applications";
    }
    
    @GetMapping("/cards")
    @Retryable(maxAttempts = 5, value = Exception.class, backoff = @Backoff(delay = 300, multiplier = 1.5))
    public String newCardApplications(Model model) {
        
        model.addAttribute("applications", listUtilities.getCreatingCardApplicationList());
        model.addAttribute("type", "card");
        
        return "/applications";
    }
    
    @GetMapping("/approving/{id}")
    @Retryable(maxAttempts = 5, value = Exception.class, backoff = @Backoff(delay = 300, multiplier = 1.5))
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
    @Retryable(maxAttempts = 5, value = Exception.class, backoff = @Backoff(delay = 300, multiplier = 1.5))
    public String approvingCardSave(@RequestParam(name = "id") Long applicationId,
                                Model model) {
        
        creatingCardApplicationService.approve(applicationId);
        
        return "redirect:/applications/cards";
    }
    
    @GetMapping("/processing/first/{id}")
    @Retryable(maxAttempts = 5, value = Exception.class, backoff = @Backoff(delay = 300, multiplier = 1.5))
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
    @Retryable(maxAttempts = 5, value = Exception.class, backoff = @Backoff(delay = 300, multiplier = 1.5))
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
    @Retryable(maxAttempts = 5, value = Exception.class, backoff = @Backoff(delay = 300, multiplier = 1.5))
    public String processingApplicationSecondStep(@PathVariable(name = "id") Long applicationId,
                                                  Model model) {
        
        RegistrationApplication application = registrationApplicationService.findById(applicationId);
        
        model.addAttribute("application", application);
        model.addAttribute("student", application.getStudent());
        
        return "/process_application_second";
    }
    
    @PostMapping("/processing/second/{id}")
    @Retryable(maxAttempts = 5, value = Exception.class, backoff = @Backoff(delay = 300, multiplier = 1.5))
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
    @Retryable(maxAttempts = 5, value = Exception.class, backoff = @Backoff(delay = 300, multiplier = 1.5))
    public String rejectRegistry(@PathVariable(name = "id") Long applicationId,
                                 @RequestParam("reason") String reason) {
        
        registrationApplicationService.reject(applicationId, reason);
        
        return "redirect:/applications/users";
    }
    
    @PostMapping("/reject/card/{id}")
    @Retryable(maxAttempts = 5, value = Exception.class, backoff = @Backoff(delay = 300, multiplier = 1.5))
    public String rejectCard(@PathVariable(name = "id") Long applicationId,
                             @RequestParam("reason") String reason) {
        
        creatingCardApplicationService.reject(applicationId, reason);
        
        return "redirect:/applications/cards";
    }
    
    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/requests")
    @Retryable(maxAttempts = 5, value = Exception.class, backoff = @Backoff(delay = 300, multiplier = 1.5))
    public String menuRequest() {
        
        return "/add_request_menu";
    }
    
    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/requests")
    @Retryable(maxAttempts = 5, value = Exception.class, backoff = @Backoff(delay = 300, multiplier = 1.5))
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
    @Retryable(maxAttempts = 5, value = Exception.class, backoff = @Backoff(delay = 300, multiplier = 1.5))
    public String listCardRequest(Model model) {
        
        model.addAttribute("applications", listUtilities.getCardRequestList());
        model.addAttribute("type", "request");
        
        return "/applications";
    }
    
    @GetMapping("/requests/service")
    @Retryable(maxAttempts = 5, value = Exception.class, backoff = @Backoff(delay = 300, multiplier = 1.5))
    public String listServiceRequest(Model model) {
    
        model.addAttribute("applications", listUtilities.getServiceRequestList());
        model.addAttribute("type", "request");
        
        return "/applications";
    }
    
    @GetMapping("/requests/{id}")
    @Retryable(maxAttempts = 5, value = Exception.class, backoff = @Backoff(delay = 300, multiplier = 1.5))
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
    @Retryable(maxAttempts = 5, value = Exception.class, backoff = @Backoff(delay = 300, multiplier = 1.5))
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
    @Retryable(maxAttempts = 5, value = Exception.class, backoff = @Backoff(delay = 300, multiplier = 1.5))
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
    
    @Recover
    @PostMapping("/error")
    @GetMapping("/error")
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "An error on the server side or a click on an invalid link")
    public ModelAndView error(Exception exception) {
        
        ModelAndView model = new ModelAndView("Error");
        model.addObject("Error",
                String.format(ERROR_MESSAGE, exception.getMessage(), exception.getCause(), supportEmail)
        );
        
        return model;
    }
}
