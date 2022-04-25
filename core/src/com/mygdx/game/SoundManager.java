package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class SoundManager{

    public static Music home;

    public static Music gameLevel3;
    public static Music gameLevel2;
    public static Music gameLevel1;

    public static Music menu;
    public static Music about;



    public static void create()
    {
        home = Gdx.audio.newMusic(Gdx.files.internal("Audio\\home.mp3"));


        gameLevel3 = Gdx.audio.newMusic(Gdx.files.internal("Audio\\gameLevel3.mp3"));
        gameLevel2 = Gdx.audio.newMusic(Gdx.files.internal("Audio\\gameLevel2.mp3"));
        gameLevel1 = Gdx.audio.newMusic(Gdx.files.internal("Audio\\gameLevel1.mp3"));

        menu = Gdx.audio.newMusic(Gdx.files.internal("Audio\\menu.mp3"));
        about = Gdx.audio.newMusic(Gdx.files.internal("Audio\\about.mp3"));


    }

    public void dispose() {

    }



}
