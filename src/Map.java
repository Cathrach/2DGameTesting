import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import org.newdawn.slick.tiled.*;

/**
 * Created by serinahu on 5/6/17.
 */

public class Map extends BasicGameState {
    private int id;
    TiledMap map;
    private Entity leader;

    public Map() {
        id = 0;
    }
    public Map(int id) {
        this.id = id;
    }

    @Override
    public int getID() {
        return id;
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        map = new TiledMap("maps/sample.tmx");
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        map.render(0, 0);
        leader.render(container, game, g);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        Input input = container.getInput();
        if (input.isKeyPressed(Input.KEY_ESCAPE)) {
            game.enterState(TestingGame.MAIN_MENU);
        }
        leader.update(container, game, delta, this);
        // if the tile is an entry, change state to the town map
        // if there's a random encounter, change state to combat

    }

    // TODO: perhaps make it dependent on direction :)
    public boolean isBlocked(float xPos, float yPos) {
        if (xPos < 0 || yPos < 0 || xPos > this.getWidth() || yPos > this.getHeight()) {
            return true;
        }
        // current tile is xPos / the tile width
        int tileWidth = map.getTileWidth();
        int tileHeight = map.getTileHeight();
        int tileID = map.getTileId((int) xPos / tileWidth, (int) yPos / tileHeight, map.getLayerIndex("Blocked"));
        return map.getTileProperty(tileID, "Blocked", "false").equals("true");
    }

    public int getWidth() {
        return map.getWidth() * map.getTileWidth();
    }

    public int getHeight() {
        return map.getHeight() * map.getTileHeight();
    }
}
