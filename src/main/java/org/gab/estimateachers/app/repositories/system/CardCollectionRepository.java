package org.gab.estimateachers.app.repositories.system;

import org.gab.estimateachers.entities.client.CardType;
import org.gab.estimateachers.entities.system.users.CardCollection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository("cardCollectionRepository")
public interface CardCollectionRepository extends CrudRepository<CardCollection, Long>,
        JpaRepository<CardCollection, Long>  {
    
    @Query(value = "select collection from CardCollection collection where collection.userId = :userId")
    List<CardCollection> findCollectionByUser(@Param("userId") Long id);
    
    @Transactional
    @Modifying
    @Query(value = "delete from CardCollection c where c.userId = :userId and c.cardId = :cardId and c.cardType = :cardType")
    void remove(@Param("userId") Long id, @Param("cardId") Long cardId, @Param("cardType") CardType cardType);
    
    @Query(value = "select collection from CardCollection collection where collection.userId = :userId and collection.cardType = :cardType")
    List<CardCollection> findByUserAndCardType(@Param("userId") Long cardId, @Param("cardType") CardType cardType);
}
