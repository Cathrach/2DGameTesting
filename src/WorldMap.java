/**
 * Created by serinahu on 5/3/17.
 */

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import org.newdawn.slick.tiled.*;

public class WorldMap extends BasicGameState {

    private int id;
    TiledMap map;
    private Entity square;


    public WorldMap(int id) {
        this.id = id;
    }

    @Override
    public int getID() {
        return id;
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        map = new TiledMap("maps/sample.tmx");
        square = new Entity(0, 0);
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        map.render(0, 0);
        square.render(container, game, g);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        Input input = container.getInput();
        if (input.isKeyPressed(Input.KEY_ESCAPE)) {
            game.enterState(TestingGame.MAIN_MENU);
        }
        square.update(container, game, delta, this);

    }

    public void changeMap(String map_name) throws SlickException {
        map = new TiledMap(map_name);
    }

    public boolean isBlocked(float xPos, float yPos) {
        if (xPos == 0 || yPos == 0 || xPos == map.getWidth() * map.getTileWidth() || yPos == map.getHeight() * map.getTileHeight()) {
            return true;
        }
        // current tile is xPos / the tile width
        int tileWidth = map.getTileWidth();
        int tileHeight = map.getTileHeight();
        int tileID = map.getTileId((int) xPos / tileWidth, (int) yPos / tileHeight, map.getLayerIndex("Blocked"));
        return map.getTileProperty(tileID, "Blocked", "false").equals("true");
    }
}
