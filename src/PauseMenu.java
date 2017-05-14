import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created by serinahu on 5/4/17.
 */
public class PauseMenu extends BasicGameState {

    private int id;

    public PauseMenu(int id) {
        this.id = id;
    }

    @Override
    public int getID() {
        return id;
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {

    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        // have toggle-able button on top
        // render either the inventory or the stat menus
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        // if in inventory, update inventory
        Inventory.update(container, delta);
        // if in stat menu, update that
    }
}
