package com.example.androiddeezer2020;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.androiddeezer2020.adapter.AlbumAdapter;
import com.example.androiddeezer2020.adapter.ArtistAdapter;
import com.example.androiddeezer2020.service.DeezerService;
import com.example.androiddeezer2020.service.data.DataAlbum;
import com.example.androiddeezer2020.service.data.DataSearchArtist;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progressBar = findViewById(R.id.progress_circular);
        hideProgress();

        recyclerView = (RecyclerView) findViewById(R.id.author_recycler_view);
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        final SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            String lastText = null;

            @Override
            public boolean onQueryTextSubmit(String query) {

                if (!query.equals(lastText)) {
                    lastText = query;
                    Snackbar.make(findViewById(android.R.id.content),
                            "Search " + query,
                            Snackbar.LENGTH_SHORT).show();
                    showProgress();

                    Response.Listener<DataAlbum> rep2 = new Response.Listener<DataAlbum>() {
                        @Override
                        public void onResponse(DataAlbum response) {
                            Log.d(TAG, "searchAlbum Found " + response.getTotal() + " album");
                            AlbumAdapter mAdapter = new AlbumAdapter(response.getData());
                            recyclerView.setAdapter(mAdapter);
                            hideProgress();

                        }
                    };


                    Response.Listener<DataSearchArtist> rep = new Response.Listener<DataSearchArtist>() {
                        @Override
                        public void onResponse(DataSearchArtist response) {
                            Log.d(TAG, "searchAuthor Found " + response.getTotal() + " artist");
                            ArtistAdapter mAdapter = new ArtistAdapter(response.getData());
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
                    //DeezerService.searchAlbum(query, rep2, error, MainActivity.this);
                    //Pour la recherche d'auteur
                    DeezerService.searchAuthor(MainActivity.this, query, rep, error);

                }
                return false;
            }


            @Override
            public boolean onQueryTextChange(String query) {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }

}