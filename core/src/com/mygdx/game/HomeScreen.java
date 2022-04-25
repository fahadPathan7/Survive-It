package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;

public class HomeScreen implements Screen {

    MyGdxGame game;

    Texture mainBackground;
    int mainBackgroundX ,mainBackgroundY;

    Texture continueGame ;
    int continueX , continueY;

    Texture gameName ;
    int gameNameX , gameNameY;

    private final float TEXT_WIDTH = 500;
    private final float TEXT_HEIGHT = 500;

    Texture character;
    int characterX , characterY;
    private final float CHARACTER_WIDTH = 140;
    private final float CHARACTER_HEIGHT = 240;

    private static final float FRAME_TIME = 1 / 20f;   // fps
    private float end_time;
    private Animation<TextureAtlas.AtlasRegion> run;



    public HomeScreen(MyGdxGame game) {

        this.game = game;

        mainBackgroundX = 0;
        mainBackgroundY = 0;
        mainBackground = new Texture("Background\\21.jpeg") ;

        gameNameX = 590;
        gameNameY = 420;
        gameName = new Texture("Background\\gamename.png");

        characterX = 760;
        characterY = 350;
        character =  new Texture("Background\\r1.png") ;

        continueX = 600;
        continueY = 10;
        continueGame =  new Texture("Background\\continue.png") ;


    }

    @Override
    public void show() {

        TextureAtlas charset = new TextureAtlas(Gdx.files.internal("Run\\run.atlas"));
        run = new Animation<>(FRAME_TIME, ((TextureAtlas) charset).findRegions("run"));
        run.setFrameDuration(FRAME_TIME);

        SoundManager.create();
        SoundManager.home.setLooping(true);
        SoundManager.home.setVolume(0.3f);     // 50% of main volume
        SoundManager.home.play();

    }

    @Override
    public void render(float delta) {

        ScreenUtils.clear(1, 1, 1, 1);
        //mainBackground = new Texture("Background\\21.jpeg") ;

        if(Gdx.input.isKeyPressed(Input.Keys.ENTER))
        {
            this.dispose();
            game.setScreen(new MainMenuScreen(game));
        }

        end_time += delta;
        TextureRegion currentFrame = run.getKeyFrame(end_time, true);

        Gdx.gl.glClearColor(0.0f, 0, 0.0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        game.batch.begin();

        game.batch.draw(mainBackground, mainBackgroundX, mainBackgroundY,mainBackground.getWidth(), Gdx.graphics.getHeight());
        game.batch.draw(gameName, gameNameX, gameNameY,TEXT_WIDTH, TEXT_HEIGHT);
        game.batch.draw(continueGame, continueX, continueY,TEXT_WIDTH, TEXT_HEIGHT);
        //game.batch.draw(character, 760, 390,108,180);
        game.batch.draw(currentFrame, characterX, characterY, CHARACTER_WIDTH, CHARACTER_HEIGHT);

        game.batch.end();
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
        SoundManager.home.dispose();
    }
}
