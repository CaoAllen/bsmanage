package com.spring.example.api;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.example.Constants;

@RestController
@RequestMapping(value = Constants.URI_API)
public class LoginController {
	private static final Logger log = LoggerFactory
            .getLogger(LoginController.class);
    
    /**
     * Get the authenticated user info.
     * 
     * @param principal
     * @return 
     */
    @RequestMapping("/login")
    public ResponseEntity<Map<String, Object>> user(Principal user) {
        Map<String, Object> map = new HashMap<>();
        map.put("username", user.getName());
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
    
    @RequestMapping("/context")
    public ResponseEntity<Map<String, Object>> loadContext() {
    	UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Map<String, Object> map = new HashMap<>();
        map.put("username", userDetails.getUsername());
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
