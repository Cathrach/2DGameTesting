/**
 * Created by serinahu on 5/3/17.
 */

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import org.newdawn.slick.tiled.*;

public class WorldMap extends BasicGameState {

    private int id;
    TiledMap map;
    private Entity leader;

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
        // check if there's a "save"; if not, make a new entity
        TestingGame.party = new Entity[4];
        TestingGame.party[0] = new Entity(0, 0, new BattleEntity("square", true));
        leader = TestingGame.party[0];
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

    // don't need this cuz world map
    public void changeMap(String map_name) throws SlickException {
        map = new TiledMap(map_name);
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
