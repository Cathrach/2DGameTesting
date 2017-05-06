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
    private BattleEntity player;
    // TODO: animations

    // constructor: make character at some position
    public Entity(float xPos, float yPos, BattleEntity battleEntity) throws SlickException {
        sprite = new Image("sprites/sample_sprite.png");
        this.xPos = xPos;
        this.yPos = yPos;
        width = sprite.getWidth();
        height = sprite.getHeight();
        player = battleEntity;
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

    @Override
    public String toString() {
        return "Entity{" +
                "sprite=" + sprite +
                ", xPos=" + xPos +
                ", yPos=" + yPos +
                ", width=" + width +
                ", height=" + height +
                ", SPEED=" + SPEED +
                ", player=" + player +
                '}';
    }

    // update: check if keys are pressed -> change position; handle collisions/damage/teleports eventually
    public void update(GameContainer container, StateBasedGame game, int delta, Map map) throws SlickException {
        Input input = container.getInput();
        if (input.isKeyDown(Input.KEY_UP) && !(map.isBlocked(xPos + width, yPos) || map.isBlocked(xPos, yPos) || yPos <= 0)) {
            yPos -= delta * SPEED;
        } else if (input.isKeyDown(Input.KEY_DOWN) && !(map.isBlocked(xPos + width, yPos + height) || map.isBlocked(xPos, yPos + height) || yPos >= map.getHeight())) {
            yPos += delta * SPEED;
        } else if (input.isKeyDown(Input.KEY_LEFT) && !(map.isBlocked(xPos, yPos + height) || map.isBlocked(xPos, yPos) || xPos <= 0)) {
            xPos -= delta * SPEED;
        } else if (input.isKeyDown(Input.KEY_RIGHT) && !(map.isBlocked(xPos + width, yPos) || map.isBlocked(xPos + width, yPos + height) || xPos >= map.getWidth())) {
            xPos += delta * SPEED;
        } else {

        }
    }


}
