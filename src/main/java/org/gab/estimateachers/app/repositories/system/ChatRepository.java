package org.gab.estimateachers.app.repositories.system;

import org.gab.estimateachers.entities.system.Chat;
import org.gab.estimateachers.entities.system.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends CrudRepository<Chat, Long>,
        JpaRepository<Chat, Long> {

}
