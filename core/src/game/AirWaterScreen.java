package game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.SoundManager;

import java.util.ArrayList;
import java.util.Random;

public class AirWaterScreen implements Screen {
    MyGdxGame game; // reference of MyGdxGame
    Character character; // reference of Character
    Random rand; // for taking random value

    // bullet starts
    ArrayList<Bullet> bullets;
    private final float BULLET_MIN_TIME = 5f, BULLET_MAX_TIME = 10f;
    public float bulletLaunchTime = 0f;
    // bullet ends

    // background starts
    Texture background1, background2;
    public final int BACKGROUND_HORIZONTAL_SPEED = 120;
    int background1_x, background2_x;
    // background ends

    // poison animation starts
    float poisonLaunchTime = 5f;
    ArrayList<Poison> poisons;
    public final float POISON_MIN_TIME = 40f, POISON_MAX_TIME = 60f;
    // poison animation ends

    public AirWaterScreen(MyGdxGame game) {
        this.game = game;
        rand = new Random();

        // creating character object
        character = new Character();

        bullets = new ArrayList<>();

        // background starts
        background1 = new Texture("Background\\water_air.jpeg");
        background2 = new Texture("Background\\water_air.jpeg");
        background1_x = 0;
        background2_x = 5000; // initial huge value to avoid colliding
        // background ends

        // poison animation starts
        //poisons = new ArrayList<>();
        // poison animation ends
    }
    @Override
    public void show() {
        SoundManager.create();
        SoundManager.gameLevel3.setLooping(true);
        SoundManager.gameLevel3.setVolume(0.3f);     // 30% of main volume
        SoundManager.gameLevel3.play();

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1, 1, 1, 1);

        // updating character
        character.update();

        // bullet starts
        if (bulletLaunchTime <= 0) {
            bulletLaunchTime = rand.nextFloat() * (BULLET_MAX_TIME - BULLET_MIN_TIME) + BULLET_MIN_TIME;
            bullets.add(new Bullet());
        }
        bulletLaunchTime -= delta;

        ArrayList<Bullet> bulletToRemove = new ArrayList<>();
        for (Bullet bullet : bullets) {
            bullet.update();
            if (bullet.remove) {
                bulletToRemove.add(bullet);
            }
            // checking if bullet is colliding with character
            if (character.getCollision().isCollide(bullet.getCollision())) {
                bulletToRemove.add(bullet);
            }
        }
        bullets.removeAll(bulletToRemove);

        // checking if bullet is colliding with character
        bulletToRemove.clear();
        for (Bullet bullet : bullets) {
            if (character.getCollision().isCollide(bullet.getCollision())) {
                bulletToRemove.add(bullet);
            }
        }
        bullets.removeAll(bulletToRemove);
        // bullet ends

        // background starts
        background1_x -= BACKGROUND_HORIZONTAL_SPEED * delta;
        background2_x -= BACKGROUND_HORIZONTAL_SPEED * delta;
        if (background1_x + background1.getWidth() < Gdx.graphics.getWidth())
            background2_x = background1_x + background1.getWidth();
        if (background2_x + background2.getWidth() < Gdx.graphics.getWidth())
            background1_x = background2_x + background2.getWidth();
        // background ends

        // poison animation starts
//        poisonLaunchTime -= delta;
//        if (poisonLaunchTime <= 0) {
//            poisons.add(new Poison());
//            poisonLaunchTime = rand.nextFloat() * (POISON_MAX_TIME - POISON_MIN_TIME) + POISON_MIN_TIME;
//        }
//
//        ArrayList<Poison> poisonToRemove = new ArrayList<>();
//        for (Poison poison : poisons) {
//            poison.update();
//            if (poison.poisonRemove) poisonToRemove.add(poison);
//        }
//        poisons.removeAll(poisonToRemove);
        // poison animation ends

        game.batch.begin();

        // background starts
        game.batch.draw(background1, background1_x, 0, background1.getWidth(), Gdx.graphics.getHeight());
        game.batch.draw(background2, background2_x, 0, background2.getWidth(), Gdx.graphics.getHeight());
        // background ends

        // rendering character
        character.render(game.batch);

        // bullet starts
        for (Bullet bullet : bullets) {
            bullet.render(game.batch);
        }
        // bullet ends

        // for poison animation
//        for (Poison poison : poisons) {
//            poison.render(game.batch);
//        }

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
        SoundManager.gameLevel3.dispose();


    }
}
