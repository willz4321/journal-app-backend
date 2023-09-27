package com.facundosuarez.journal.journalapp.models.services.NotesServiceImpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.facundosuarez.journal.journalapp.models.entity.note.notes;

@Service
public interface INotesService {
    
    public List<notes> findAll();

    public notes save(notes note);
    
    public notes findById(String id);

    public void delete(notes note);
}
