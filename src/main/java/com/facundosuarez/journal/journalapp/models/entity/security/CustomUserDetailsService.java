package com.facundosuarez.journal.journalapp.models.entity.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.facundosuarez.journal.journalapp.models.dao.IAuthDao.IUserDao;
import com.facundosuarez.journal.journalapp.models.entity.auth.userEntity;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private IUserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        userEntity userDate = userDao.findByCorreo(email);
    
        if (userDate == null) {
            throw new UsernameNotFoundException("Usuario no encontrado con el correo electrónico: " + email);
        }
    
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
    
        return new CustomUserDetails(userDate.getCorreo(), userDate.getPassword(), true, true, true, true, authorities, userDate.getId(), userDate.getUserName());
    }
    
}

