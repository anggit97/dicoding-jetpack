package com.anggit97.mynotesapps.ui.main;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.anggit97.mynotesapps.R;
import com.anggit97.mynotesapps.database.Note;
import com.anggit97.mynotesapps.ui.insert.NoteAddUpdateActivity;

import static com.anggit97.mynotesapps.ui.insert.NoteAddUpdateActivityKt.EXTRA_NOTE;
import static com.anggit97.mynotesapps.ui.insert.NoteAddUpdateActivityKt.EXTRA_POSITION;
import static com.anggit97.mynotesapps.ui.insert.NoteAddUpdateActivityKt.REQUEST_UPDATE;

/**
 * Created by Anggit Prayogo on 2019-09-09.
 * Github : @anggit97
 */
public class NotePagedListAdapter extends PagedListAdapter<Note, NotePagedListAdapter.NoteViewHolder> {
    private Activity activity;

    NotePagedListAdapter(Activity activity) {
        super(DIFF_CALLBACK);
        this.activity = activity;
    }

    private static DiffUtil.ItemCallback<Note> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Note>() {
                @Override
                public boolean areItemsTheSame(Note oldNote, Note newNote) {
                    return oldNote.getTitle().equals(newNote.getTitle());
                }

                @SuppressLint("DiffUtilEquals")
                @Override
                public boolean areContentsTheSame(Note oldNote, @NonNull Note newNote) {
                    return oldNote.equals(newNote);
                }
            };

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final NoteViewHolder holder, int position) {
        final Note note = getItem(position);
        if (note != null) {
            holder.tvTitle.setText(note.getTitle());
            holder.tvDate.setText(note.getDate());
            holder.tvDescription.setText(note.getDescription());
            holder.cvNote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, NoteAddUpdateActivity.class);
                    intent.putExtra(EXTRA_POSITION, holder.getAdapterPosition());
                    intent.putExtra(EXTRA_NOTE, note);
                    activity.startActivityForResult(intent, REQUEST_UPDATE);
                }
            });
        }
    }

    class NoteViewHolder extends RecyclerView.ViewHolder {
        final TextView tvTitle, tvDescription, tvDate;
        final CardView cvNote;

        NoteViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_item_title);
            tvDescription = itemView.findViewById(R.id.tv_item_description);
            tvDate = itemView.findViewById(R.id.tv_item_date);
            cvNote = itemView.findViewById(R.id.cv_item_note);
        }
    }
}
