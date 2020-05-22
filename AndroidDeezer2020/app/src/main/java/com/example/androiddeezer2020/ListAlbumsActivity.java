package com.example.androiddeezer2020;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.androiddeezer2020.adapter.AlbumAdapter;
import com.example.androiddeezer2020.service.DeezerService;
import com.example.androiddeezer2020.service.data.DataAlbum;

public class ListAlbumsActivity extends AppCompatActivity {
    private static final String TAG = "ListAlbumsActivity";

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ProgressBar progressBar;
    //    public Bundle extra;
//    public String value;
    private String lastText = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_albums);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progressBar = findViewById(R.id.progress_circular);
        hideProgress();

        recyclerView = (RecyclerView) findViewById(R.id.album_recycler_view);
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //récupérer le nom de l'artiste de ArtistAdapter
//        extra = getIntent().getExtras();
//        value = extra.getString(Intent.EXTRA_TEXT);
        String artist = getIntent().getStringExtra("artist");
        showProgress();

        Log.d("intent", artist);


        Response.Listener<DataAlbum> rep2 = new Response.Listener<DataAlbum>() {
            @Override
            public void onResponse(DataAlbum response) {
                Log.d(TAG, "searchAlbum Found " + response.getTotal() + " album");
                AlbumAdapter mAdapter = new AlbumAdapter(response.getData());
                recyclerView.setAdapter(mAdapter);
                hideProgress();

            }
        };

        final Response.ErrorListener error = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "searchAuthor onErrorResponse: " + error.getMessage());
                hideProgress();
            }
        };

        //Pour la recherche d'album
        DeezerService.searchAlbum(artist, rep2, error, ListAlbumsActivity.this);


    }
    //}


    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }

}
