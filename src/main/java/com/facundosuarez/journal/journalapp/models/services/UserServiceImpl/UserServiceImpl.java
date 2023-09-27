package com.facundosuarez.journal.journalapp.models.services.UserServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.facundosuarez.journal.journalapp.models.dao.IAuthDao.IUserDao;
import com.facundosuarez.journal.journalapp.models.entity.auth.userEntity;

@Service
public class UserServiceImpl implements IUserService{

    @Autowired
    private IUserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(IUserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<userEntity> findAll() {
        return (List<userEntity>) userDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public userEntity findByCorreo(String correo) {
       return userDao.findByCorreo(correo);
    }

    @Override
    public userEntity save(userEntity user) {
        // Verifica si la contraseña es nueva o ya está codificada
        String plainPassword = user.getPassword();
        if (plainPassword != null && !plainPassword.startsWith("$2a$")) {
            // La contraseña no está codificada, así que la codificamos
            user.setPasswordEncoded(passwordEncoder, plainPassword);
        }
        return userDao.save(user);
    }

    @Override
    public boolean existsByCorreo(String correo) {
        return userDao.existsByCorreo(correo);
    }

    @Override
    @Transactional(readOnly = true)
    public userEntity findById(String id) {
        return userDao.findById(id).orElse(null);
    }

   
}
