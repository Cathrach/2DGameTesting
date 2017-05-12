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
    private int selectedItemID;
    private boolean isUsingMenu;
    private boolean isEquippingMenu;
    private int selectedUnitID;

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
        // render inventory: items, then equips
        // put box around highlighted item
        // render use menu if needed
        // render the equip/use menu
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        // if in the equip menu: select the party member
        // if in the 
    }
}
