package game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Blast {
    public float blastX;
    public float blastY;
    public float blastWidth = 80f;
    public float blastHeight = 80f;
    public float blastFrameDuration = 0.09f;
    public float blastStateTime = 0f;
    public int blastImgCnt = 8;
    Animation blastAnimation;
    TextureRegion blastReg;
    public boolean blastRemove = false;

    public Blast(float x, float y) {
        blastX = x;
        blastY = y;

        createBlastAnimation();
    }

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

    public void renderBlastAnimation(SpriteBatch batch) {
        blastStateTime += Gdx.graphics.getDeltaTime(); // to calculate how long the animation is showing
        blastReg = (TextureRegion) blastAnimation.getKeyFrame(blastStateTime); // setting a texture for a specific time period

        batch.draw(blastReg, blastX, blastY, blastWidth / 2, blastHeight / 2, blastWidth,
                blastHeight,1,1,0); // drawing blast animation

        if (blastStateTime >= (float)blastImgCnt * blastFrameDuration) blastRemove = true;
    }
}
