package com.facundosuarez.journal.journalapp.models.services.UserServiceImpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.facundosuarez.journal.journalapp.models.entity.auth.user;

@Service
public interface IUserService {

    public List<user> findAll();

    public user findByCorreo(String correo);

    public user save(user user);

   public boolean existsByCorreo(String correo);
}
