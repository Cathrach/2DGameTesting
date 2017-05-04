/**
 * Created by serinahu on 5/4/17.
 */

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public class Entity {
    // image representing character
    private Image sprite;
    private float xPos, yPos;
    private int width, height;
    private final float SPEED = 0.1f;
    // TODO: animations

    // constructor: make character at some position
    public Entity(float xPos, float yPos) throws SlickException {
        sprite = new Image("sprites/sample_sprite.png");
        this.xPos = xPos;
        this.yPos = yPos;
        width = sprite.getWidth();
        height = sprite.getHeight();
    }
    // init: set up animations?
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        // set up animations here
    }
    // render: draw the character
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        g.drawString("X: " + xPos + " Y: " + yPos, 20, 20);
        sprite.draw(xPos, yPos);
    }

    // update: check if keys are pressed -> change position; handle collisions/damage/teleports eventually
    public void update(GameContainer container, StateBasedGame game, int delta, WorldMap map) throws SlickException {
        Input input = container.getInput();
        if (input.isKeyDown(Input.KEY_UP) && !(map.isBlocked(xPos + width, yPos) || map.isBlocked(xPos, yPos))) {
            yPos -= delta * SPEED;
        } else if (input.isKeyDown(Input.KEY_DOWN) && !(map.isBlocked(xPos + width, yPos + height) || map.isBlocked(xPos, yPos + height))) {
            yPos += delta * SPEED;
        } else if (input.isKeyDown(Input.KEY_LEFT) && !(map.isBlocked(xPos, yPos + height) || map.isBlocked(xPos, yPos))) {
            xPos -= delta * SPEED;
        } else if (input.isKeyDown(Input.KEY_RIGHT) && !(map.isBlocked(xPos + width, yPos) || map.isBlocked(xPos + width, yPos + height))) {
            xPos += delta * SPEED;
        }
    }


}
