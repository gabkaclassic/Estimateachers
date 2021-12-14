package org.gab.estimateachers.app.services;

import org.gab.estimateachers.app.repositories.system.ApplicationRepository;
import org.gab.estimateachers.app.repositories.system.RequestRepository;
import org.gab.estimateachers.app.utilities.ApplicationsUtilities;
import org.gab.estimateachers.entities.client.Card;
import org.gab.estimateachers.entities.system.Request;
import org.gab.estimateachers.entities.system.RequestType;
import org.gab.estimateachers.entities.system.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service("requestService")
public class RequestService extends ApplicationService<Request, RequestRepository> {
    
    @Autowired
    @Qualifier("requestRepository")
    protected void setApplicationRepository(RequestRepository repository) {
        
        super.setApplicationRepository(repository);
    }
    
    public void create(User user, String date, String textRequest, String type) {
        
        RequestType requestType = RequestType.valueOf(type);
        Request request = new Request(
                user.getOwner(),
                date,
                textRequest,
                requestType
        );
        
        applicationRepository.save(request);
    }
    
    public List<Request> findAllCard() {
        
        return applicationRepository.findAllType(RequestType.CHANGING_CARDS.toString());
    }
    
    public List<Request> findAllService() {
        
        return applicationRepository.findAllType(RequestType.OPERATIONS_SERVICE.toString());
    }
}
