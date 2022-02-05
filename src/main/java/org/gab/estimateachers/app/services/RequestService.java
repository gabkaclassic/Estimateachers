package org.gab.estimateachers.app.services;

import org.gab.estimateachers.app.repositories.system.RequestRepository;
import org.gab.estimateachers.app.utilities.FilesUtilities;
import org.gab.estimateachers.app.utilities.RegistrationType;
import org.gab.estimateachers.entities.system.applications.Request;
import org.gab.estimateachers.entities.system.applications.RequestType;
import org.gab.estimateachers.entities.system.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

@Service("requestService")
public class RequestService extends ApplicationService<Request, RequestRepository> {
    
    @Autowired
    @Qualifier("filesUtilities")
    private FilesUtilities filesUtilities;
    
    @Autowired
    @Qualifier("requestRepository")
    protected void setApplicationRepository(RequestRepository repository) {
        
        super.setApplicationRepository(repository);
    }
    
    public void apply(Request request) {
    
        mailService.applyRequest(request.getStudent()
                .getAccount());
    
        applicationRepository.delete(request);
    }
    
    public void reject(Long requestId, String reason) {
        
        Request request = findById(requestId);
        mailService.rejectRequest(request.getStudent().getAccount(), reason);
        
        deleteById(requestId);
    }
    
    public void create(User user, String date, String textRequest, String type, Set<MultipartFile> files) {
        
        RequestType requestType = RequestType.valueOf(type);
        
        Request request = new Request(
                user.getOwner(),
                date,
                textRequest,
                requestType
        );
        files.stream()
                .map(f -> filesUtilities.registrationFile(f, RegistrationType.OTHER))
                .forEach(request::addPhoto);
        
        applicationRepository.save(request);
    }
    
    public List<Request> findAllCard() {
        
        return applicationRepository.findAllType(RequestType.CHANGING_CARDS.toString());
    }
    
    public List<Request> findAllService() {
        
        return applicationRepository.findAllType(RequestType.OPERATIONS_SERVICE.toString());
    }
}
