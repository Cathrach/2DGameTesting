/**
 * Created by serinahu on 5/4/17.
 */

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

import java.util.List;

public class Combat extends BasicGameState {

    private int id;
    private boolean isPlayerTurn;
    private List<BattleEntity> turnOrder;

    public Combat(int id) {
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
        // draw background
        // display enemies on one side and players on another
        // if player turn, render the action menu
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        // check whose move it is

        // if player move:
        // change selected action w/arrow keys
        // get selected action w/enter key
        // else:
        // choose an action from the AI

        // move around actions in the turnOrder list
        // execute actions
        // delete enemies and players if necessary (check if HP <= 0)
        // consume skill effects for entity that just moved

        // if all enemies or all players are dead, exit back to the world map
        // modify the inventory with loot
    }
}
