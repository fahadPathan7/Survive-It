package Intro;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.MyGdxGame;
import game.AirWaterScreen;


public class IntroScreen4 implements Screen {

    MyGdxGame game;
    Texture background4;

    public IntroScreen4 (MyGdxGame game) {

        this.game = game;
        background4 = new Texture("Intro\\4.png");

    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        //ScreenUtils.clear(1, 1, 1, 1);

        if (Gdx.input.getX() >= 1430 && Gdx.input.getX() <= 1630 &&
                MyGdxGame.SCREEN_HEIGHT - Gdx.input.getY() >= 50
                && MyGdxGame.SCREEN_HEIGHT - Gdx.input.getY() <= 117) {
            if (Gdx.input.isTouched()) {
                this.dispose();
                game.setScreen(new AirWaterScreen(game));
            }
        }

        if (Gdx.input.getX() >= 1544 && Gdx.input.getX() <= 1666 &&
                MyGdxGame.SCREEN_HEIGHT - Gdx.input.getY() >= 423
                && MyGdxGame.SCREEN_HEIGHT - Gdx.input.getY() <= 558) {
            if (Gdx.input.justTouched()) {
                this.dispose();
                game.setScreen(new IntroScreen5(game));

            }
        }

        if (Gdx.input.getX() >= 30 && Gdx.input.getX() <= 155 &&
                MyGdxGame.SCREEN_HEIGHT - Gdx.input.getY() >= 423
                && MyGdxGame.SCREEN_HEIGHT - Gdx.input.getY() <= 558) {
            if (Gdx.input.justTouched()) {
                this.dispose();
                game.setScreen(new IntroScreen3(game));
            }
        }



        game.batch.begin();

        game.batch.draw(background4,0,0);

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


    }
}
