package game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Character {
    float characterX = 50, characterY = 20;
    public float characterWidth = 120, characterHeight = 250;
    private final float CHARACTER_SPEED = 200;
    Texture characterImage;
    Collision collision;


    public Character() {
        characterImage = new Texture("ani3.png");
        collision = new Collision(characterX, characterY, characterWidth, characterHeight);
    }

    public void update() {
        // changing character position
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            characterY += CHARACTER_SPEED * Gdx.graphics.getDeltaTime();
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            characterY -= CHARACTER_SPEED * Gdx.graphics.getDeltaTime();
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            characterX -= CHARACTER_SPEED * Gdx.graphics.getDeltaTime();
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            characterX += CHARACTER_SPEED * Gdx.graphics.getDeltaTime();
        }

        // making boundary of character
        if (characterX <= 0) characterX = 0;
        if (characterX + characterWidth >= Gdx.graphics.getWidth()) characterX = Gdx.graphics.getWidth() - characterWidth;
        if (characterY <= 0) characterY = 0;
        if (characterY + characterHeight >= Gdx.graphics.getHeight()) characterY = Gdx.graphics.getHeight() - characterHeight;

        // updating collision
        collision.update(characterX, characterY, characterWidth, characterHeight);
    }

    public void render(SpriteBatch batch) {
        batch.draw(characterImage, characterX, characterY, characterWidth, characterHeight);
    }

    public Collision getCollision() {
        return collision;
    }
}
