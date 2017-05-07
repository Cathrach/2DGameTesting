/**
 * Created by serinahu on 5/6/17.
 */

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import org.newdawn.slick.tiled.*;

public class TownMap extends Map {
    private int id;
    TiledMap map;
    private Entity leader;

    public TownMap(int id) {
        this.id = id;
    }


    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        map = new TiledMap("maps/sample.tmx");
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
        // if the tile is an entry, change map
        // if the tile is an exit, change to world map
        // if the tile is a shop, change to shop menu
        // if the tile is a chest/some other trigger, do things

    }

    public void changeMap(TiledMap map) {
        this.map = map;
    }
}
