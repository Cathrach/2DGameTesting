/**
 * Created by serinahu on 5/4/17.
 */
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
public class Entity {

    // image representing character
    private Image sprite;
    private Image hitbox;
    private int dir;
    private double frame;
    float xPos, yPos;
    private int hitWidth, hitHeight;
    private int imgWidth, imgHeight;
    private final float SPEED = 0.1f;
    private final double frameSpeed = 0.1;
    Ally battleEntity;
    // constructor: make character at some position
    public Entity(float xPos, float yPos, int dir, int frame, Ally  battleEntity) throws SlickException {
        sprite = new Image("images/sprites/heroine.png");
        hitbox = new Image("images/sprites/hitbox.png");
        this.xPos = xPos;
        this.yPos = yPos;
        this.dir = dir;         // 0=down, 1=left, 2=right, 3=up
        this.frame = frame;     // number of frames in the walk animation: 3
        hitWidth = hitbox.getWidth();
        hitHeight = hitbox.getHeight();
        imgWidth = 32;
        imgHeight = 45;
        this.battleEntity = battleEntity;
    }
    // init: set up animations?
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        // do you need this empty function for anything besides animation?
    }
    // render: draw the character
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        g.drawString("X: " + xPos + " Y: " + yPos, 20, 25);
        sprite.getSubImage(imgWidth*((int)frame), imgHeight*dir, imgWidth, imgHeight).draw(xPos, yPos-(imgHeight-hitHeight));
    }
    @Override
    public String toString() {
        return "Entity{" +
                "sprite=" + sprite +
                ", xPos=" + xPos +
                ", yPos=" + yPos +
                ", width=" + hitWidth +
                ", height=" + hitHeight +
                ", SPEED=" + SPEED +
                ", player=" + battleEntity.getName() +
                '}';
    }

    // update: check if keys are pressed -> change position; handle collisions/damage/teleports eventually
    public void update(GameContainer container, StateBasedGame game, int delta, MapState mapState) throws SlickException {
        Input input = container.getInput();
        float moveDist = delta * SPEED;
        if (input.isKeyDown(Input.KEY_DOWN)) {
            dir = 0; frame = (frame + frameSpeed)%3;
            if (!(mapState.isBlocked(xPos, yPos + hitHeight + moveDist) || mapState.isBlocked(xPos + hitWidth, yPos + hitHeight + moveDist))) {
                yPos += moveDist;
            }
        }
        if (input.isKeyDown(Input.KEY_LEFT)) {
            dir = 1; frame = (frame + frameSpeed)%3;
            if (!(mapState.isBlocked(xPos - moveDist, yPos) || mapState.isBlocked(xPos - moveDist, yPos + hitWidth))) {
                xPos -= moveDist;
            }
        }
        if (input.isKeyDown(Input.KEY_RIGHT)) {
            dir = 2; frame = (frame + frameSpeed)%3;
            if (!(mapState.isBlocked(xPos + hitWidth + moveDist, yPos) || mapState.isBlocked(xPos + hitWidth + moveDist, yPos + hitWidth))) {
                xPos += moveDist;
            }
        }
        if (input.isKeyDown(Input.KEY_UP)) {
            dir = 3; frame = (frame + frameSpeed)%3;
            if (!(mapState.isBlocked(xPos, yPos - moveDist) || mapState.isBlocked(xPos + hitWidth, yPos - moveDist))) {
                yPos -= moveDist;
            }
        }
        // check if player presses SPACE while facing & next to a container (i.e. chest, barrel, crate, etc)
        if (input.isKeyDown(Input.KEY_SPACE)){
            if (dir==0 && mapState.isContainer(xPos + hitWidth/2, yPos + hitHeight + moveDist)){
                Inventory.addItem(mapState.getItem(xPos + hitWidth/2, yPos + hitHeight + moveDist), 1);
            } else if (dir==1 && mapState.isContainer(xPos - hitWidth, yPos + hitHeight/2)){
                Inventory.addItem(mapState.getItem(xPos - hitWidth, yPos + hitHeight/2), 1);
            } else if (dir==2 && mapState.isContainer(xPos + hitWidth + moveDist, yPos + hitHeight/2)){
                Inventory.addItem(mapState.getItem(xPos + hitWidth + moveDist, yPos + hitHeight/2), 1);
            } else if (dir==3 && mapState.isContainer(xPos + hitWidth/2, yPos - hitHeight)){
                Inventory.addItem(mapState.getItem(xPos + hitWidth/2, yPos - hitHeight), 1);
            }
        }
    }
    public Image getSprite() {
        return sprite;
    }
}