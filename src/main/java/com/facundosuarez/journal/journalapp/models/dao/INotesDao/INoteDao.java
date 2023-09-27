package com.facundosuarez.journal.journalapp.models.dao.INotesDao;

import org.springframework.data.repository.CrudRepository;

import com.facundosuarez.journal.journalapp.models.entity.note.notes;

public interface INoteDao extends CrudRepository<notes, String>{
    
}
