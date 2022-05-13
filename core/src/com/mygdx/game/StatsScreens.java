package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import game.AirWaterScreen;
import game.Score;


public class StatsScreens implements Screen {

    MyGdxGame game;

    Score monsterKills ;
    Score monsterFails;
    Score monsterSpellUsed ;
    Score monsterSpellMiss ;
    Score monsterSpellOnScreen ;

    Texture statsScreen ;

    // sound starts
    public boolean soundState;
    public boolean showMute;
    Texture mute ;
    // sound ends

    public int status;

    public StatsScreens(MyGdxGame game,boolean soundState,int status) {

        this.game = game;
        this.soundState = soundState;
        this.status = status;

        monsterKills = new Score(game.batch);
        monsterFails = new Score(game.batch);
        monsterSpellUsed = new Score(game.batch);
        monsterSpellMiss = new Score(game.batch);
        monsterSpellOnScreen = new Score(game.batch);

        SoundManager.create();
        SoundManager.end.setLooping(true);
        SoundManager.end.setVolume(0.4f);     // 50% of main volume
        if(soundState)
        {
            //SoundManager.end.play();
            showMute = false;
        }
        else showMute = true;

        System.out.println(AirWaterScreen.totalSpellCnt);
        System.out.println(AirWaterScreen.totalSpellMissCnt);
        System.out.println(AirWaterScreen.totalMonsterHitCnt);
        System.out.println(AirWaterScreen.totalMonsterMissCnt);
    }


    @Override
    public void show() {

       statsScreen = new Texture("stats\\stats.png");
       mute = new Texture("Audio\\mute.png") ;

        monsterKills.digitY = 680 ;
        monsterKills.plusWeight = 500;
        monsterKills.score = AirWaterScreen.totalMonsterHitCnt;

        monsterFails.digitY = 560+10 ;
        monsterFails.plusWeight = 500;
        monsterFails.score = AirWaterScreen.totalMonsterMissCnt;

        monsterSpellUsed.digitY = 440+19 ;
        monsterSpellUsed.plusWeight = 500;
        monsterSpellUsed.score = AirWaterScreen.totalSpellCnt;

        monsterSpellMiss.digitY = 320+15 ;
        monsterSpellMiss.plusWeight = 500;
        monsterSpellMiss.score = AirWaterScreen.totalSpellMissCnt;

        monsterSpellOnScreen.digitY = 200+27 ;
        monsterSpellOnScreen.plusWeight = 500;
        monsterSpellOnScreen.score = AirWaterScreen.totalSpellCnt - AirWaterScreen.totalSpellMissCnt- AirWaterScreen.totalMonsterHitCnt;

    }

    @Override
    public void render(float delta) {


        button();
        game.batch.begin();

        game.batch.draw(statsScreen, 0,0);
        if(showMute) game.batch.draw(mute, 1580, 830,100,100);

        monsterKills.render(game.batch);
        monsterFails.render(game.batch);
        monsterSpellUsed.render(game.batch);
        monsterSpellMiss.render(game.batch);
        monsterSpellOnScreen.render(game.batch);


        game.batch.end();

    }

    public void button()
    {

        if (Gdx.input.getX() >= 77 && Gdx.input.getX() <= 520 &&
                MyGdxGame.SCREEN_HEIGHT - Gdx.input.getY() >= 25
                && MyGdxGame.SCREEN_HEIGHT - Gdx.input.getY() <= 108) {
            if (Gdx.input.justTouched()) {
                this.dispose();
                SoundManager.click.play();
                game.setScreen(new EndScreen(game,soundState,status));
            }
        }

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

        SoundManager.end.dispose();
        SoundManager.click.dispose();

    }
}
