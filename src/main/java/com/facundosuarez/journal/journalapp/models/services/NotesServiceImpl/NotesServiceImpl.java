package com.facundosuarez.journal.journalapp.models.services.NotesServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.facundosuarez.journal.journalapp.models.dao.INotesDao.INoteDao;
import com.facundosuarez.journal.journalapp.models.entity.note.notes;

@Service
public class NotesServiceImpl implements INotesService{

    @Autowired
    private INoteDao noteDao;

    
    public NotesServiceImpl(INoteDao noteDao) {
        this.noteDao = noteDao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<notes> findAll() {
        return (List<notes>) noteDao.findAll();
    }

    @Override
    public notes save(notes note) {
        return noteDao.save(note);
    }

    @Override
    @Transactional(readOnly = true)
    public notes findById(String id) {
       return noteDao.findById(id).orElse(null);
    }

    @Override
    public void delete(notes note) {
        noteDao.delete(note);
    }
    
}
