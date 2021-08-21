package org.gab.estimateachers.app.repositories.system;

import org.gab.estimateachers.entities.system.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("userRepository")
public interface UserRepository extends CrudRepository<User, Long>,
        JpaRepository<User, Long> {

    User findByUsername(String username);
    
    boolean existsByUsername(String username);
    
    boolean existsByEmail(String email);
}
