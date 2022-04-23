package game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Poison {
    Texture poisonImg;
    float poisonAnimationFrameDuration = 0.07f;
    Animation poisonAnimation;
    TextureRegion reg;
    float poisonStateTime = 0f;
    final float POISON_SPEED = 60;
    float height = Gdx.graphics.getHeight() / 2, width = 600;
    float x = Gdx.graphics.getWidth() + width / 2, y = 0;
    boolean remove = false;
    static int part = 1;

    public Poison() {
        part ^= 1;
        if (part == 1) y = Gdx.graphics.getHeight() / 2;

        Array<TextureRegion> textureRegion = new Array<>();
        for (int i = 1; i <= 6; i++) {
            Texture texture = new Texture("poison" + i + ".jpg");
            texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            textureRegion.add(new TextureRegion(texture));
        }
        for (int i = 6; i >= 1; i--) {
            Texture texture = new Texture("poison" + i + ".jpg");
            texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            textureRegion.add(new TextureRegion(texture));
        }
        poisonAnimation = new Animation(poisonAnimationFrameDuration, textureRegion);
    }
    public void update() {
        x -= POISON_SPEED * Gdx.graphics.getDeltaTime();
        if (x + width <= 0) remove = true;
    }
    public void render(SpriteBatch batch) {
        poisonStateTime += Gdx.graphics.getDeltaTime();
        poisonStateTime %= (poisonAnimationFrameDuration * 12);
        reg = (TextureRegion) poisonAnimation.getKeyFrame(poisonStateTime);

        batch.draw(reg,x,y,width / 2,height / 2,width,height,1,1,0);
    }
}
