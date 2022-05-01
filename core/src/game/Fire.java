package game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Fire implements ForObject{
    public float fireX;
    public float fireY;
    public float fireWidth = 100;
    public float fireHeight = 40;
    public float fireSpeed = 100;
    public float fireFrameDuration = 0.1f;
    public float fireStateTime = 0f;
    public int fireImgCnt = 6;
    Animation fireAnimation;
    TextureRegion fireReg;
    public boolean fireRemove = false;
    Collision collision;

    public Fire (float characterX, float characterY, float characterWidth, float characterHeight) {
        fireX = characterX + characterWidth;
        fireY = characterY + characterHeight / 2f - fireHeight / 2f;

        collision = new Collision(fireX, fireY, fireWidth, fireHeight);
        createFireAnimation();
    }

    @Override
    public void update() {
        fireX += fireSpeed * Gdx.graphics.getDeltaTime();
        collision.update(fireX, fireY, fireWidth, fireHeight);

        if (fireX >= Gdx.graphics.getWidth()) fireRemove = true;
    }

    @Override
    public void render(SpriteBatch batch) {
        fireStateTime += Gdx.graphics.getDeltaTime(); // to calculate how long the animation is showing
        fireStateTime %= (fireImgCnt * fireFrameDuration); // to make loop
        fireReg = (TextureRegion) fireAnimation.getKeyFrame(fireStateTime); // setting a texture for a specific time period

        batch.draw(fireReg, fireX, fireY, fireWidth / 2, fireHeight / 2, fireWidth,
                fireHeight,1,1,0); // drawing fire animation
    }

    @Override
    public Collision getCollision() {
        return collision;
    }

    public void createFireAnimation() {
        Array<TextureRegion> textureRegion = new Array<>(); // to store all the textures.
        for (int i = 1; i <= fireImgCnt; i++) {
            Texture texture = new Texture("Fire\\fire" + i + ".png"); // creating new texture
            texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear); // filtering texture
            textureRegion.add(new TextureRegion(texture));// adding texture to texture region
        }
        fireAnimation = new Animation<>(fireFrameDuration, textureRegion); // creating animation
        fireStateTime = 0f; // setting state time 0
    }
}
