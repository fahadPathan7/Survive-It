package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import game.GameScreen;

public class MainMenuScreen implements Screen {
    private final float IMAGE_WIDTH = 100;
    private final float IMAGE_HEIGHT = 100;
    private final float SPEED = 250;
    float imageX = (float)Gdx.graphics.getWidth() / 2 - IMAGE_WIDTH / 2, imageY = (float)Gdx.graphics.getHeight() / 2 - IMAGE_HEIGHT / 2;

    Texture img;
    MyGdxGame game;
    public MainMenuScreen(MyGdxGame game) {
        this.game = game;
        img = new Texture("exit_button_on.png");
    }
    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 1, 0, 1);

        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            imageY += SPEED * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            imageY -= SPEED * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            imageX -= SPEED * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            imageX += SPEED * Gdx.graphics.getDeltaTime();
        }

        if (Gdx.input.getX() >= imageX && Gdx.input.getX() <= imageX + IMAGE_WIDTH && MyGdxGame.SCREEN_HEIGHT - Gdx.input.getY() >= imageY
                && MyGdxGame.SCREEN_HEIGHT - Gdx.input.getY() <= imageY + IMAGE_HEIGHT) {
            if (Gdx.input.isTouched()) {
                this.dispose();
                game.setScreen(new GameScreen(game));
            }
        }

        game.batch.begin();
        game.batch.draw(img, imageX, imageY, IMAGE_WIDTH, IMAGE_HEIGHT);
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
        //game.batch.dispose();
        img.dispose();
    }
}
