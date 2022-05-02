package game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Spell implements ForObject{
    Collision collision; // to check if there is a collision with monsters

    // spell variables starts
    public float spellX;
    public float spellY;
    public float spellWidth;
    public float spellHeight;
    public float spellSpeed = 100;
    public boolean spellRemove = false;
    public int spellType; // if the spell fire or freeze. (1 means fire. 0 means freeze)
    // spell variables ends

    // fire variables starts
    public float fireWidth = 100;
    public float fireHeight = 40;
    public float fireFrameDuration = 0.1f;
    public float fireStateTime = 0f;
    public int fireImgCnt = 6;
    Animation fireAnimation;
    TextureRegion fireReg;
    // fire variable ends

    // freeze variables starts
    public float freezeWidth = 100;
    public float freezeHeight = 40;
    public float freezeFrameDuration = 0.1f;
    public float freezeStateTime = 0f;
    public int freezeImgCnt = 7;
    Animation freezeAnimation;
    TextureRegion freezeReg;
    // freeze variable ends

    public Spell(float characterX, float characterY, float characterWidth, float characterHeight, boolean inWater) {
        if (inWater) {
            createFreezeAnimation();
            spellType = 0;
            spellWidth = freezeWidth;
            spellHeight = freezeHeight;
        }
        else {
            createFireAnimation();
            spellType = 1;
            spellWidth = fireWidth;
            spellHeight = fireHeight;
        }

        spellX = characterX + characterWidth;
        spellY = characterY + characterHeight / 2f - spellHeight / 2f;

        collision = new Collision(spellX, spellY, spellWidth, spellHeight);
    }

    @Override
    public void update() {
        spellX += spellSpeed * Gdx.graphics.getDeltaTime();
        collision.update(spellX, spellY, spellWidth, spellHeight);

        if (spellX >= Gdx.graphics.getWidth()) spellRemove = true;
    }

    @Override
    public void render(SpriteBatch batch) {
        if (spellType == 0) {
            renderFreezeAnimation(batch);
        }
        else {
            renderFireAnimation(batch);
        }
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

    public void renderFireAnimation(SpriteBatch batch) {
        fireStateTime += Gdx.graphics.getDeltaTime(); // to calculate how long the animation is showing
        fireStateTime %= (fireImgCnt * fireFrameDuration); // to make loop
        fireReg = (TextureRegion) fireAnimation.getKeyFrame(fireStateTime); // setting a texture for a specific time period

        batch.draw(fireReg, spellX, spellY, spellWidth / 2, spellHeight / 2, spellWidth,
                spellHeight,1,1,0); // drawing fire animation
    }

    public void createFreezeAnimation() {
        Array<TextureRegion> textureRegion = new Array<>(); // to store all the textures.
        for (int i = 1; i <= freezeImgCnt; i++) {
            Texture texture = new Texture("Freeze\\freeze" + i + ".png"); // creating new texture
            texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear); // filtering texture
            textureRegion.add(new TextureRegion(texture));// adding texture to texture region
        }
        freezeAnimation = new Animation<>(freezeFrameDuration, textureRegion); // creating animation
        freezeStateTime = 0f; // setting state time 0
    }

    public void renderFreezeAnimation(SpriteBatch batch) {
        freezeStateTime += Gdx.graphics.getDeltaTime(); // to calculate how long the animation is showing
        freezeStateTime %= (freezeImgCnt * freezeFrameDuration); // to make loop
        freezeReg = (TextureRegion) freezeAnimation.getKeyFrame(freezeStateTime); // setting a texture for a specific time period

        batch.draw(freezeReg, spellX, spellY, spellWidth / 2, spellHeight / 2, spellWidth,
                spellHeight,1,1,0); // drawing freeze animation
    }
}
