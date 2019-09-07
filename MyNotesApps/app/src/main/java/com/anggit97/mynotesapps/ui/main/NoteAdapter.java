package com.anggit97.mynotesapps.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.anggit97.mynotesapps.R;
import com.anggit97.mynotesapps.database.Note;
import com.anggit97.mynotesapps.helper.NoteDiffCallback;
import com.anggit97.mynotesapps.ui.insert.NoteAddUpdateActivity;

import java.util.ArrayList;
import java.util.List;

import static com.anggit97.mynotesapps.ui.insert.NoteAddUpdateActivityKt.EXTRA_NOTE;
import static com.anggit97.mynotesapps.ui.insert.NoteAddUpdateActivityKt.EXTRA_POSITION;
import static com.anggit97.mynotesapps.ui.insert.NoteAddUpdateActivityKt.REQUEST_UPDATE;

/**
 * Created by Anggit Prayogo on 2019-09-06.
 * Github : @anggit97
 */
public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {
    private final ArrayList<Note> listNotes = new ArrayList<>();
    private final Activity activity;

    NoteAdapter(Activity activity) {
        this.activity = activity;
    }

    void setListNotes(List<Note> listNotes) {
        final NoteDiffCallback diffCallback = new NoteDiffCallback(this.listNotes, listNotes);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

        this.listNotes.clear();
        this.listNotes.addAll(listNotes);
        diffResult.dispatchUpdatesTo(this);
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final NoteViewHolder holder, int position) {
        holder.tvTitle.setText(listNotes.get(position).getTitle());
        holder.tvDate.setText(listNotes.get(position).getDate());
        holder.tvDescription.setText(listNotes.get(position).getDescription());
        holder.cvNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, NoteAddUpdateActivity.class);
                intent.putExtra(EXTRA_POSITION, holder.getAdapterPosition());
                intent.putExtra(EXTRA_NOTE, listNotes.get(holder.getAdapterPosition()));
                activity.startActivityForResult(intent, REQUEST_UPDATE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listNotes.size();
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
