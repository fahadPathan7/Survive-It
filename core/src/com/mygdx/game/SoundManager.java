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

    public static Music collisionWithEmeny;
    public static Music blast;
    public static Music jump;


    public static Music click ;
    public static Music countdown ;
    public static Music surfaceChange ;


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

        collisionWithEmeny = Gdx.audio.newMusic(Gdx.files.internal("Audio\\collsiion1.mp3"));
        blast = Gdx.audio.newMusic(Gdx.files.internal("Audio\\collsiion3.mp3"));
        jump= Gdx.audio.newMusic(Gdx.files.internal("Audio\\jump.mp3"));

        click = Gdx.audio.newMusic(Gdx.files.internal("Audio\\click.mp3"));
        countdown = Gdx.audio.newMusic(Gdx.files.internal("Audio\\countdown.mp3"));
        surfaceChange = Gdx.audio.newMusic(Gdx.files.internal("Audio\\surfaceChange.mp3"));

        SoundManager.click.setVolume(0.3f);
        SoundManager.jump.setVolume(0.3f);
        SoundManager.countdown.setVolume(0.4f);
        SoundManager.surfaceChange.setVolume(0.3f);

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
        collisionWithEmeny.dispose();
        blast.dispose();
        click.dispose();
        jump.dispose();
        surfaceChange.dispose();

    }



}
