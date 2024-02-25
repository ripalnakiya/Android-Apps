package com.ripalnakiya.notesapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.ripalnakiya.notesapp.repository.NoteRepository;
import com.ripalnakiya.notesapp.repository.room.Note;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {
    NoteRepository repository;
    LiveData<List<Note>> allNotes;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        repository = new NoteRepository(application);
        allNotes = repository.getAllNotes();
    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }

    public void insert(Note note) {
        repository.insert(note);
    }

    public void update(Note note) {
        repository.update(note);
    }

    public void delete(Note note) {
        repository.delete(note);
    }

    public void deleteAll() {
        repository.deleteAll();
    }
}
