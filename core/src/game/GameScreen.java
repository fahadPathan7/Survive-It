package game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.MyGdxGame;

import java.util.ArrayList;
import java.util.Random;

public class GameScreen implements Screen {
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

    // animation starts
    float walkingStateTime = 0f;
    Animation walkAnimation;
    Texture walkSheet;
    TextureRegion reg;
    public int walkingAnimationRows = 2, walkingAnimationCols = 5;
    public float walkingAnimationFrameDuration = 0.1f;
    // animation ends
    public GameScreen(MyGdxGame game) {
        this.game = game;
        rand = new Random();

        bullets = new ArrayList<Bullet>();

        character = new Character();

        // background starts
        background1 = new Texture("background1.png");
        background2 = new Texture("background2.png");
        background1_x = 0;
        background2_x = 5000; // initial huge value to avoid colliding
        // background ends

        // animation starts
        walkSheet = new Texture("run1.png");

        TextureRegion[][] walkingTmp = TextureRegion.split(walkSheet,
                walkSheet.getWidth() / walkingAnimationCols,
                walkSheet.getHeight() / walkingAnimationRows);

        TextureRegion[] walkFrames = new TextureRegion[walkingAnimationRows * walkingAnimationCols];
        int index = 0;
        for (int i = 0; i < walkingAnimationRows; i++) {
            for (int j = 0; j < walkingAnimationCols; j++) {
                walkFrames[index++] = walkingTmp[i][j];
            }
        }
        walkAnimation = new Animation<TextureRegion>(walkingAnimationFrameDuration, walkFrames);
        walkingStateTime = 0f;
        // animation ends
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1, 1, 1, 1);

        // animation starts
        walkingStateTime += delta;
        walkingStateTime %= (walkingAnimationFrameDuration * 10);
        reg = (TextureRegion) walkAnimation.getKeyFrame(walkingStateTime);
        // animation ends

        // bullet starts
        if (bulletLaunchTime <= 0) {
            bulletLaunchTime = rand.nextFloat(BULLET_MAX_TIME - BULLET_MIN_TIME) + BULLET_MIN_TIME;
            bullets.add(new Bullet());
        }
        bulletLaunchTime -= delta;

        ArrayList<Bullet> bulletToRemove = new ArrayList<>();
        for (Bullet bullet : bullets) {
            bullet.update(delta);
            if (bullet.remove) {
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
        if (background1_x + background1.getWidth() < Gdx.graphics.getWidth()) background2_x = background1_x + background1.getWidth();
        if (background2_x + background2.getWidth() < Gdx.graphics.getWidth()) background1_x = background2_x + background2.getWidth();
        // background ends

        //character.update(); // updating character

        game.batch.begin();

        // background starts
        game.batch.draw(background1, background1_x, 0, background1.getWidth(), Gdx.graphics.getHeight());
        game.batch.draw(background2, background2_x, 0, background2.getWidth(), Gdx.graphics.getHeight());
        // background ends

        // bullet starts
        for (Bullet bullet : bullets) {
            bullet.render(game.batch);
        }
        // bullet ends

        //character.render(game.batch); // rendering character

        // for animation
        game.batch.draw(reg,100,100,50/2,100/2,50,100,3,2,0);

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
