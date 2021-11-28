package org.gab.estimateachers.app.repositories.system;

import org.gab.estimateachers.entities.system.CreatingCardApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("creatingCardApplicationRepository")
public interface CreatingCardApplicationRepository extends ApplicationRepository<CreatingCardApplication> {

}
