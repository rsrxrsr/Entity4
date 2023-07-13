package com.rsr.logger;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebFilter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;

import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 
@WebFilter(urlPatterns = {"/*", "/restapi/*"})
public class LogFilter implements Filter {
    private final static Logger logger = LoggerFactory.getLogger("AppLog"); 
    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain)
        throws IOException, ServletException
    {
    	HttpServletRequest req = (HttpServletRequest)request;
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(req);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper((HttpServletResponse) response);
        logger.info(String.format("*** Request User: %s :METHOD: %s :URI: %s", getUsername(req.getUserPrincipal()), req.getMethod(), req.getRequestURI()));    
        chain.doFilter(requestWrapper, responseWrapper);
        logger.info("*** Response Status Code: " + ((HttpServletResponse) response).getStatus());        
        if (req.getMethod().contentEquals("POST")) {
        	logger.info("*** ResponseBody:" + getResponsePayload(responseWrapper));
        }
    	responseWrapper.copyBodyToResponse();
    }

    public String getResponsePayload(ContentCachingResponseWrapper responseWrapper)
    	throws UnsupportedEncodingException
    {    	
    	return new String(responseWrapper.getContentAsByteArray(), responseWrapper.getCharacterEncoding());  
    }
    
    public String getUsername(Principal principal ) {
    	return principal == null ? "Invitado" : principal.getName();
    }
        
}