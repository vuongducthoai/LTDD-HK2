package com.example.databinding_shared_preferences_sqllite.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.databinding_shared_preferences_sqllite.MainActivity;
import com.example.databinding_shared_preferences_sqllite.R;
import com.example.databinding_shared_preferences_sqllite.model.NotesModel;

import java.util.List;

public class NotesAdapter extends BaseAdapter {
    //Khoi tao bien toan cuc
    private Context context;
    private int layout;
    private List<NotesModel> noteList;
    private MainActivity context1;

    //tao constructor
    public NotesAdapter(Context context, int layout, List<NotesModel> noteList){
        this.context = context;
        this.layout = layout;
        this.noteList = noteList;
        this.context1 = (MainActivity) context;
    }

    @Override
    public int getCount() {
        return noteList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class  ViewHolder{
        TextView textViewNote;
        ImageView imageViewEdit;
        ImageView imageViewDelete;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //gọi viewHolder
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            viewHolder.textViewNote = (TextView) convertView.findViewById(R.id.textViewNameNote);
            viewHolder.imageViewDelete = (ImageView) convertView.findViewById(R.id.imageViewDelete);
            viewHolder.imageViewEdit = (ImageView) convertView.findViewById(R.id.imageViewEdit);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //Lấy giá trị
        NotesModel notesModel = noteList.get(position);
        viewHolder.textViewNote.setText(notesModel.getNameNote());

        //Bat su kien nut cap nhat
        viewHolder.imageViewEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Cập nhât" + notesModel.getNameNote(), Toast.LENGTH_SHORT).show();
                //Goi Dialog trong MainActivity.java
                context1.DialogCapNhatNotes(notesModel.getNameNote(), notesModel.getIdNote());
            }
        });

        viewHolder.imageViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context1.DialogDelete(notesModel.getNameNote(), notesModel.getIdNote());
            }
        });
        return convertView;
    }

}
