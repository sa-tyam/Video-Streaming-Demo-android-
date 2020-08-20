package application.greyhats.videostreamingdemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {
    private List<Video> allVideos;
    private Context context;

    public VideoAdapter ( Context ctx, List<Video> videos) {
        this.allVideos = videos;
        this.context = ctx;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_element_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.titleTextView.setText(allVideos.get(position).getTitle());
        holder.subtitleTextView.setText(allVideos.get(position).getAuthor());
        Picasso.get().load(allVideos.get(position).getImageUrl()).into(holder.thumbnailImage);

        holder.vv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putSerializable("videoData", allVideos.get(position));
                Intent i = new Intent(context, PlayerActivity.class);
                i.putExtras(b);
                v.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return allVideos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView thumbnailImage;
        TextView titleTextView;
        TextView subtitleTextView;
        View vv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            thumbnailImage = itemView.findViewById(R.id.thumbnailImageView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            subtitleTextView = itemView.findViewById(R.id.subtitleTextView);
            vv = itemView;
        }
    }
}
