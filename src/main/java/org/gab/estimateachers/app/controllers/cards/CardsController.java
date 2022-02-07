package org.gab.estimateachers.app.controllers.cards;

import lombok.extern.slf4j.Slf4j;
import org.gab.estimateachers.app.services.*;
import org.gab.estimateachers.app.utilities.CardsUtilities;
import org.gab.estimateachers.app.utilities.ListsUtilities;
import org.gab.estimateachers.app.utilities.UsersUtilities;
import org.gab.estimateachers.entities.client.Card;
import org.gab.estimateachers.entities.client.CardType;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Slf4j
@Controller
@RequestMapping("/cards")
public class CardsController extends org.gab.estimateachers.app.controllers.Controller {
    
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
    
    @Autowired
    @Qualifier("creatingCardApplicationService")
    private CreatingCardApplicationService creatingCardApplicationService;
    
    @Autowired
    @Qualifier("usersUtilities")
    private UsersUtilities usersUtilities;
    
    @Autowired
    @Qualifier("listsUtilities")
    private ListsUtilities listUtilities;
    
    @Autowired
    @Qualifier("cardsUtilities")
    private CardsUtilities cardsUtilities;
    
    @Autowired
    @Qualifier("cardCollectionService")
    private CardCollectionService cardCollectionService;
    
    @GetMapping(value = {
            "/search/title",
            "/delete",
            "/collection/add",
            "/edit",
            "/add/teacher",
            "/add/faculty",
            "/add/dormitory",
            "/add/university",
            "/estimation/teacher",
            "/estimation/dormitory",
            "/estimation/faculty",
            "/estimation/university"
            
    })
    @Retryable(maxAttempts = 5, value = Exception.class, backoff = @Backoff(delay = 300, multiplier = 1.5))
    public String plug(HttpServletRequest request) {
        
        String header = request.getHeader("Referer");
    
        log.info("A plug has triggered in cards controller, the request has been redirected to: " + header);
    
        return "redirect:" + header;
    }
    
    @GetMapping("/list/{cardType}")
    @Retryable(maxAttempts = 5, value = Exception.class, backoff = @Backoff(delay = 300, multiplier = 1.5))
    public String cardsList(@AuthenticationPrincipal User user,
                            @PathVariable("cardType") String cardType,
                            Model model) {
        
        model.addAttribute("user", user);
        model.addAttribute("isAdmin", Objects.nonNull(user) && user.isAdmin());
        listUtilities.findAllApproved(cardType, model);
        
        log.info(String.format("Requested the list of cards (type: %s)", cardType));
        
        return "/cards_list";
    }
    
    @PostMapping("/search/title")
    @Retryable(maxAttempts = 5, value = Exception.class, backoff = @Backoff(delay = 300, multiplier = 1.5))
    public String findByTitle(@AuthenticationPrincipal User user,
                              @RequestParam(value = "title", required = false) String title,
                              @RequestParam(value = "cardType") String cardType,
                              @RequestParam(value = "collection", required = false) Boolean collection,
                              Model model) {
        
        collection = Objects.nonNull(collection) && collection;
        
        if(Objects.nonNull(title) && !title.isEmpty()) {
            
            if(!collection)
                listUtilities.findByTitlePattern(title, cardType, model);
            else
                listUtilities.findByTitlePatternInCollection(title, cardType, user, model);
        }
        else
            return "redirect:/cards/list/".concat("ALL");
    
        model.addAttribute("user", user);
        model.addAttribute("collection", collection);
        model.addAttribute("isAdmin", Objects.nonNull(user) && user.isAdmin());
    
        log.info("Performed a search by card titles");
        
        return "/cards_list";
    }
    
    @PostMapping("/estimation/university")
    @Retryable(maxAttempts = 5, value = Exception.class, backoff = @Backoff(delay = 300, multiplier = 1.5))
    public String estimationUniversity(@AuthenticationPrincipal User user,
                                       @RequestParam(value = "priceRating", required = false) Integer priceRating,
                                       @RequestParam(value = "complexityRating", required = false) Integer complexityRating,
                                       @RequestParam(value = "utilityRating", required = false) Integer utilityRating,
                                       @RequestParam("cardId") Long universityId,
                                       Model model) {
        
        universityService.estimation(universityId, user, priceRating, complexityRating, utilityRating);
        
        log.info(String.format("The card of university has been evaluated (ID: %s)", universityId.toString()));
        
        return getCard(user, universityId, "university", model);
    }
    
