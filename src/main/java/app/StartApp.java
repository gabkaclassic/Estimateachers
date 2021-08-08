package app;

import lombok.extern.log4j.Log4j;

@Log4j
public class StartApp {
    
    public static void main(String[] args) {
    
        StartApp app = new StartApp();
        
        log.info("App started");
        
        app.start();
        
        log.info("App stopped");
    }
    
    private void start() {
    
    
    }
    
}
