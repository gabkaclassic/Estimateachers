package org.gab.estimateachers.app.controllers.chats;

import org.gab.estimateachers.app.services.CommentService;
import org.gab.estimateachers.app.services.DiscussionService;
import org.gab.estimateachers.entities.system.discussions.Comment;
import org.gab.estimateachers.entities.system.discussions.Discussion;
import org.gab.estimateachers.entities.system.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/discussions")
public class DiscussionController extends org.gab.estimateachers.app.controllers.Controller {
    
    @Autowired
    @Qualifier("discussionService")
    private DiscussionService discussionService;
    
    @Autowired
    @Qualifier("commentService")
    private CommentService commentService;
    
    @GetMapping(value = {
            "/comment/send",
            "/comment/like",
            "/comment/dislike",
            "/comment/search/text",
            "/comment/search/author"
    })
    @Retryable(maxAttempts = 5, value = Exception.class, backoff = @Backoff(delay = 300, multiplier = 1.5))
    public String plug(HttpServletRequest request) {
        
        return "redirect:" + request.getHeader("Referer");
    }
    
    @GetMapping("/get/{discussionId}")
    @Retryable(maxAttempts = 5, value = Exception.class, backoff = @Backoff(delay = 300, multiplier = 1.5))
    public String chatList(@AuthenticationPrincipal User user,
                           @PathVariable("discussionId") Long discussionId,
                           Model model) {
        
        Discussion discussion = discussionService.findById(discussionId);
        model.addAttribute("discussion", discussion);
        model.addAttribute("comments", commentService.findByDiscussionId(discussionId));
        model.addAttribute("isAdmin", user.isAdmin());
        
        return "/discussion";
    }
    
    @GetMapping("/sorted/asc/{discussionId}")
    @Retryable(maxAttempts = 5, value = Exception.class, backoff = @Backoff(delay = 300, multiplier = 1.5))
    public String chatListOrderAsc(@AuthenticationPrincipal User user,
                           @PathVariable("discussionId") Long discussionId,
                           Model model) {
        
        Discussion discussion = discussionService.findById(discussionId);
        model.addAttribute("discussion", discussion);
        model.addAttribute("comments", commentService.findByDiscussionIdOrderByRatingAsc(discussionId));
        model.addAttribute("isAdmin", user.isAdmin());
        
        return "/discussion";
    }
    
    @GetMapping("/sorted/desc/{discussionId}")
    @Retryable(maxAttempts = 5, value = Exception.class, backoff = @Backoff(delay = 300, multiplier = 1.5))
    public String chatListOrderDesc(@AuthenticationPrincipal User user,
                                   @PathVariable("discussionId") Long discussionId,
                                   Model model) {
        
        Discussion discussion = discussionService.findById(discussionId);
        model.addAttribute("discussion", discussion);
        model.addAttribute("comments", commentService.findByDiscussionIdOrderByRatingDesc(discussionId));
        model.addAttribute("isAdmin", user.isAdmin());
        
        return "/discussion";
    }
    
    @PostMapping("/comment/send")
    @Retryable(maxAttempts = 5, value = Exception.class, backoff = @Backoff(delay = 300, multiplier = 1.5))
    public String sendComment(@AuthenticationPrincipal User user,
                              @RequestParam("text") String text,
                              @RequestParam("date") String dateSending,
                              @RequestParam("discussionId") Long discussionId) {
        
        commentService.create(text, dateSending, discussionId, user);
        
        return "redirect:/discussions/get/" + discussionId;
    }
    
    @PostMapping("/comment/search/text")
    @Retryable(maxAttempts = 5, value = Exception.class, backoff = @Backoff(delay = 300, multiplier = 1.5))
    public String searchByText(@AuthenticationPrincipal User user,
                               @RequestParam("text") String text,
                               @RequestParam("discussionId") Long discussionId,
                               Model model) {
    
        Discussion discussion = discussionService.findById(discussionId);
        List<Comment> comments = commentService.findByTextPattern(text, discussionId);
        model.addAttribute("discussion", discussion);
        model.addAttribute("comments", (Objects.nonNull(comments) && !comments.isEmpty()) ? comments :
                commentService.findByDiscussionId(discussionId));
        model.addAttribute("text", text);
        model.addAttribute("isAdmin", user.isAdmin());
        
        return "/discussion";
    }
    
    @PostMapping("/comment/search/author")
    @Retryable(maxAttempts = 5, value = Exception.class, backoff = @Backoff(delay = 300, multiplier = 1.5))
    public String searchByAuthor(@AuthenticationPrincipal User user,
                                 @RequestParam("username") String username,
                                 @RequestParam("discussionId") Long discussionId,
                                 Model model) {
        
        Discussion discussion = discussionService.findById(discussionId);
        List<Comment> comments = commentService.findByAuthorUsername(username, discussionId);
        model.addAttribute("discussion", discussion);
        model.addAttribute("comments", (Objects.nonNull(comments) && !comments.isEmpty()) ? comments :
                commentService.findByDiscussionId(discussionId));
        model.addAttribute("username", username);
        model.addAttribute("isAdmin", user.isAdmin());
        
        return "/discussion";
    }
    
    @PostMapping("/comment/like")
    @Retryable(maxAttempts = 5, value = Exception.class, backoff = @Backoff(delay = 300, multiplier = 1.5))
    public String likeComment(@AuthenticationPrincipal User user,
                              @RequestParam("commentId") Long commentId,
                              @RequestParam("discussionId") Long discussionId) {
        
        commentService.like(commentId, user);
    
        return "redirect:/discussions/get/" + discussionId;
    }
    
    @PostMapping("/comment/dislike")
    @Retryable(maxAttempts = 5, value = Exception.class, backoff = @Backoff(delay = 300, multiplier = 1.5))
    public String dislikeComment(@AuthenticationPrincipal User user,
                                 @RequestParam("commentId") Long commentId,
                                 @RequestParam("discussionId") Long discussionId) {
        
        commentService.dislike(commentId, user);
    
        return "redirect:/discussions/get/" + discussionId;
    }
}
