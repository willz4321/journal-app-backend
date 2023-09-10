package com.facundosuarez.journal.journalapp.controllers;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.facundosuarez.journal.journalapp.models.entity.auth.user;
import com.facundosuarez.journal.journalapp.models.entity.security.jwt;
import com.facundosuarez.journal.journalapp.models.services.UserServiceImpl.IUserService;

import jakarta.validation.Valid;


@Validated
@CrossOrigin(origins = { "http://localhost:3000" })
@RestController
@RequestMapping("/auth")
public class journalRestController {

    @Autowired
    private IUserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private jwt jwtInstance;
    
    @GetMapping("/users")
    public ResponseEntity<List<user>> index() {
        List<user> users = userService.findAll();
        return ResponseEntity.ok(users);
    }
    

    @PostMapping("/register")
     public ResponseEntity<Object> create(@Valid @RequestBody user newUser) {
         
          //Verifico que todos los datos existan
            if (newUser.getCorreo().isEmpty() || newUser.getCorreo().isBlank() || !newUser.getCorreo().matches("@") || !Objects.nonNull(newUser.getCorreo())
                || newUser.getPassword().isEmpty() || newUser.getPassword().isBlank() || !Objects.nonNull(newUser.getPassword())
                ||newUser.getUserName().isEmpty() || newUser.getUserName().isBlank() || !Objects.nonNull(newUser.getUserName())) {  
            // La validación falló, hay campos nulos o vacíos
            
               return  ResponseEntity.status(HttpStatus.CONFLICT).body("campos vacios o nulos");
            } 

        // Verificar si ya existe un usuario con el mismo correo
        if (userService.existsByCorreo(newUser.getCorreo())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body("El correo ya está registrado. Por favor, use otro correo.");
          }
        
        // Si no existe, guardar el nuevo usuario
        user savedUser = userService.save(newUser);
        
        if (savedUser != null) {

                //Generar y Almacena el token en el objeto User
                savedUser.setToken(jwtInstance.generateToken(savedUser));

            return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Hubo un error al registrar el usuario.");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody user loginUser) {
    // Buscar al usuario por su correo en la base de datos
    user existingUser = userService.findByCorreo(loginUser.getCorreo());
    
    if (existingUser == null) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body("Credenciales inválidas. Usuario incorrecto");
    }
     
    // Verificar la contraseña proporcionada con la contraseña almacenada
    if (passwordEncoder.matches(loginUser.getPassword(), existingUser.getPassword())) {

        // Devuelve el token en la respuesta
        existingUser.setToken(jwtInstance.generateToken(existingUser));
 
           // Devuelve una respuesta adecuada, por ejemplo, un objeto User sin la contraseña
        existingUser.setPasswordEncoded(passwordEncoder, loginUser.getPassword()); 

        return ResponseEntity.ok(existingUser);
    } else {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body("Credenciales inválidas. Contraseña incorrecta");
    }
}
}
