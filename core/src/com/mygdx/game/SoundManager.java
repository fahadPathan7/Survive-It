package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class SoundManager{

    public static Music home;

    public static Music gameLevel3;

    public static Music menu;
    public static Music about;
    public static Music setting;
    public static Music highscore;
    public static Music end;

    public static Music intro;


    public static void create()
    {
        home = Gdx.audio.newMusic(Gdx.files.internal("Audio\\home.mp3"));

        gameLevel3 = Gdx.audio.newMusic(Gdx.files.internal("Audio\\gameLevel3.mp3"));

        menu = Gdx.audio.newMusic(Gdx.files.internal("Audio\\menu.mp3"));
        about = Gdx.audio.newMusic(Gdx.files.internal("Audio\\about.mp3"));
        setting = Gdx.audio.newMusic(Gdx.files.internal("Audio\\setting.mp3"));
        highscore = Gdx.audio.newMusic(Gdx.files.internal("Audio\\highscore.mp3"));
        end = Gdx.audio.newMusic(Gdx.files.internal("Audio\\end.mp3"));

        intro = Gdx.audio.newMusic(Gdx.files.internal("Audio\\intro.mp3"));

    }

    public void dispose() {

        home.dispose();
        gameLevel3.dispose();
        menu.dispose();
        about.dispose();
        setting.dispose();
        highscore.dispose();
        end.dispose();
        intro.dispose();

    }



}
