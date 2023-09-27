package com.facundosuarez.journal.journalapp.models.dao.IAuthDao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.facundosuarez.journal.journalapp.models.entity.auth.userEntity;

public interface IUserDao extends CrudRepository<userEntity, String>{

    boolean existsByCorreo(String correo);
    userEntity findByCorreo(String correo);
   //Optional<userEntity> findByCcorreOptional(String correo);
}
