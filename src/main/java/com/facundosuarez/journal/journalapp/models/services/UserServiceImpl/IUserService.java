package com.facundosuarez.journal.journalapp.models.services.UserServiceImpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.facundosuarez.journal.journalapp.models.entity.auth.userEntity;

@Service
public interface IUserService {

    public List<userEntity> findAll();

    public userEntity findByCorreo(String correo);
    
    public userEntity findById(String id);

    public userEntity save(userEntity user);

   public boolean existsByCorreo(String correo);
}
