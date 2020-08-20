package application.greyhats.videostreamingdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

public class PlayerActivity extends AppCompatActivity {

    ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        spinner = findViewById(R.id.progressBar);

        Intent i = getIntent();
        Bundle data = i.getExtras();
        Video v = (Video) data.getSerializable("videoData");

        TextView titleTextView = findViewById(R.id.titleTextView);
        TextView descriptionTextView = findViewById(R.id.descriptionTextView);
        final VideoView videoView = findViewById(R.id.videoView);

        Uri videoUri = Uri.parse(v.getVideoUrl());

        titleTextView.setText(v.getTitle());
        descriptionTextView.setText(v.getDescription());
        getSupportActionBar().setTitle(v.getTitle());

        videoView.setVideoURI(videoUri);

        MediaController mc = new MediaController(this);
        videoView.setMediaController(mc);

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                videoView.start();
                spinner.setVisibility(View.GONE);
            }
        });
    }
}