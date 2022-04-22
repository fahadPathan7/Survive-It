package game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Bullet {
    Collision collision;

    public final float BULLET_VERTICAL_SPEED = 60, BULLET_HORIZONTAL_SPEED = 91;
    public float bulletWidth = 50, bulletHeight = 50;
    public final float BULLET_MAX_DISTANCE = (float)Gdx.graphics.getHeight() / 2 - bulletHeight, BULLET_MIN_DISTANCE = 0;
    public float bulletDirection = 1;
    float bulletX = Gdx.graphics.getWidth(), bulletY = 10f;
    public Texture bulletImg;
    public boolean remove = false;

    public Bullet() {
        bulletImg = new Texture("badlogic.jpg");

        collision = new Collision(bulletX, bulletY, bulletWidth, bulletHeight);
    }

    public void update(float delta) {
        if (bulletY > BULLET_MAX_DISTANCE) bulletDirection *= -1;
        if (bulletY < BULLET_MIN_DISTANCE) bulletDirection *= -1;

        bulletY += bulletDirection * BULLET_VERTICAL_SPEED * delta;
        bulletX -= BULLET_HORIZONTAL_SPEED * delta;

        if (bulletX + bulletWidth <= 0) {
            remove = true;
        }

        collision.update(bulletX, bulletY, bulletWidth, bulletHeight);
    }

    public void render(SpriteBatch batch) {
        batch.draw(bulletImg, bulletX, bulletY, bulletWidth, bulletHeight);
    }

    public Collision getCollision() {
        return collision;
    }
}
