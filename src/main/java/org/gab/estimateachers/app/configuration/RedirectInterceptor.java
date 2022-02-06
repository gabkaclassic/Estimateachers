package org.gab.estimateachers.app.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@Slf4j
public class RedirectInterceptor extends HandlerInterceptorAdapter {
    
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler,
                           ModelAndView modelAndView) {
        
        if(Objects.nonNull(modelAndView)) {
            
            String query = request.getQueryString();
            String arguments = (Objects.nonNull(query)) ? query : "";
            String url = request.getRequestURI().concat("?").concat(arguments);
            
            response.setHeader("Turbolinks-Location", url);
            
            log.info("Redirected request from Turbolinks");
        }
    }
}
