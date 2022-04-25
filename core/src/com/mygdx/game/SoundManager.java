package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class SoundManager{

    public static Music home;
    public static Music game;

    public static void create()
    {
        home = Gdx.audio.newMusic(Gdx.files.internal("Audio\\home.mp3"));
        game = Gdx.audio.newMusic(Gdx.files.internal("Audio\\game.mp3"));

    }

    public void dispose() {
        home.dispose();
    }



}
