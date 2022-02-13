package org.gab.estimateachers.app.services;

import org.gab.estimateachers.app.repositories.system.CommentRepository;
import org.gab.estimateachers.entities.system.discussions.Comment;
import org.gab.estimateachers.entities.system.discussions.Discussion;
import org.gab.estimateachers.entities.system.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

@org.springframework.stereotype.Service("commentService")
public class CommentService implements Service<Comment> {
    
    @Autowired
    @Qualifier("discussionService")
    private DiscussionService discussionService;
    
    @Autowired
    @Qualifier("commentRepository")
    CommentRepository commentRepository;
    
    public Comment findById(Long id) {
        
        return commentRepository.getOne(id);
    }
    
    public void save(Comment comment) {
        
        commentRepository.save(comment);
    }
    
    public List<Comment> findAll() {
        
        return commentRepository.findAll();
    }
    
    public void deleteById(Long id) {
        
        commentRepository.deleteById(id);
    }
    
    public void create(String text, String dateSending, Long discussionId, User user) {
        
        Discussion discussion = discussionService.findById(discussionId);
        Comment comment = new Comment(text, dateSending, user);
        
        discussion.addComment(comment);
        comment.setDiscussion(discussion);
        commentRepository.save(comment);
        discussionService.save(discussion);
    }
    
    public void like(Long commentId, User user) {
        
        Comment comment = commentRepository.getOne(commentId);
        comment.like(user);
        commentRepository.save(comment);
    }
    
    public void dislike(Long commentId, User user) {
        
        Comment comment = commentRepository.getOne(commentId);
        comment.dislike(user);
        commentRepository.save(comment);
    }
    
    public List<Comment> findByTextPattern(String text, Long discussionId) {
        
        return commentRepository.findByTextPattern(text, discussionId);
    }
    
    public List<Comment> findByAuthorUsername(String username, Long discussionId) {
        
        return commentRepository.findByAuthorUsername(username, discussionId);
    }
    
    public List<Comment> findByDiscussionId(Long discussionId) {
        
        return commentRepository.findByDiscussionId(discussionId);
    }
    
    public List<Comment> findByDiscussionIdOrderByRatingDesc(Long discussionId) {
        
        return commentRepository.findByDiscussionIdOrderByRatingDesc(discussionId);
    }
    
    public List<Comment> findByDiscussionIdOrderByRatingAsc(Long discussionId) {
        
        return commentRepository.findByDiscussionIdOrderByRatingAsc(discussionId);
    }
}
