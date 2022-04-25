package game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

public class Monster implements ForObject{
    Collision collision;
    Random rand;

    public float monsterVerticalSpeed = 60, monsterHorizontalSpeed = 250;
    public float monsterDistanceCorrection = monsterVerticalSpeed * Gdx.graphics.getDeltaTime();
    public float monsterWidth = 120, monsterHeight = 56.64f; // Change
    //public float bulletWidth = 240, bulletHeight = 200;  default
    //public float bulletWidth = 279, bulletHeight = 150;  tortoise
    //public float bulletWidth = 160, bulletHeight = 150;  bluebird
    public float monsterMaxDistance = (float)Gdx.graphics.getHeight() / 2 - monsterHeight, monsterMinDistance = 0;
    public float monsterDirection = 1;
    float monsterX = Gdx.graphics.getWidth(), monsterY;
    public String[] monsterImg = {"Monster\\mons1.png", "Monster\\mons2.png", "Monster\\mons3.png", "Monster\\mons4.png",
            "Monster\\mons5.png", "Monster\\mons6.png"};
    Texture texture;
    public boolean remove = false;

    public Monster() {
        rand = new Random();
        monsterY = rand.nextFloat() * (Gdx.graphics.getHeight() - monsterWidth);

        int idx = 0;
        if (monsterY > monsterMaxDistance + monsterDistanceCorrection) {
            idx = rand.nextInt(4);
        }
        else idx = rand.nextInt(2) + 4;
        texture = new Texture(monsterImg[idx]);

        collision = new Collision(monsterX, monsterY, monsterWidth, monsterHeight);
    }

    @Override
    public void update() {
        if (monsterY >= monsterMaxDistance && monsterY <= monsterMaxDistance + monsterDistanceCorrection) {
            monsterDirection *= -1;
            monsterY = monsterMaxDistance;
        }
        if (monsterY <= monsterMinDistance) {
            monsterDirection *= -1;
            monsterY = monsterMinDistance;
        }

        if (monsterY <= monsterMaxDistance) {
            monsterY += monsterDirection * monsterVerticalSpeed * Gdx.graphics.getDeltaTime();
        }
        monsterX -= monsterHorizontalSpeed * Gdx.graphics.getDeltaTime();
        //System.out.println(bulletY); //! test
        if (monsterX + monsterWidth <= 0) {
            remove = true;
        }

        collision.update(monsterX, monsterY, monsterWidth, monsterHeight);
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(texture, monsterX, monsterY, monsterWidth, monsterHeight);
    }

    @Override
    public Collision getCollision() {
        return collision;
    }
}
