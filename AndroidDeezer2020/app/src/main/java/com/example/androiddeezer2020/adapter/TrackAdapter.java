package com.example.androiddeezer2020.adapter;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.androiddeezer2020.MediaPlayerSingleton;
import com.example.androiddeezer2020.R;
import com.example.androiddeezer2020.service.data.Track;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TrackAdapter extends RecyclerView.Adapter<TrackAdapter.ViewHolder> {
    private static final String TAG = "AdapterTrack";

    private List<Track> listTrack;

    public TrackAdapter(List<Track> listTrack) {
        this.listTrack = listTrack;

    }

    @Override
    public TrackAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cell_track, parent, false);
        TrackAdapter.ViewHolder vh = new TrackAdapter.ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(TrackAdapter.ViewHolder holder, int position) {
        final Track track = listTrack.get(position);

        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.textTrackName.setText(track.getTitle());
//        Picasso.get().load(track.getPicture()).into(holder.imageView);
        SimpleDateFormat format = new SimpleDateFormat("mm:ss", Locale.FRANCE);
        String str = format.format(new Date(track.getDuration() * 1000));

        holder.textTrackDuration.setText(str);
        holder.playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "click on <" + track.getId() + ">");

                MediaPlayerSingleton.INSTANCE.mp.reset();
                MediaPlayerSingleton.INSTANCE.mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
                try {
                    MediaPlayerSingleton.INSTANCE.mp.setDataSource(track.getPreview());
                    MediaPlayerSingleton.INSTANCE.mp.prepare();
                } catch (IOException e) {
                    Log.e(TAG, "erreur media preview", e);
                }
                MediaPlayerSingleton.INSTANCE.mp.start();

            }
        });

    }

    @Override
    public int getItemCount() {
        return listTrack.size();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textTrackName;
        //      private ImageView imageView;
        private TextView textTrackDuration;
        private ImageButton playButton;
        private MediaPlayer mediaPlayer;
        private View itemView;


        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            //c'est ici que l'on fait nos findView
            textTrackName = (TextView) itemView.findViewById(R.id.textTrackName);
            textTrackDuration = (TextView) itemView.findViewById(R.id.textTrackDuration);
            playButton = (ImageButton) itemView.findViewById(R.id.playButton);
//            imageView = (ImageView) itemView.findViewById(R.id.imageTrack);
        }
    }
}
