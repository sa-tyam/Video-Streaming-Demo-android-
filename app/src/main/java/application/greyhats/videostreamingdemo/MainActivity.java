package application.greyhats.videostreamingdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "TAG";
    RecyclerView videoList;
    VideoAdapter adapter;

    List<Video> all_videos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        all_videos = new ArrayList<>();
        videoList = findViewById(R.id.recycleView);
        videoList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new VideoAdapter(this, all_videos);
        videoList.setAdapter(adapter);

        getJsonData();

    }

    private void getJsonData() {
        InputStream is = getResources().openRawResource(R.raw.data);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];

        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n=reader.read(buffer))!= -1) {
                writer.write(buffer, 0, n);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String jsonString = writer.toString();

        try {
            JSONObject response = new JSONObject(jsonString);
            JSONArray categories = response.getJSONArray("categories");
            JSONObject categoryData = categories.getJSONObject(0);
            JSONArray videos = categoryData.getJSONArray("videos");

            for ( int i = 0; i < videos.length(); i++ ) {
                JSONObject video = videos.getJSONObject(i);

                Video v = new Video();
                v.setTitle(video.getString("title"));
                v.setDescription(video.getString("description"));
                v.setAuthor(video.getString("subtitle"));
                v.setImageUrl(video.getString("thumb"));
                JSONArray videoUrl = video.getJSONArray("sources");
                v.setVideoUrl(videoUrl.getString(0));

                Log.i("image_url", video.getString("thumb"));

                all_videos.add(v);
                adapter.notifyDataSetChanged();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}