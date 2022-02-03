package org.gab.estimateachers.app.repositories.system;

import org.gab.estimateachers.entities.system.discussions.Discussion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface DiscussionRepository extends JpaRepository<Discussion, Long>,
        CrudRepository<Discussion, Long> {
    
}
