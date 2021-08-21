package org.gab.estimateachers.app.repositories.system;

import org.gab.estimateachers.entities.system.Message;
import org.gab.estimateachers.entities.system.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends CrudRepository<Message, Long>,
        JpaRepository<Message, Long> {

}
