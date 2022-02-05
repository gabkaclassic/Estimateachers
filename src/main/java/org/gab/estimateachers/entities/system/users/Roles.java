package org.gab.estimateachers.entities.system.users;

import org.springframework.security.core.GrantedAuthority;

public enum Roles implements GrantedAuthority {
    
    USER, ADMIN, MODERATOR, LOCKED;
    
    public String getAuthority() {
        
        return name();
    }
}
