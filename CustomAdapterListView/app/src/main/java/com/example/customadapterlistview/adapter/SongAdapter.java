package com.example.customadapterlistview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customadapterlistview.R;
import com.example.customadapterlistview.model.SongModel;

import java.util.List;

public class SongAdapter extends  RecyclerView.Adapter<SongAdapter.SongViewHolder>{
    private static final String  TAG = "SongAdapter";
    private List<SongModel> mSongs; // Danh sach các bai hat
    private Context mContext; // Ngu canh cua ung dung
    private LayoutInflater mLayoutInflater; // Doi tuong ho tro tao view tu XML


    public SongAdapter(Context context, List<SongModel> datas){
        mContext = context;
        mSongs = datas;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public SongAdapter.SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflate view from row_item_song.xml
        View itemView = mLayoutInflater.inflate(R.layout.row_item_song, parent, false);
        return new SongViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SongAdapter.SongViewHolder holder, int position) {
        //Get song in mSong via position
        SongModel song = mSongs.get(position);
        //bind data to viewholder
        holder.tvCode.setText(song.getmCode());
        holder.tvTitle.setText(song.getmTitle());
        holder.tvArtist.setText(song.getmArtist());
    }

    @Override
    public int getItemCount() {
        return mSongs.size();
    }

    class SongViewHolder extends RecyclerView.ViewHolder{
        private TextView tvCode;
        private TextView tvTitle;
        private TextView tvLyric;
        private TextView tvArtist;

        public SongViewHolder(@NonNull View itemView){
            super(itemView);
            tvCode = (TextView)itemView.findViewById(R.id.tv_code);
            tvTitle = (TextView)itemView.findViewById(R.id.tv_title);
            tvLyric = (TextView)itemView.findViewById(R.id.tv_lyric);
            tvArtist = (TextView)itemView.findViewById(R.id.tv_artist);

            //Thiet lap su kien
            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    SongModel song = mSongs.get(getAdapterPosition());
                    Toast.makeText(mContext, song.getmTitle(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
