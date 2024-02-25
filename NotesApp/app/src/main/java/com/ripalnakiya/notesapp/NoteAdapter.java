package com.ripalnakiya.notesapp;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ripalnakiya.notesapp.repository.room.Note;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {
    List<Note> notes = new ArrayList<>();
    Context context;
    MyDialogManager myDialogManager = MyDialogManager.getInstance();

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.note_item, parent, false);
        NoteViewHolder noteViewHolder = new NoteViewHolder(itemView);
        return noteViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note currentNote = notes.get(position);
        holder.textViewPriority.setText(String.valueOf(currentNote.getPriority()));
        holder.textViewTitle.setText(currentNote.getTitle());
        holder.textViewDescription.setText(currentNote.getDescription());

        holder.itemContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = myDialogManager.getUpdateDialog(context, currentNote);
                dialog.show();
            }
        });

        holder.itemContainer.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Dialog dialog = myDialogManager.getDeleteDialog(context, currentNote);
                dialog.show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    class NoteViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewPriority, textViewTitle, textViewDescription;
        private RelativeLayout itemContainer;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewPriority = itemView.findViewById(R.id.textViewPriority);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
            itemContainer = itemView.findViewById(R.id.itemContainer);
        }
    }
}
