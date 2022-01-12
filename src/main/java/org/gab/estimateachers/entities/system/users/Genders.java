package org.gab.estimateachers.entities.system.users;

import com.sun.istack.NotNull;

public enum Genders {

    MAN("Man"), WOMAN("Woman"), NO("No");

    private String title;
    
    Genders(String title) {
        
        this.title = title;
    }
    
    public String toString() {
       
       return title;
    }
}
