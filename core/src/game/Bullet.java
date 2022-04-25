package game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

public class Bullet implements ForObject{
    Collision collision;
    Random rand;

    public float bulletVerticalSpeed = 60, bulletHorizontalSpeed = 200;
    public float bulletDistanceCorrection = bulletVerticalSpeed * Gdx.graphics.getDeltaTime();
    public float bulletWidth = 150, bulletHeight = 70.8f; // Change
    //public float bulletWidth = 240, bulletHeight = 200;  default
    //public float bulletWidth = 279, bulletHeight = 150;  tortoise
    //public float bulletWidth = 160, bulletHeight = 150;  bluebird
    public float bulletMaxDistance = (float)Gdx.graphics.getHeight() / 2 - bulletHeight, bulletMinDistance = 0;
    public float bulletDirection = 1;
    float bulletX = Gdx.graphics.getWidth(), bulletY;
    public Texture bulletImg;
    public boolean remove = false;

    public Bullet() {
        rand = new Random();
        bulletImg = new Texture("Monster\\mons2.png");    // change

        collision = new Collision(bulletX, bulletY, bulletWidth, bulletHeight);

        bulletY = rand.nextFloat() * (Gdx.graphics.getHeight() - bulletWidth);
    }

    @Override
    public void update() {
        if (bulletY >= bulletMaxDistance && bulletY <= bulletMaxDistance + bulletDistanceCorrection) {
            bulletDirection *= -1;
            bulletY = bulletMaxDistance;
        }
        if (bulletY <= bulletMinDistance) {
            bulletDirection *= -1;
            bulletY = bulletMinDistance;
        }

        if (bulletY >= bulletMinDistance && bulletY <= bulletMaxDistance) {
            bulletY += bulletDirection * bulletVerticalSpeed * Gdx.graphics.getDeltaTime();
        }
        bulletX -= bulletHorizontalSpeed * Gdx.graphics.getDeltaTime();
        //System.out.println(bulletY); //! test
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
