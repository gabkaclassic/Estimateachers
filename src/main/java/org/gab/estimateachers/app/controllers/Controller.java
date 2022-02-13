package org.gab.estimateachers.app.controllers;

import org.springframework.retry.annotation.Recover;

public abstract class Controller {
    
    @Recover
    public String exception() {
        
        return "redirect:/error";
    }
}
