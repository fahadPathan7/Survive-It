package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class HomeScreen implements Screen {

    MyGdxGame game;

    // background rendering starts
    Texture[] mainBackground = new Texture[6];
    int state = 0 ;
    int mainBackgroundX = 0;
    int mainBackgroundY = 0;
    public double lightSpeed = .25;
    public double value = 30;
    // background rendering ends

    // play button starts
    Texture playButton ;
    int playButtonX = 1500 ;
    int playButtonY = 30 ;
    // play button ends


    // character variables starts
    public float characterX = 550; // x-axis of character
    public float characterY = 285; // y-axis of character
    public float characterWaterWidth = 100; // character width when in water
    public float characterWidth = characterWaterWidth; // initially character is in water
    public float characterHeight = 140; // character height is always same
    // character variable ends

    // run animation starts
    float runStateTime = 0f; // to calculate state time for running animation
    Animation runAnimation; // to create running animation
    TextureRegion runReg; // for animate every texture
    public int runImgCnt = 10; // number of images for run animation
    public float runFrameDuration = 0.075f; // frame duration of every texture
    // run animation ends

    // sound running starts
    public boolean soundState = true;
    // sound running ends



    public HomeScreen(MyGdxGame game) {
        this.game = game;
    }
//    public HomeScreen(MyGdxGame game, boolean soundState) {
//
//        this.game = game;
//        this.soundState = soundState;
//    }

    @Override
    public void show() {


        mainBackground[0] = new Texture("Home\\1.png") ;
        mainBackground[1] = new Texture("Home\\2.png") ;
        mainBackground[2] = new Texture("Home\\3.png") ;
        mainBackground[3] = new Texture("Home\\4.png") ;
        mainBackground[4] = new Texture("Home\\5.png") ;
        mainBackground[5] = new Texture("Home\\6.png") ;


        playButton = new Texture("Home\\check.png");

        SoundManager.create();
        SoundManager.home.setLooping(true);
        SoundManager.home.setVolume(0.3f);
        SoundManager.click.setVolume(0.3f);

        if(soundState)SoundManager.home.play();

        createRunAnimation();

    }

    @Override
    public void render(float delta) {

          // * Enter key Access
//        if(Gdx.input.isKeyPressed(Input.Keys.ENTER))
//        {
//            this.dispose();
//            //game.setScreen(new MainMenuScreen(game));
//            game.setScreen(new GameMenuScreen(game));
//        }

        if (Gdx.input.getX() >= 1500 && Gdx.input.getX() <= 1500+150 &&
                MyGdxGame.SCREEN_HEIGHT - Gdx.input.getY() >= 30
                && MyGdxGame.SCREEN_HEIGHT - Gdx.input.getY() <= 30+150) {
            if (Gdx.input.justTouched()) {
                this.dispose();
                SoundManager.click.play();
                game.setScreen(new GameMenuScreen(game,soundState));;
            }
        }

        game.batch.begin();

        game.batch.draw(mainBackground[state], mainBackgroundX, mainBackgroundY);

        changeBackGround();

        game.batch.draw(playButton, playButtonX, playButtonY,150,150);

        renderRunAnimation(game.batch);

        game.batch.end();
    }

    public void changeBackGround()
    {
        if(value < 0) value = 30;

        if(value <= 30 && value > 25) state = 0;

        else if(value <= 25 && value > 20) state = 1 ;

        else if(value <= 20 && value > 15) state = 2 ;

        else if(value <= 15 && value > 10) state = 3 ;

        else if(value <= 10 && value > 5) state = 4 ;

        else if(value <= 5 && value > 0)  state = 5 ;

        value = value - lightSpeed;
    }

    public void createRunAnimation() {
        Array<TextureRegion> textureRegion = new Array<>(); // to store all the textures.
        for (int i = 1; i <= runImgCnt; i++) {
            Texture texture = new Texture("Run\\r" + i + ".png"); // creating new texture
            texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear); // filtering texture
            textureRegion.add(new TextureRegion(texture)); // adding texture to texture region
        }
        runAnimation = new Animation<>(runFrameDuration, textureRegion); // creating animation.
        runStateTime = 0f; // setting state time 0
    }

    public void renderRunAnimation(SpriteBatch batch) {
        runStateTime += Gdx.graphics.getDeltaTime(); // to calculate how long the character is running.
        runStateTime %= (runFrameDuration * runImgCnt); // modded for looping the animation
        runReg = (TextureRegion) runAnimation.getKeyFrame(runStateTime); // setting a texture for specific time period.`

        batch.draw(runReg, characterX, characterY, characterWidth / 2, characterHeight / 2,
                characterWidth,characterHeight,1,1,0); // drawing run animation
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
        SoundManager.click.dispose();
        playButton.dispose();
        mainBackground[0].dispose();
        mainBackground[1].dispose();
        mainBackground[2].dispose();
        mainBackground[3].dispose();
        mainBackground[4].dispose();
        mainBackground[5].dispose();


    }
}
