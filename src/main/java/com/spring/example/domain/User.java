package com.spring.example.domain;

import java.util.HashSet;  
import java.util.Set;  
   
import javax.persistence.Column;  
import javax.persistence.Entity;  
import javax.persistence.FetchType;  
import javax.persistence.GeneratedValue;  
import javax.persistence.GenerationType;  
import javax.persistence.Id;  
import javax.persistence.JoinColumn;  
import javax.persistence.JoinTable;  
import javax.persistence.ManyToMany;  
import javax.persistence.Table;

import com.spring.example.model.State;  
   
@Entity  
@Table(name="APP_USER")  
public class User {  
   
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)  
    private int id;  
   
    @Column(name="NAME", unique=true, nullable=false)  
    private String name;  
       
    @Column(name="PASSWORD", nullable=false)  
    private String password;  
           
    @Column(name="EMAIL", nullable=false)  
    private String email;  
   
    @Column(name="STATE", nullable=false)  
    private String state = State.ACTIVE.getState();  
   
    @ManyToMany(fetch = FetchType.EAGER)  
    @JoinTable(name = "APP_USER_USER_PROFILE",   
             joinColumns = { @JoinColumn(name = "USER_ID") },   
             inverseJoinColumns = { @JoinColumn(name = "USER_PROFILE_ID") })  
    private Set<UserProfile> userProfiles = new HashSet<UserProfile>();  
   
    public int getId() {  
        return id;  
    }  
   
    public void setId(int id) {  
        this.id = id;  
    }  
   
    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {  
        return password;  
    }  
   
    public void setPassword(String password) {  
        this.password = password;  
    }  
   
    public String getEmail() {  
        return email;  
    }  
   
    public void setEmail(String email) {  
        this.email = email;  
    }  
   
    public String getState() {  
        return state;  
    }  
   
    public void setState(String state) {  
        this.state = state;  
    }  
   
    public Set<UserProfile> getUserProfiles() {  
        return userProfiles;  
    }  
   
    public void setUserProfiles(Set<UserProfile> userProfiles) {  
        this.userProfiles = userProfiles;  
    }  
   
    @Override  
    public int hashCode() {  
        final int prime = 31;  
        int result = 1;  
        result = prime * result + id;  
        result = prime * result + ((name == null) ? 0 : name.hashCode());  
        return result;  
    }  
   
    @Override  
    public boolean equals(Object obj) {  
        if (this == obj)  
            return true;  
        if (obj == null)  
            return false;  
        if (!(obj instanceof User))  
            return false;  
        User other = (User) obj;  
        if (id != other.id)  
            return false;  
        if (name == null) {  
            if (other.name != null)  
                return false;  
        } else if (!name.equals(other.name))  
            return false;  
        return true;  
    }  
   
    @Override  
    public String toString() {  
        return "User [id=" + id + ", name=" + name + ", password=" + password  
                + ", email=" + email + ", state=" + state + ", userProfiles=" + userProfiles +"]";  
    }  
   
       
}  
