package org.gab.estimateachers.app.repositories.system;

import org.gab.estimateachers.entities.system.discussions.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long>,
        JpaRepository<Comment, Long> {
    
    @Query(
            value = "select c from Comment c where c.discussion.id = :discussionId and lower(c.text) like lower(concat('%', :text, '%')) order by c.date desc"
    )
    List<Comment> findByTextPattern(@Param("text") String text, @Param("discussionId") Long discussionId);
    
    @Query(
            value = "select c from Comment c where c.discussion.id = :discussionId and lower(c.author.username) like lower(concat('%', :username, '%')) order by c.date desc"
    )
    List<Comment> findByAuthorUsername(@Param("username") String username, @Param("discussionId") Long discussionId);
    
    @Query(
            value = "select c from Comment c where c.discussion.id = :discussionId order by c.date"
    )
    List<Comment> findByDiscussionId(@Param("discussionId") Long discussionId);
    
    @Query(
            value = "select c from Comment c where c.discussion.id = :discussionId order by c.rating"
    )
    List<Comment> findByDiscussionIdOrderByRatingAsc(@Param("discussionId") Long discussionId);
    
    @Query(
            value = "select c from Comment c where c.discussion.id = :discussionId order by c.rating desc"
    )
    List<Comment> findByDiscussionIdOrderByRatingDesc(@Param("discussionId") Long discussionId);
}
