package game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface ForObject {
    public void update();

    public void render(SpriteBatch batch);

    public Collision getCollision();
}
