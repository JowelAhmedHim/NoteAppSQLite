package com.example.noteappbysqldatabase.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noteappbysqldatabase.Activity.UpdateNoteActivity;
import com.example.noteappbysqldatabase.Model.NotesModel;
import com.example.noteappbysqldatabase.R;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> implements Filterable {

    Context context;
    Activity activity;
    List<NotesModel> notesList;
    List<NotesModel> newList;

    public NoteAdapter(Context context, Activity activity, List<NotesModel> notesList) {
        this.context = context;
        this.activity = activity;
        this.notesList = notesList;
        newList = new ArrayList<>(notesList);
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.note_rv_item,parent,false);
        NoteViewHolder vh = new NoteViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {


        holder.titleTv.setText(notesList.get(position).getTitle());
        holder.descriptionTv.setText(notesList.get(position).getDescription());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateNoteActivity.class);
                intent.putExtra("id",notesList.get(position).getId());
                intent.putExtra("title",notesList.get(position).getTitle());
                intent.putExtra("description",notesList.get(position).getDescription());

                activity.startActivity(intent);


            }
        });

    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    class NoteViewHolder extends RecyclerView.ViewHolder{

        private TextView titleTv,descriptionTv;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTv = itemView.findViewById(R.id.noteTitle);
            descriptionTv = itemView.findViewById(R.id.noteDescription);

        }
    }



    // filter search result
    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<NotesModel> filterList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0)
            {
                filterList.addAll(newList);

            }else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (NotesModel item :newList){
                    if (item.getTitle().toLowerCase().contains(filterPattern)){
                        filterList.add(item);
                    }

                }

            }
            FilterResults results = new FilterResults();
            results.values =filterList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            notesList.clear();
            notesList.addAll((List)results.values);
            notifyDataSetChanged();

        }
    };


    //swap to remove data and restore
    public List<NotesModel> getList()
    {
        return notesList;
    }
    public void removeItem(int position) {
        notesList.remove(position);
        notifyItemRemoved(position);
    }
    public void restoreItem(NotesModel item,int position) {
        notesList.add(position,item);
        notifyItemInserted(position);

    }
}
