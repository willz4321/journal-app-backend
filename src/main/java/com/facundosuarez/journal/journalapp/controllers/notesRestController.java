package com.facundosuarez.journal.journalapp.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.facundosuarez.journal.journalapp.models.entity.auth.userEntity;
import com.facundosuarez.journal.journalapp.models.entity.note.notes;
import com.facundosuarez.journal.journalapp.models.services.NotesServiceImpl.INotesService;
import com.facundosuarez.journal.journalapp.models.services.UserServiceImpl.IUserService;

@CrossOrigin(origins = { "http://localhost:3000" })
@RestController
@RequestMapping("/journal")
public class notesRestController {
    
    @Autowired
    private INotesService noteService;

    @Autowired
    private IUserService userService;
    
    @GetMapping("/notes/{userId}")
    public List<notes> lookNotes(@PathVariable String userId){
          List<notes> allNotes = noteService.findAll();

    // Filtrar las notas para obtener solo las asociadas al ID de usuario especificado
            List<notes> userNotes = allNotes.stream()
                    .filter(note -> userId.equals(note.getUser().getId()))
                    .collect(Collectors.toList());

            return userNotes;
    }

    @PostMapping("/registerNote/{userId}")
    public ResponseEntity<Object> createNote( @RequestBody notes note, @PathVariable String userId) {

        
        userEntity user = userService.findById(userId); 
        note.setUser(user);
        
       
        noteService.save(note);
        
        return ResponseEntity.status(HttpStatus.CREATED).body( noteService.save(note));
    }
    
    @PutMapping("/modifNotes/{noteId}")
    public ResponseEntity<Object> modifNote( @RequestBody notes note, @PathVariable String noteId) {

        try {
            // Buscar la nota existente por su ID
            notes existingNote = noteService.findById(noteId);

            // Verificar si la nota existe
            if (existingNote == null) {
                return ResponseEntity.notFound().build();
            }

            // Actualizar el título y el cuerpo de la nota con los valores proporcionados
            existingNote.setTitle(note.getTitle());
            existingNote.setBody(note.getBody());
            existingNote.setImageUrls(note.getImageUrls());

            // Guardar la nota actualizada en la base de datos
            noteService.save(existingNote);

            // Devolver una respuesta exitosa
            return ResponseEntity.status(HttpStatus.OK).body(noteService.save(existingNote));
        } catch (Exception ex) {
            // Manejar cualquier excepción que pueda ocurrir durante la operación
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al modificar la nota.");
        }
       
    }

    @DeleteMapping("/deleteNote/{noteId}")
    public ResponseEntity<Object> deleteNote(@PathVariable String noteId) {
        try {
            // Buscar la nota existente por su ID
            notes existingNote = noteService.findById(noteId);
    
            // Verificar si la nota existe
            if (existingNote == null) {
                return ResponseEntity.notFound().build();
            }
    
            // Eliminar la nota de la base de datos
            noteService.delete(existingNote);
    
            // Devolver una respuesta exitosa
            return ResponseEntity.status(HttpStatus.OK).body("Nota eliminada exitosamente.");
        } catch (Exception ex) {
            // Manejar cualquier excepción que pueda ocurrir durante la operación
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar la nota.");
        }
    }
}
