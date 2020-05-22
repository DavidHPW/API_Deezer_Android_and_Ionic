package com.example.androiddeezer2020;

import android.media.MediaPlayer;

//Singleton MediaPlayer qui sera instancié par TrackAdapter et TrackActivity
public enum MediaPlayerSingleton {
    INSTANCE;
    public MediaPlayer mp = new MediaPlayer();
}
