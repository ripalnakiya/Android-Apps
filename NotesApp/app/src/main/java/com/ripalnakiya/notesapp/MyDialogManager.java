package com.ripalnakiya.notesapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.ripalnakiya.notesapp.repository.room.Note;

import java.util.ArrayList;

public class MyDialogManager {
    private static MyDialogManager myDialogManager = null;
    View dialogView;
    EditText dialogTitle;
    EditText dialogDescription;
    Spinner dialogSpinner;

    public static MyDialogManager getInstance() {
        if (myDialogManager == null) {
            myDialogManager = new MyDialogManager();
        }
        return myDialogManager;
    }

    public AlertDialog.Builder getDialogBuilder(Context context) {
        dialogView = LayoutInflater.from(context).inflate(R.layout.add_update_dialog, null);

        dialogTitle = dialogView.findViewById(R.id.dialogTitle);
        dialogDescription = dialogView.findViewById(R.id.dialogDescription);
        dialogSpinner = dialogView.findViewById(R.id.dialogSpinner);

        // Set Spinner Values
        ArrayList<String> priorities = new ArrayList<>();
        priorities.add("1");
        priorities.add("2");
        priorities.add("3");
        priorities.add("4");
        priorities.add("5");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, priorities);
        dialogSpinner.setAdapter(adapter);

        // Create a new AlertDialog.Builder and SetView, SetNeutralButton(Cancel Operation)
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(dialogView)
                .setCancelable(false)
                // For Positive Button We has to implement in the particular method
                .setNeutralButton("Cancel", (dialog, which) ->
                        Toast.makeText(context, "Discarded", Toast.LENGTH_SHORT).show());
        return builder;
    }

    public AlertDialog getAddDialog(Context context) {
        AlertDialog.Builder builder = getDialogBuilder(context);
        builder.setPositiveButton("Add", (dialog, which) -> {
            Note note = getNoteFromDialog();
            // Add Note through Interface
            ((MainActivity) context).onNoteListener.onNoteAdd(note);
        });
        return builder.create();
    }

    public AlertDialog getUpdateDialog(Context context, Note note) {
        AlertDialog.Builder builder = getDialogBuilder(context);

        // Fill the Original Note Details
        dialogTitle.setText(note.getTitle());
        dialogDescription.setText(note.getDescription());
        dialogSpinner.setSelection(note.getPriority() - 1);

        builder.setPositiveButton("Update", (dialog, which) -> {
            Note newNote = getNoteFromDialog();
            // Get the old Note Id, and set it to the newNote
            newNote.setId(note.getId());

            // Add Note through Interface
            ((MainActivity) context).onNoteListener.onNoteUpdate(newNote);
        });
        return builder.create();
    }

    private Note getNoteFromDialog() {
        String title = dialogTitle.getText().toString();
        String description = dialogDescription.getText().toString();
        int priority = dialogSpinner.getSelectedItemPosition();
        return new Note(title, description, priority + 1);
    }

    public AlertDialog getDeleteDialog(Context context, Note note) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Delete Note ?")
                .setPositiveButton("Yes", (dialog, id) -> {
                    ((MainActivity) context).onNoteListener.onNoteDelete(note);
                })
                .setNegativeButton("No", (dialog, id) ->
                        Toast.makeText(context, "Delete Cancelled", Toast.LENGTH_SHORT).show());
        return builder.create();
    }

}
