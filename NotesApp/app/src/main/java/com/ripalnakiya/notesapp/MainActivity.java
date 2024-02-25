package com.ripalnakiya.notesapp;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ripalnakiya.notesapp.repository.room.Note;
import com.ripalnakiya.notesapp.viewmodel.NoteViewModel;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity.this";
    private NoteViewModel viewModel;
    MyDialogManager myDialogManager = MyDialogManager.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        FloatingActionButton faButton = findViewById(R.id.faButton);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        NoteAdapter adapter = new NoteAdapter();
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        viewModel.getAllNotes().observe(this, notes -> {
            adapter.setNotes(notes);
            Log.d(TAG, "onChanged: Notes Loaded");
        });

        faButton.setOnClickListener(v -> {
            Dialog dialog = myDialogManager.getAddDialog(MainActivity.this);
            dialog.show();
        });

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.deleteAllMenu) {
                    viewModel.deleteAll();
                    Toast.makeText(MainActivity.this, "All Notes Deleted", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
    }

    OnNoteListener onNoteListener = new OnNoteListener() {
        @Override
        public void onNoteAdd(Note note) {
            viewModel.insert(note);
            Toast.makeText(MainActivity.this, "Note Added", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNoteDelete(Note note) {
            viewModel.delete(note);
            Toast.makeText(MainActivity.this, "Note Deleted", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNoteUpdate(Note note) {
            viewModel.update(note);
            Toast.makeText(MainActivity.this, "Note Updated", Toast.LENGTH_SHORT).show();
        }
    };
}






