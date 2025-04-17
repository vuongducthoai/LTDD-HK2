package com.example.firebase.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.firebase.R;
import com.example.firebase.model.Video;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    private Context context;
    private List<Video> videoList;

    public VideoAdapter(Context context, List<Video> videoList) {
        this.context = context;
        this.videoList = videoList;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_video, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        Video video = videoList.get(position);

        // Load avatar & email
        Glide.with(context).load(video.getAvatar()).into(holder.avatar);
        holder.email.setText(video.getEmail());

        // Set số like/dislike
        holder.likes.setText(String.valueOf(video.getQuantityLike()));
        holder.dislikes.setText(String.valueOf(video.getQuantityDislike()));

        // Load video
        Uri videoUri = Uri.parse(video.getUrlVideo());
        holder.videoView.setVideoURI(videoUri);
        holder.videoView.setOnPreparedListener(mp -> {
            mp.setLooping(true); // Phát lặp như TikTok
            holder.videoView.start(); // Tự động phát
        });

        // Optional: xử lý click share, like, more...
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    public static class VideoViewHolder extends RecyclerView.ViewHolder {
        VideoView videoView;
        ImageView avatar;
        TextView email, likes, dislikes;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);

            videoView = itemView.findViewById(R.id.videoView);
            avatar = itemView.findViewById(R.id.imgAvatar);
            email = itemView.findViewById(R.id.txtEmail);
            likes = itemView.findViewById(R.id.txtLikes);
            dislikes = itemView.findViewById(R.id.txtDislikes);
        }
    }
}
