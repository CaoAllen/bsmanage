package com.spring.example.service;

import java.util.ArrayList;  
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.security.core.GrantedAuthority;  
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;  
import org.springframework.security.core.userdetails.UserDetailsService;  
import org.springframework.security.core.userdetails.UsernameNotFoundException;  
import org.springframework.stereotype.Service;  
import org.springframework.transaction.annotation.Transactional;

import com.spring.example.domain.User;
import com.spring.example.domain.UserProfile;  
   
   
@Service("customUserDetailsService")  
public class CustomUserDetailsService implements UserDetailsService{  
	private static final Logger log = LoggerFactory.getLogger(CustomUserDetailsService.class);
	
    @Autowired  
    private UserSerivce userService;  
       
    @Transactional(readOnly=true)  
    public UserDetails loadUserByUsername(String name)  
            throws UsernameNotFoundException { 
//    	UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//    	log.debug("userDetails : "+userDetails);  
//    	if(userDetails !=null && userDetails.getUsername().equals(name)){
//    		return userDetails;
//    	}
        User user = userService.findByName(name);  
        log.debug("User : "+user);  
        if(user == null){  
        	log.error("User not found");  
            throw new UsernameNotFoundException("Username not found");  
        }  
        return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(),   
             user.getState().equals("Active"), true, true, true, getGrantedAuthorities(user));  
    }  
   
       
    private List<GrantedAuthority> getGrantedAuthorities(User user){  
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();  
           
        for(UserProfile userProfile : user.getUserProfiles()){  
        	log.debug("UserProfile : "+userProfile);  
            authorities.add(new SimpleGrantedAuthority("ROLE_"+userProfile.getType()));  
        }  
        log.debug("authorities :"+authorities);  
        return authorities;  
    }  
       
}  
