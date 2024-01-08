package com.facundosuarez.journal.journalapp.models.entity.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;


public class CustomUserDetails extends User {
    private String uid;
    private String name;

    // constructor, getters and setters...

    public CustomUserDetails(String username, String password, 
                             boolean enabled, boolean accountNonExpired, 
                             boolean credentialsNonExpired, boolean accountNonLocked, 
                             Collection<? extends GrantedAuthority> authorities, String uid, String name) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.uid = uid;
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }
}
