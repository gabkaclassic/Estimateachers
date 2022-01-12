package org.gab.estimateachers.app.repositories.system;

import org.gab.estimateachers.entities.system.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userRepository")
public interface UserRepository extends CrudRepository<User, Long>,
        JpaRepository<User, Long> {
    
    @Query(
            value = "select u from User u where lower(u.username) like lower(concat('%', :username, '%'))"
    )
    List<User> findByUsernamePattern(@Param("username") String pattern);
    
    User findByUsername(String username);
    
    boolean existsByUsername(String username);
    
    boolean existsByEmail(String email);
}
