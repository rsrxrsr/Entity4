package com.rsr.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebFilter;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 
@WebFilter(urlPatterns = {"/usuario/*", "/restapi/*"})
public class LogFilter implements Filter {
    private final static Logger logger = LoggerFactory.getLogger("AppLog"); 
    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain)
        throws IOException, ServletException
    {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        logger.info("Request User: " + req.getUserPrincipal() + " :METHOD: " + req.getMethod() +" :URI: "+ req.getRequestURI());    
        chain.doFilter(request, response);
        logger.info("Response Status Code: " + res.getStatus());
    }
}