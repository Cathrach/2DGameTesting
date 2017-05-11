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
    private boolean isSelectingTarget;
    private boolean isSelectingItem;
    private boolean isSelectingSkill;
    private final int ATTACK = 0;
    private final int DEFEND = 1;
    private final int ITEM = 2;
    private final int SKILL = 3;
    private final int FLEE = 4;
    private final String[] skillMenu = {"Attack", "Defend", "Item", "Skill", "Flee"};
    private int highlightedActionID;
    private int selectedActionID;
    private BattleAction selectedAction;
    private int highlightedUseID;
    private Consumable selectedItem;
    private Skill selectedSkill;
    private int highlightedTargetID;
    private BattleEntity selectedTarget;
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
        highlightedActionID = 0;
        highlightedUseID = 0;
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
        if (isPlayerTurn) {
            if (isSelectingTarget) {
                // stick arrow on top of target
            } else if (isSelectingItem) {
                // draw item menu, rectangle around highlighted menu
            } else if (isSelectingSkill) {
                // draw skill menu, rectangle around highlighted menu
            } else {
                // draw skill menu: attack, defend, item, skill, flee
                // draw a rectangle around the highlighted action
            }
        }
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        Input input = container.getInput();
        // check whose move it is
        isPlayerTurn = turnOrder.get(0) instanceof Ally;
        BattleEntity currMove = turnOrder.get(0);
        if (isPlayerTurn) {
            // if selecting a target, different things apply....
            if (isSelectingSkill) {
                if (input.isKeyPressed(Input.KEY_DOWN)) {
                }
            } else if (isSelectingItem) {

            } else if (isSelectingTarget) {

            } else if (!isSelectingTarget && selectedTarget == null) { // if a target hasn't been selected yet
                // process input
                if (input.isKeyPressed(Input.KEY_DOWN)) {
                    if (highlightedActionID < skillMenu.length - 1) {
                        highlightedActionID++;
                    }
                } else if (input.isKeyPressed(Input.KEY_UP)) {
                    if (highlightedActionID > 0) {
                        highlightedActionID--;
                    }
                } else if (input.isKeyPressed(Input.KEY_ENTER)) {
                    selectedActionID = highlightedActionID;
                    switch (selectedActionID) {
                        // attack
                        case ATTACK:
                            selectedSkill = currMove.skills.get(0);
                            isSelectingTarget = true;
                            break;
                        case DEFEND:
                            // cast defend on self: immediate use
                            currMove.use(currMove.skills.get(1), currMove);
                            break;
                        case ITEM:
                            // select item to use
                            isSelectingTarget = true;
                            break;
                        case SKILL:
                            // select skill to use
                            isSelectingTarget = true;
                            break;
                        case FLEE:
                            // flee somehow
                            break;
                        default:
                            // shouldn't happen but
                            System.out.println("Error: selectedActionID is not in array bounds");
                    }
                }
            } else {
                switch (selectedActionID) {
                    case ATTACK:
                        // attack target
                        selectedAction = new BattleAction(currMove, selectedSkill, selectedTarget);
                        break;
                    case ITEM:
                        // use item on target: immediate use!
                        selectedItem.use(selectedTarget);
                        break;
                    case SKILL:
                        // use skill on target
                        selectedAction = new BattleAction(currMove, selectedSkill, selectedTarget);
                        break;
                    default:
                        // should not occur
                        System.out.println("Error: selected a target when there was no need to");
                }
            }
        }
        // this should be handled in the Ally/Enemy things.
        // if player move:
        // change selected skill w/arrow keys
        // get selected skill w/enter key
        // else:
        // choose an skill from the AI

        // move around actions in the actionOrder list
        if (selectedAction != null) {
            int i = 0;
            while (i < actionOrder.size() || actionOrder.get(i).skill.delay < selectedAction.skill.delay) {
                i++;
            }
            actionOrder.add(i - 1, selectedAction);
        }
        // execute actions
        for (int i = 0; i < actionOrder.size(); i++) {
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
