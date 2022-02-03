package org.gab.estimateachers.app.controllers.chats;

import org.gab.estimateachers.app.repositories.system.DiscussionRepository;
import org.gab.estimateachers.app.services.DiscussionService;
import org.gab.estimateachers.entities.client.Card;
import org.gab.estimateachers.entities.client.CardType;
import org.gab.estimateachers.entities.system.discussions.Discussion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/discussions")
public class DiscussionController {
    
    @Autowired
    @Qualifier("discussionService")
    private DiscussionService discussionService;
    
    @GetMapping("/get")
    public String chatList(@RequestParam("discussionId") Long discussionId,
                           Model model) {
        
        Discussion discussion = discussionService.findById(discussionId);
        model.addAttribute("discussion", discussion);
        model.addAttribute("comments", discussion.getComments());
        
        return "/chat";
    }
}
