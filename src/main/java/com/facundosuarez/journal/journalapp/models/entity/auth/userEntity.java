package com.facundosuarez.journal.journalapp.models.entity.auth;

import java.io.Serializable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;



@Document(collection = "users")
public class userEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @NotBlank(message = "El campo username es obligatorio")
    @NotNull
    @NotEmpty
    private String userName;
    @NotBlank(message = "El campo Email es obligatorio")
    @NotNull
    @Email
    @NotEmpty
    private String correo;
    @NotBlank(message = "El campo password es obligatorio")
    @NotNull
    @NotEmpty
    private String password;

    private String token; 
    
 
    public String getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPasswordEncoded(PasswordEncoder passwordEncoder, String plainPassword) {
        this.password = passwordEncoder.encode(plainPassword);
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

   

}
