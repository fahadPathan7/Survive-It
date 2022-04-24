package game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Bullet implements ForObject{
    Collision collision;

    public final float BULLET_VERTICAL_SPEED = 60, BULLET_HORIZONTAL_SPEED = 91;
    public float bulletWidth = 240, bulletHeight = 200;             // Change
    //public float bulletWidth = 240, bulletHeight = 200;  default
    //public float bulletWidth = 279, bulletHeight = 150;  tortoise
    //public float bulletWidth = 160, bulletHeight = 150;  bluebird
    public final float BULLET_MAX_DISTANCE = (float)Gdx.graphics.getHeight() / 2 - bulletHeight, BULLET_MIN_DISTANCE = 0;
    public float bulletDirection = 1;
    float bulletX = Gdx.graphics.getWidth(), bulletY = 10f;
    public Texture bulletImg;
    public boolean remove = false;

    public Bullet() {
        bulletImg = new Texture("Monster\\mons2.png");    // change

        collision = new Collision(bulletX, bulletY, bulletWidth, bulletHeight);
    }

    @Override
    public void update() {
        if (bulletY > BULLET_MAX_DISTANCE) bulletDirection *= -1;
        if (bulletY < BULLET_MIN_DISTANCE) bulletDirection *= -1;

        bulletY += bulletDirection * BULLET_VERTICAL_SPEED * Gdx.graphics.getDeltaTime();
        bulletX -= BULLET_HORIZONTAL_SPEED * Gdx.graphics.getDeltaTime();

        if (bulletX + bulletWidth <= 0) {
            remove = true;
        }

        collision.update(bulletX, bulletY, bulletWidth, bulletHeight);
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(bulletImg, bulletX, bulletY, bulletWidth, bulletHeight);
    }

    @Override
    public Collision getCollision() {
        return collision;
    }
}
