package com.facundosuarez.journal.journalapp.models.dao.IAuthDao;

import org.springframework.data.repository.CrudRepository;

import com.facundosuarez.journal.journalapp.models.entity.auth.user;

public interface IUserDao extends CrudRepository<user, String>{

    boolean existsByCorreo(String correo);
    user findByCorreo(String correo);
}
