package com.studentmanagement.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.storage.FirebaseStorage;

import com.studentmanagement.R;

import com.studentmanagement.Models.Notes;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ImageViewHolder> {

    private Context mContext;
    private List<Notes> mNotes;

    public NotesAdapter(Context context, List<Notes> notes){
        mContext = context;
        mNotes = notes;
    }


    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.single_note_layout, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ImageViewHolder holder, final int position) {

        final Notes notes = mNotes.get(position);


        holder.Note.setText(notes.getNote());
        holder.Pdf_Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                browserIntent.setDataAndType(Uri.parse(notes.getUrl()), "application/pdf");
                mContext.startActivity(browserIntent);
            }
        });
        if (!notes.getUrl().isEmpty()) {
            holder.Pdf_Layout.setVisibility(View.VISIBLE);
            holder.Pdf_Name.setText(FirebaseStorage.getInstance().getReferenceFromUrl(notes.getUrl()).getName());
        } else {
            holder.Pdf_Layout.setVisibility(View.GONE);
        }



    }

    @Override
    public int getItemCount() {
        return mNotes.size();
    }


    public class ImageViewHolder extends RecyclerView.ViewHolder {

        public TextView Note, Pdf_Name;
        private RelativeLayout Pdf_Layout;


        public ImageViewHolder(View itemView) {
            super(itemView);

            Note = itemView.findViewById(R.id.note);
            Pdf_Name = itemView.findViewById(R.id.pdf_name);
            Pdf_Layout = itemView.findViewById(R.id.pdf_layout);
        }
    }



}