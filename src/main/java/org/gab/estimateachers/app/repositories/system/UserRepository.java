package org.gab.estimateachers.app.repositories.system;

import org.gab.estimateachers.entities.system.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long>, JpaRepository<User, Long> {

    User findByLogin(String login);
    
    boolean existsByLogin(String login);
    
    boolean existsByEmail(String email);
}
