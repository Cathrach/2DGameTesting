import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

/**
 * Created by serinahu on 5/4/17.
 */
public class PauseMenu extends BasicGameState {

    private int id;
    //static boolean inInventory;
    static int inMenu;
    static final int NONE = -1;
    static final int INVENTORY = 0;
    static final int STATS = 1;

    public PauseMenu(int id) {
        this.id = id;
    }

    @Override
    public int getID() {
        return id;
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        inMenu = -1;
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        // have toggle-able button on top
        // render either the inventory or the stat menus
        if (inMenu == INVENTORY) {
            Inventory.render(g);
        } else if (inMenu == STATS){
            Stats.render(g);
        } else {
            g.drawString("[ESC] - return to game", 200, 150);
            g.drawString("[X] - quit game", 200, 180);
            g.drawString("[I] - inventory", 200, 210);
            g.drawString("[S] - stats", 200, 240);
        }
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        // get key input to determine which menu to switch to
        Input input = container.getInput();
        if (input.isKeyPressed(Input.KEY_ESCAPE)) {
            if (inMenu == NONE) {
                // if on the main pause screen when ESC pressed, return to game
                game.enterState(TestingGame.MAP);
            } else {
                // if in a menu when ESC pressed, return to main pause screen
                inMenu = NONE;
            }
        } else if (input.isKeyPressed(Input.KEY_X)) {
            container.exit();
        } else if (input.isKeyPressed(Input.KEY_I)) {
            inMenu = INVENTORY;
            Inventory.init();
        } else if (input.isKeyPressed(Input.KEY_S)) {
            inMenu = STATS;
            Stats.init();
        }

        // update the current menu
        if (inMenu == INVENTORY) {
            Inventory.update(container, delta);
        } else if (inMenu == STATS){
            Stats.update(container, delta);
        }

    }
}
