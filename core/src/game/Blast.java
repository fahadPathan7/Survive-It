package game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;


/*
this class is used for,
1. creating and showing blast animation for a specific time period.
 */
public class Blast {
    public float blastX; // blast x-axis
    public float blastY; // blast y-axis
    public float blastWidth = 80f; // blast width
    public float blastHeight = 80f; // blast height
    public float blastFrameDuration = 0.09f; // frame duration for every texture
    public float blastStateTime = 0f; // state time
    public int blastImgCnt = 8; // no of textures for blast animation
    Animation blastAnimation; // to create animation
    TextureRegion blastReg; // to store texture
    public boolean blastRemove = false; // to check if the animation should remove or not.


    /*
    default constructor
     */
    public Blast(float x, float y) {
        blastX = x; // setting x-axis
        blastY = y; // setting y-axis

        createBlastAnimation(); // creating animation
    }


    /*
    used to create blast animation.
     */
    public void createBlastAnimation() {
        Array<TextureRegion> textureRegion = new Array<>(); // to store all the textures.
        for (int i = 1; i <= blastImgCnt; i++) {
            Texture texture = new Texture("Blast\\blast" + i + ".png"); // creating new texture
            texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear); // filtering texture
            textureRegion.add(new TextureRegion(texture));// adding texture to texture region
        }
        blastAnimation = new Animation<>(blastFrameDuration, textureRegion); // creating animation
        blastStateTime = 0f; // setting state time 0
    }


    /*
    used to draw blast animation.
     */
    public void renderBlastAnimation(SpriteBatch batch) {
        blastStateTime += Gdx.graphics.getDeltaTime(); // to calculate how long the animation is showing
        blastReg = (TextureRegion) blastAnimation.getKeyFrame(blastStateTime); // setting a texture for a specific time period

        batch.draw(blastReg, blastX, blastY, blastWidth / 2, blastHeight / 2, blastWidth,
                blastHeight,1,1,0); // drawing blast animation

        if (blastStateTime >= (float)blastImgCnt * blastFrameDuration) blastRemove = true; // if time is over it should be removed.
    }
}
