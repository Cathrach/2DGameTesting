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
    static final int QUESTS = 2;

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
        // render one of the available menus
        if (inMenu == INVENTORY) {
            Inventory.render(g);
        } else if (inMenu == STATS){
            Stats.render(g);
        } else if (inMenu == QUESTS) {
            g.drawString("QUESTS", 50, 50);
            g.drawString("STATUS", 500, 50);
            g.drawString("Press ESC to return to menu", 10, 450);
            int yOffset = 0;
            for (int i=0; i<Resources.quests_db.length; i++) {
                Quest quest = Resources.quests_db[i];
                if (quest.getStatus() != Quest.NOT_STARTED) {
                    g.drawString(quest.getName(), 50, yOffset*60 + 80);
                    g.drawString(quest.getDescription(), 50, yOffset*60 + 100);
                    g.drawString(quest.getStatusString(), 500, yOffset*60 + 80);
                    yOffset++;
                }
            }
        } else {
            g.drawString("[ESC] - return to game", 200, 150);
            g.drawString("[X] - quit game", 200, 180);
            g.drawString("[I] - inventory", 200, 210);
            g.drawString("[S] - stats", 200, 240);
            g.drawString("[Q] - quest log", 200, 270);
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
        } else if (input.isKeyPressed(Input.KEY_Q)) {
            inMenu = QUESTS;
        }

        // update the current menu
        if (inMenu == INVENTORY) {
            Inventory.update(container, delta);
        } else if (inMenu == STATS){
            Stats.update(container, delta);
        }
    }
}
