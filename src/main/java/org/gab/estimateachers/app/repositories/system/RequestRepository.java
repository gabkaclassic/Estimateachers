package org.gab.estimateachers.app.repositories.system;

import org.gab.estimateachers.entities.system.Request;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("requestRepository")
public interface RequestRepository extends ApplicationRepository<Request> {
    
    @Query(
            value = "select * from requests where request_type = :type",
            nativeQuery = true
    )
    List<Request> findAllType(@Param("type") String type);
}
