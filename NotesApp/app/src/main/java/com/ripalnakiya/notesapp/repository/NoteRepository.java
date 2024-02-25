package com.ripalnakiya.notesapp.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.ripalnakiya.notesapp.repository.room.Note;
import com.ripalnakiya.notesapp.repository.room.NoteDao;
import com.ripalnakiya.notesapp.repository.room.NoteDatabase;

import java.util.List;

public class NoteRepository {
    private NoteDao noteDao;
    private LiveData<List<Note>> allNotes;

    public NoteRepository(Application application) {
        NoteDatabase noteDatabase = NoteDatabase.getInstance(application);
        noteDao = noteDatabase.noteDao();
        allNotes = noteDao.getAllNotes();
    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }

    public void insert(Note note) {
        InsertNoteAsyncTask insertNoteAsyncTask = new InsertNoteAsyncTask(noteDao);
        insertNoteAsyncTask.execute(note);
    }

    public void update(Note note) {
        UpdateNoteAsyncTask updateNoteAsyncTask = new UpdateNoteAsyncTask(noteDao);
        updateNoteAsyncTask.execute(note);
    }

    public void delete(Note note) {
        DeleteNoteAsyncTask deleteNoteAsyncTask = new DeleteNoteAsyncTask(noteDao);
        deleteNoteAsyncTask.execute(note);
    }

    public void deleteAll() {
        DeleteAllNoteAsyncTask deleteAllNoteAsyncTask = new DeleteAllNoteAsyncTask(noteDao);
        deleteAllNoteAsyncTask.execute();
    }

    private static class InsertNoteAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteDao noteDao;

        public InsertNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.insert(notes[0]);
            return null;
        }
    }

    private static class UpdateNoteAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteDao noteDao;

        public UpdateNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.update(notes[0]);
            return null;
        }
    }

    private static class DeleteNoteAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteDao noteDao;

        public DeleteNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.delete(notes[0]);
            return null;
        }
    }

    private static class DeleteAllNoteAsyncTask extends AsyncTask<Void, Void, Void> {
        private NoteDao noteDao;

        public DeleteAllNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.deleteAll();
            return null;
        }
    }
}
