package game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Poison implements ForObject {
    float poisonFrameDuration = 0.07f;
    Animation poisonAnimation;
    TextureRegion poisonReg;
    float poisonStateTime = 0f;
    final float POISON_SPEED = 60;
    float poisonHeight = Gdx.graphics.getHeight() / 2, poisonWidth = 600;
    float poisonX = Gdx.graphics.getWidth() + poisonWidth / 2, poisonY = 0;
    boolean poisonRemove = false;
    static int poisonPart = 1;
    Collision collision;

    public Poison() {
        collision = new Collision(poisonX, poisonY, poisonWidth, poisonHeight);

        poisonPart ^= 1;
        if (poisonPart == 1) poisonY = Gdx.graphics.getHeight() / 2;

        // poison animation starts
        Array<TextureRegion> textureRegion = new Array<>();
        for (int i = 1; i <= 6; i++) {
            Texture texture = new Texture("Poison\\poison" + i + ".jpg");
            texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            textureRegion.add(new TextureRegion(texture));
        }
        for (int i = 6; i >= 1; i--) {
            Texture texture = new Texture("Poison\\poison" + i + ".jpg");
            texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            textureRegion.add(new TextureRegion(texture));
        }
        poisonAnimation = new Animation(poisonFrameDuration, textureRegion);
        // poison animation ends
    }

    @Override
    public void update() {
        poisonX -= POISON_SPEED * Gdx.graphics.getDeltaTime();
        if (poisonX + poisonWidth <= 0) poisonRemove = true;

        collision.update(poisonX, poisonY, poisonWidth, poisonHeight);
    }

    @Override
    public void render(SpriteBatch batch) {
        poisonStateTime += Gdx.graphics.getDeltaTime();
        poisonStateTime %= (poisonFrameDuration * 12);
        poisonReg = (TextureRegion) poisonAnimation.getKeyFrame(poisonStateTime);

        batch.draw(poisonReg, poisonX, poisonY, poisonWidth / 2, poisonHeight / 2, poisonWidth,
                poisonHeight,1,1,0);
    }
    @Override
    public Collision getCollision() {
        return collision;
    }
}
