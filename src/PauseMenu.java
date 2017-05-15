import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

/**
 * Created by serinahu on 5/4/17.
 */
public class PauseMenu extends BasicGameState {

    private int id;
    private boolean inInventory;

    public PauseMenu(int id) {
        this.id = id;
    }

    @Override
    public int getID() {
        return id;
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        inInventory = true;
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        // have toggle-able button on top
        // render either the inventory or the stat menus
        if (inInventory) {
            Inventory.render(g);
        }
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        Input input = container.getInput();
        if (input.isKeyPressed(Input.KEY_I)) {
            inInventory = true;
        } else if (input.isKeyPressed(Input.KEY_S)) {
            inInventory = false;
        } else if (input.isKeyPressed(Input.KEY_ESCAPE)) {
            game.enterState(TestingGame.MAP);
        }

        if (inInventory) {
            Inventory.update(container, delta);
        } else {
            // update stat menu
        }


    }
}