    @PostMapping("/estimation/faculty")
    @Retryable(maxAttempts = 5, value = Exception.class, backoff = @Backoff(delay = 300, multiplier = 1.5))
    public String estimationFaculty(@AuthenticationPrincipal User user,
                                    @RequestParam(value = "priceRating", required = false) Integer priceRating,
                                    @RequestParam(value = "educationRating", required = false) Integer educationRating,
                                    @RequestParam("cardId") Long facultyId,
                                    Model model) {
        
        facultyService.estimation(facultyId, user, priceRating, educationRating);
    
        log.info(String.format("The card of faculty has been evaluated (ID: %s)", facultyId.toString()));
        
        return getCard(user, facultyId, "faculty", model);
    }
    
    @PostMapping("/estimation/dormitory")
    @Retryable(maxAttempts = 5, value = Exception.class, backoff = @Backoff(delay = 300, multiplier = 1.5))
    public String estimationDormitory(@AuthenticationPrincipal User user,
                                      @RequestParam(value = "cleaningRating", required = false) Integer cleaningRating,
                                      @RequestParam(value = "roommatesRating", required = false) Integer roommatesRating,
                                      @RequestParam(value = "capacityRating", required = false) Integer capacityRating,
                                      @RequestParam("cardId") Long dormitoryId,
                                      Model model) {
        
        dormitoryService.estimation(dormitoryId, user, cleaningRating, roommatesRating, capacityRating);
    
        log.info(String.format("The card of dormitory has been evaluated (ID: %s)", dormitoryId.toString()));
        
        return getCard(user, dormitoryId, "dormitory", model);
    }
    
    @PostMapping("/estimation/teacher")
    @Retryable(maxAttempts = 5, value = Exception.class, backoff = @Backoff(delay = 300, multiplier = 1.5))
    public String estimationTeacher(@AuthenticationPrincipal User user,
                                    @RequestParam(value = "freebiesRating", required = false) Integer freebiesRating,
                                    @RequestParam(value = "exactingRating", required = false) Integer exactingRating,
                                    @RequestParam(value = "severityRating", required = false) Integer severityRating,
                                    @RequestParam("cardId") Long teacherId,
                                    Model model) {
        
        teacherService.estimation(teacherId, user, freebiesRating, exactingRating, severityRating);
        
        log.info(String.format("The card of teacher has been evaluated (ID: %s)", teacherId.toString()));
        
        return getCard(user, teacherId, "teacher", model);
    }
    
    @GetMapping("/add")
    @Retryable(maxAttempts = 5, value = Exception.class, backoff = @Backoff(delay = 300, multiplier = 1.5))
    public String createCard(Model model) {
        
        model.addAttribute("universities", listUtilities.getUniversitiesAbbreviationsList());
        model.addAttribute("faculties", listUtilities.getAllFacultiesTitlesList());
        model.addAttribute("teachers", listUtilities.getTeachersTitles());
    
        log.info("The menu for creating cards was opened");
        
        return "/add_card_menu";
    }
    
    @PostMapping("/add/university")
    @Retryable(maxAttempts = 5, value = Exception.class, backoff = @Backoff(delay = 300, multiplier = 1.5))
    public String addUniversity(@AuthenticationPrincipal User user,
                                @RequestParam("title") String universityTitle,
                                @RequestParam(value = "bachelor", required = false) Boolean bachelor,
                                @RequestParam(value = "magistracy", required = false) Boolean magistracy,
                                @RequestParam(value = "specialty", required = false) Boolean specialty,
                                @RequestParam("date") String dateSending,
                                @RequestParam("files") Set<MultipartFile> files,
                                Model model) {
    
        List<String> remarks = new ArrayList<>();
        cardsUtilities.checkTitle(universityTitle, remarks, universityService);
    
        if(!remarks.isEmpty()) {
        
            model.addAttribute("remarks", remarks);
            
            log.info("Failed process of creating a university card");
            
            return "/add_card_menu";
        }
        
        if(user.isAdmin())
            universityService.create(universityTitle, bachelor, magistracy, specialty, files, user.isAdmin());
        else
            creatingCardApplicationService.create(
                    CardType.UNIVERSITY,
                    universityService.create(universityTitle, bachelor, magistracy, specialty, files, user.isAdmin()),
                    user,
                    dateSending
            );
        
        log.info("Success process of creating a university card");
        
        return createCard(model);
    }
    
