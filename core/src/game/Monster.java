package game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

public class Monster implements ForObject{
    Collision collision; // collision object for checking with any other objects
    Random rand; // for creating random value


    public float monsterX = Gdx.graphics.getWidth(); // x-axis of monsters
    public float monsterY; // y-axis of monsters (randomly created in constructor)
    public float monsterVerticalSpeed = 60f; // vertical speed of monster
    public float monsterHorizontalSpeed = 250f; // horizontal speed of monster
    public float monsterWidth = 120f; // to define monster width
    public float monsterHeight = 56.64f; // to define monster height
    public float airMonsterSafeDistanceFromWater = 20f; // air monsters safe distance from water
    public float waterMonsterSafeDistanceFromAir = 10f; // water monsters safe distance from air
    public float waterMonsterMinDistance = 0f; // min distance of water monsters. (the lowest point they can go)
    public float waterMonsterMaxDistance = Gdx.graphics.getHeight() / 2f - monsterHeight; // max distance of
        // water monsters. (up to which they can go)
    public float airMonsterMinDistance = Gdx.graphics.getHeight() / 2f; // min distance of air monsters.
    public float airMonsterSafeDistanceFromUp = 65f;
    public float airMonsterMaxDistance = Gdx.graphics.getHeight() - airMonsterSafeDistanceFromUp; // max distance of air monsters
    public float monsterDirection = 1f; // direction of water monsters. if they are going upward or downward.
    public int waterMonsterCnt = 4; // count of water monsters
    public int airMonsterCnt = 4; // count of air monsters
    public String[] monsterImgLoc = {"Monster\\mons1.png", "Monster\\mons2.png", "Monster\\mons3.png",
            "Monster\\mons4.png", "Monster\\mons5.png", "Monster\\mons6.png",
            "Monster\\mons7.png", "Monster\\mons8.png"}; // image location of monsters (water & air)
    Texture monsTexture; // texture for monster images
    public boolean remove = false; // a boolean value to remove monsters after collision with character
        // or when x-axis < 0


    /*
    default constructor
     */
    public Monster() {
        rand = new Random(); // initializing random object

        monsterY = rand.nextFloat() * (Gdx.graphics.getHeight() - monsterHeight); // creating random y-axis

        selectMonsterTexture(); // for selecting random monster texture.

        collision = new Collision(monsterX, monsterY, monsterWidth, monsterHeight); // creating collision object
    }

    /*
    updates monster's x and y-axis
     */
    @Override
    public void update() {
        // changing directing for water monsters.
        if (monsterY >= waterMonsterMaxDistance - waterMonsterSafeDistanceFromAir &&
                monsterY <= waterMonsterMaxDistance) {
            monsterDirection *= -1;
            monsterY = waterMonsterMaxDistance - waterMonsterSafeDistanceFromAir;
        }
        else if (monsterY <= waterMonsterMinDistance) {
            monsterDirection *= -1;
            monsterY = waterMonsterMinDistance;
        }

        // changing monster y-axis for water monsters
        if (monsterY <= waterMonsterMaxDistance) {
            monsterY += monsterDirection * monsterVerticalSpeed * Gdx.graphics.getDeltaTime();
        }

        monsterX -= monsterHorizontalSpeed * Gdx.graphics.getDeltaTime(); // changing x-axis for all monsters.

        // if monster is out of the screen it should be removed.
        if (monsterX + monsterWidth <= 0) {
            remove = true;
        }

        collision.update(monsterX, monsterY, monsterWidth, monsterHeight); // updating collision object.
    }

    /*
    used to draw monsters.
     */
    @Override
    public void render(SpriteBatch batch) {
        batch.draw(monsTexture, monsterX, monsterY, monsterWidth, monsterHeight); // drawing monster.
    }

    /*
    used to check if the monster is colliding with any other object
     */
    @Override
    public Collision getCollision() {
        return collision;
    }

    /*
    selecting random monster according to y-axis.
    if y-axis is in the air, the monster will be air monster.
    if y-axis is in the water, the monster will be water monster.
     */
    public void selectMonsterTexture() {
        // the conditions are to make sure that no monsters are crossing their limit (water monsters their upper
        // limit and air monsters their lower limit). e.g., water monsters may get into
        // air for their speed (a short distance. like 5pixel/ 6pixel) and make a collision.
        if (monsterY > airMonsterMinDistance &&
                monsterY <= airMonsterMinDistance + airMonsterSafeDistanceFromWater) {
            monsterY = airMonsterMinDistance + airMonsterSafeDistanceFromWater;
        }
        else if (monsterY > waterMonsterMaxDistance - waterMonsterSafeDistanceFromAir &&
                monsterY <= waterMonsterMaxDistance + monsterHeight) {
            monsterY = waterMonsterMaxDistance - waterMonsterSafeDistanceFromAir;
        }

        // the condition is to make sure that air monsters are not crossing their maximum distance.
        if (monsterY + monsterHeight > airMonsterMaxDistance) {
            monsterY = airMonsterMaxDistance - monsterHeight;
        }

        int idx;
        // if the condition is true. the monster is in the water else in air.
        if (monsterY <= waterMonsterMaxDistance) idx = rand.nextInt(waterMonsterCnt) + airMonsterCnt;
        else idx = rand.nextInt(airMonsterCnt); // index from 0 to airMonsterCnt - 1.

        if (idx == 0) monsterWidth = 150.75f; // to adjust width of first monster

        monsTexture = new Texture(monsterImgLoc[idx]); // taking texture according to index.
    }
}