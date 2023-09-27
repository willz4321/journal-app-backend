package com.facundosuarez.journal.journalapp.models.entity.note;

import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.facundosuarez.journal.journalapp.models.entity.auth.userEntity;

@Document(collection = "notes")
public class notes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String title;
    private String body;
    private List<String> imageUrls;

    @DBRef 
    private userEntity user; // Referencia al usuario dueño de la nota
   
    
    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }
    public String getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getBody() {
        return body;
    }
    public void setBody(String body) {
        this.body = body;
    }
    public userEntity getUser() {
        return user;
    }
    public void setUser(userEntity user) {
        this.user = user;
    }

    
}
