/**
 * Created by serinahu on 5/4/17.
 */

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

import java.util.ArrayList;
import java.util.List;

public class Combat extends BasicGameState {

    private int id;
    private boolean isPlayerTurn;
    private List<BattleEntity> turnOrder;
    private List<BattleAction> actionOrder;

    public Combat(int id) {
        this.id = id;
        turnOrder = new ArrayList<>();
        actionOrder = new ArrayList<>();
    }

    @Override
    public int getID() {
        return id;
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        // should be done whenever combat is entered.....
        turnOrder.clear();
        actionOrder.clear();
        for (Entity entity : Resources.party) {
            turnOrder.add(entity.battleEntity);
        }
        turnOrder.addAll(Resources.currEnemies);
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        // draw background
        // display enemies on one side and players on another
        // if player turn, render the skill menu
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        // check whose move it is
        isPlayerTurn = turnOrder.get(0) instanceof Ally;
        BattleEntity currMove = turnOrder.get(0);
        BattleAction selectedAction = currMove.decideAction();
        // this should be handled in the Ally/Enemy things.
        // if player move:
        // change selected skill w/arrow keys
        // get selected skill w/enter key
        // else:
        // choose an skill from the AI

        // move around actions in the actionOrder list
        int i = 0;
        while (i < actionOrder.size() || actionOrder.get(i).skill.delay < selectedAction.skill.delay) {
            i++;
        }
        actionOrder.add(i - 1, selectedAction);
        // execute actions
        for (i = 0; i < actionOrder.size(); i++) {
            BattleAction battleAction = actionOrder.get(i);
            // delete enemies and players if necessary (check if HP <= 0)
            if (battleAction.skill.delay == 0) {
                battleAction.skill.use(battleAction.caster, battleAction.target);
            }
            updateDead();
        }
        // consume skill effects for entity that just moved
        currMove.consumeSkillEffects();

        // if all enemies or all players are dead, exit back to the world map
        updateWin(game);

        // rotate turnOrder:
        turnOrder.add(turnOrder.remove(0));
    }

    public void updateDead() {
        // assuming no resurrection
        for (int j = 0; j < Resources.party.length; j++) {
            if (Resources.party[j].battleEntity.currHP <= 0) {
                Resources.party[j] = null;
            }
        }
        for (int j = 0; j < Resources.currEnemies.size(); j++) {
            if (Resources.currEnemies.get(j).currHP <= 0) {
                Resources.currEnemies.remove(j);
            }
        }
    }

    public void updateWin(StateBasedGame game) {
        boolean alliesDead = true;
        boolean enemiesDead = Resources.currEnemies.size() == 0;
        for (Entity entity : Resources.party) {
            if (entity.battleEntity != null) {
                alliesDead = false;
            }
        }
        if (alliesDead || enemiesDead) {
            game.enterState(TestingGame.MAP);
        }
        // TODO: modify the inventory with loot
    }
}