    @PostMapping("/add/dormitory")
    @Retryable(maxAttempts = 5, value = Exception.class, backoff = @Backoff(delay = 300, multiplier = 1.5))
    public String addDormitory(@AuthenticationPrincipal User user,
                               @RequestParam("title") String dormitoryTitle,
                               @RequestParam("university") String universityAbbreviation,
                               @RequestParam("date") String dateSending,
                               @RequestParam("files") Set<MultipartFile> files,
                               Model model) {
    
        List<String> remarks = new ArrayList<>();
        cardsUtilities.checkTitle(dormitoryTitle, remarks, dormitoryService);
        
        if(!remarks.isEmpty()) {
            
            model.addAttribute("remarks", remarks);
    
            log.info("Failed process of creating a dormitory card");
            
            return "/add_card_menu";
        }
        
        if(user.isAdmin())
            dormitoryService.create(dormitoryTitle, universityService.findByAbbreviation(universityAbbreviation), files, user.isAdmin());
        else
            creatingCardApplicationService.create(
                    CardType.DORMITORY,
                    dormitoryService.create(dormitoryTitle, universityService.findByAbbreviation(universityAbbreviation), files, user.isAdmin()),
                    user,
                    dateSending
            );
    
        log.info("Success process of creating a dormitory card");
        
        return createCard(model);
    }
    
    @PostMapping("/add/faculty")
    @Retryable(maxAttempts = 5, value = Exception.class, backoff = @Backoff(delay = 300, multiplier = 1.5))
    public String addFaculty(@AuthenticationPrincipal User user,
                             @RequestParam("title") String facultyTitle,
                             @RequestParam("university") String universityAbbreviation,
                             @RequestParam("date") String dateSending,
                             @RequestParam("teachers") Set<String> teachersTitles,
                             @RequestParam("files") Set<MultipartFile> files,
                             Model model) {
    
        List<String> remarks = new ArrayList<>();
        cardsUtilities.checkTitle(facultyTitle, remarks, facultyService);
        
        if(!remarks.isEmpty()) {
        
            model.addAttribute("remarks", remarks);
    
            log.info("Failed process of creating a faculty card");
            
            return "/add_card_menu";
        }
        
        if(user.isAdmin())
            facultyService.create(facultyTitle, universityService.findByAbbreviation(universityAbbreviation), teachersTitles, files, user.isAdmin());
        else
            creatingCardApplicationService.create(
                    CardType.FACULTY,
                    facultyService.create(facultyTitle, universityService.findByAbbreviation(universityAbbreviation), teachersTitles, files, user.isAdmin()),
                    user,
                    dateSending
            );
    
        log.info("Success process of creating a faculty card");
        
        return createCard(model);
    }
    
    @PostMapping("/add/teacher")
    @Retryable(maxAttempts = 5, value = Exception.class, backoff = @Backoff(delay = 300, multiplier = 1.5))
    public String addTeacher(@AuthenticationPrincipal User user,
                             @RequestParam("firstName") String firstname,
                             @RequestParam("lastName") String lastname,
                             @RequestParam(value = "patronymic", required = false) String patronymic,
                             @RequestParam("universities") Set<String> universitiesAbbreviation,
                             @RequestParam("faculties") Set<String> facultiesTitles,
                             @RequestParam("date") String dateSending,
                             @RequestParam("files") Set<MultipartFile> files,
                             @RequestParam("excuses") String excuses,
                             Model model) {
    
        List<String> remarks = new ArrayList<>();
        usersUtilities.checkNames(firstname, lastname, patronymic, remarks);
        
        if(!remarks.isEmpty()) {
            
            model.addAttribute("remarks", remarks);
    
            log.info("Failed process of creating a teacher card");
            
            return "redirect:/cards/add";
        }
    
        if(user.isAdmin())
            teacherService.create(firstname, lastname, patronymic, excuses, universitiesAbbreviation, facultiesTitles, files, user.isAdmin());
        else
            creatingCardApplicationService.create(
                    CardType.TEACHER,
                    teacherService.create(firstname, lastname, patronymic, excuses, universitiesAbbreviation, facultiesTitles, files, user.isAdmin()),
                    user,
                    dateSending
            );
    
        log.info("Success process of creating a teacher card");
        
        return createCard(model);
    }
    
    @GetMapping("/get")
    @Retryable(maxAttempts = 5, value = Exception.class, backoff = @Backoff(delay = 300, multiplier = 1.5))
    public String getCard(@AuthenticationPrincipal User user,
                          @RequestParam("id") Long cardId,
                          @RequestParam("cardType") String cardType,
                          Model model) {
    
        Card card;
        Set<Long> collection = listUtilities.getCardsId(user, cardType);
    
        switch (CardType.valueOf(cardType)) {
            case UNIVERSITY -> model.addAttribute("university", card = universityService.findById(cardId));
            case DORMITORY -> model.addAttribute("dormitory", card = dormitoryService.findById(cardId));
            case FACULTY -> model.addAttribute("faculty", card = facultyService.findById(cardId));
            case TEACHER -> model.addAttribute("teacher", card = teacherService.findById(cardId));
            default -> card = null;
        }
    
        if (Objects.nonNull(card))
            model.addAttribute("numbers", listUtilities.getNumbers(card.getPhotos()));
        
        model.addAttribute("user", user);
        model.addAttribute("estimated", Objects.nonNull(card) && card.containsAssessor(user));
        model.addAttribute("isAdmin", Objects.nonNull(user) && user.isAdmin());
        model.addAttribute("inCollection", Objects.nonNull(card) && collection.contains(card.getId()));
    
        log.info(String.format("Request to receive a card view. Type: %s, ID: %s", cardType, cardId.toString()));
        
        return "/".concat(cardType.toLowerCase()).concat("_card");
    }
    
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/edit")
    @Retryable(maxAttempts = 5, value = Exception.class, backoff = @Backoff(delay = 300, multiplier = 1.5))
    public String editCard(@AuthenticationPrincipal User user,
                           @RequestParam("title") String cardTitle,
                           @RequestParam("type") String cardType,
                           @RequestParam("id") Long cardId,
                           @RequestParam(value = "excuses", required = false) String excuses,
                           @RequestParam(value = "files", required = false) Set<MultipartFile> files,
                           Model model) {
        
        switch (CardType.valueOf(cardType)) {
            case UNIVERSITY -> universityService.edit(cardId, cardTitle, files);
            case DORMITORY -> dormitoryService.edit(cardId, cardTitle, files);
            case FACULTY -> facultyService.edit(cardId, cardTitle, files);
            case TEACHER -> teacherService.edit(cardId, cardTitle, files, excuses);
            default -> {}
            
        }
    
        log.info(String.format("Request to receive a card display. Type: %s, ID: %s", cardType, cardId.toString()));
        
        return getCard(user, cardId, cardType, model);
    }
    
    @GetMapping("/collection")
    @Retryable(maxAttempts = 5, value = Exception.class, backoff = @Backoff(delay = 300, multiplier = 1.5))
    public String userCollectionCards(@AuthenticationPrincipal User user,
                                      Model model) {
        
        model.addAttribute("cards", cardCollectionService.findCollectionByUser(user));
        model.addAttribute("listName", "Your cards collection");
        model.addAttribute("collection", true);
        model.addAttribute("isAdmin", user.isAdmin());
        
        log.info(String.format("A collection of user cards has been requested (user ID: %s)", user.getId().toString()));
        
        return "cards_list";
    }
    
    @PostMapping("/collection/add")
    @Retryable(maxAttempts = 5, value = Exception.class, backoff = @Backoff(delay = 300, multiplier = 1.5))
    public String collectionAdd(@AuthenticationPrincipal User user,
                                @RequestParam("cardId") Long cardId,
                                @RequestParam("cardType") String cardType,
                                @RequestParam("inCollection") Boolean inCollection,
                                HttpServletRequest request,
                                Model model) {
        
        inCollection = Objects.nonNull(inCollection) && inCollection;
        
        if(inCollection)
            cardCollectionService.remove(user, cardId, cardType);
        else
            cardCollectionService.create(user, cardId, cardType);
        
        log.info(String.format("The card has been added to the user's collection (card ID: %s, card type: %s, user ID: %s)",
                cardId.toString(), cardType, user.getId().toString()));
        
        return "redirect:"+ request.getHeader("Referer");
    }
    
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/delete")
    @Retryable(maxAttempts = 5, value = Exception.class, backoff = @Backoff(delay = 300, multiplier = 1.5))
    public String deleteCard(@AuthenticationPrincipal User user,
                             @RequestParam("id") Long cardId,
                             @RequestParam("type") String cardType,
                             Model model) {
    
        switch (CardType.valueOf(cardType)) {
            case UNIVERSITY -> universityService.deleteById(cardId);
            case DORMITORY -> dormitoryService.deleteById(cardId);
            case FACULTY -> facultyService.deleteById(cardId);
            case TEACHER -> teacherService.deleteById(cardId);
            default -> {}
        }
        
        log.info(String.format("Card deletion process (ID: %s, type: %s)", cardId.toString(), cardType));
        
        return cardsList(user, cardType, model);
    }
    
    @Recover
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "An error on the server side or a click on an invalid link")
    public ModelAndView error(Exception exception) {
        
        ModelAndView model = new ModelAndView("Error");
        
        model.addObject("Error",
                String.format(ERROR_MESSAGE, exception.getMessage(), exception.getCause(), supportEmail)
        );
    
        log.warn(String.format("Exception: %s, reason: %s", exception.getMessage(), exception.getCause()));
        
        return model;
    }
}