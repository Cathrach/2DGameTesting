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
    private boolean isFinishedTurn;
    private BattleEntity currMove;
    private final int ATTACK = 0;
    private final int DEFEND = 1;
    private final int ITEM = 2;
    private final int SKILL = 3;
    private final int FLEE = 4;
    private final String[] skillMenu = {"Attack", "Defend", "Item", "Skill", "Flee"};
    private int highlightedActionID;
    private int selectedActionID;
    private BattleAction selectedAction;
    private int highlightedSkillID;
    private int highlightedItemID;
    private Consumable selectedItem;
    private Skill selectedSkill;
    private int highlightedTargetID;
    private List<BattleEntity> selectedTargets;
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
        turnOrder = new ArrayList<>();
        highlightedActionID = 0;
        highlightedItemID = 0;
        highlightedSkillID = 2;
        turnOrder.clear();
        actionOrder.clear();
        // might change for ambushes, etc.
        for (Entity entity : Resources.party) {
            if (entity != null) {
                turnOrder.add(entity.battleEntity);
            }
        }
        turnOrder.addAll(Resources.currEnemies);
        currMove = turnOrder.get(0);
        isPlayerTurn = turnOrder.get(0) instanceof Ally;
        isSelectingItem = false;
        isSelectingTarget = false;
        isSelectingSkill = false;
        isFinishedTurn = false;
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
                // draw part of the item menu, rectangle around highlighted menu
                // move the "part" of the item menu based on the highlighted id
            } else if (isSelectingSkill) {
                // draw skill menu starting from 3rd element, rectangle around highlighted menu
            } else {
                // draw skill menu: attack, defend, item, skill, flee
                // draw a rectangle around the highlighted action
            }
        }
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        Input input = container.getInput();
        if (isPlayerTurn) {
            // if selecting a target, different things apply....
            if (isSelectingSkill) {
                if (input.isKeyPressed(Input.KEY_DOWN)) {
                    if (highlightedSkillID < currMove.skills.size() - 1) {
                        highlightedSkillID++;
                    }
                } else if (input.isKeyPressed(Input.KEY_UP)) {
                    if (highlightedSkillID > 2) {
                        highlightedSkillID--;
                    }
                } else if (input.isKeyPressed(Input.KEY_ESCAPE)) {
                    isSelectingSkill = false;
                } else if (input.isKeyPressed(Input.KEY_ENTER)) {
                    selectedSkill = currMove.skills.get(highlightedSkillID);
                    isSelectingSkill = false;
                    isSelectingTarget = true;
                }
            } else if (isSelectingItem) {
                if (input.isKeyPressed(Input.KEY_DOWN)) {
                    if (highlightedItemID < Resources.consumableInven.size() - 1) {
                        highlightedItemID++;
                    }
                } else if (input.isKeyPressed(Input.KEY_UP)) {
                    if (highlightedItemID > 0) {
                        highlightedItemID--;
                    }
                } else if (input.isKeyPressed(Input.KEY_ESCAPE)) {
                    isSelectingItem = false;
                } else if (input.isKeyPressed(Input.KEY_ENTER)) {
                    selectedItem = Resources.consumableInven.get(highlightedItemID);
                    isSelectingItem = false;
                    isSelectingTarget = true;
                }
            } else if (isSelectingTarget) {
                // TODO: casework: if target is self, only option is target
                // if target is all enemies, only option is all enemies
                // similarly for all allies
                // if target is single enemy, go through enemy list
                // if target is single ally, go through ally list
                if (input.isKeyPressed(Input.KEY_DOWN)) {
                    if (highlightedTargetID < Resources.currEnemies.size() - 1) {
                        highlightedTargetID++;
                    }
                } else if (input.isKeyPressed(Input.KEY_UP)) {
                    if (highlightedTargetID > 0) {
                        highlightedTargetID--;
                    }
                } else if (input.isKeyPressed(Input.KEY_ESCAPE)) {
                    isSelectingTarget = false;
                } else if (input.isKeyPressed(Input.KEY_ENTER)) {
                    selectedTargets.add(Resources.currEnemies.get(highlightedTargetID));
                }
            } else if (!isSelectingTarget && selectedTargets.size() == 0) { // if a target hasn't been selected yet
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
                            isSelectingItem = true;
                            break;
                        case SKILL:
                            // select skill to use
                            isSelectingSkill = true;
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
                        // attack target: first element of target list
                        selectedAction = new BattleAction(currMove, selectedSkill, selectedTargets.get(0));
                        break;
                    case ITEM:
                        // use item on target: immediate use!
                        for (BattleEntity target : selectedTargets) {
                            selectedItem.use(target);
                            updateDead();
                        }
                        break;
                    case SKILL:
                        // use skill on all targets in target list
                        for (BattleEntity target : selectedTargets) {
                            selectedAction = new BattleAction(currMove, selectedSkill, target);
                        }
                        break;
                    default:
                        // should not occur
                        System.out.println("Error: selected a target when there was no need to");
                }
                isFinishedTurn = true;
            }
        }
        // this should be handled in the Ally/Enemy things.
        // if player move:
        // change selected skill w/arrow keys
        // get selected skill w/enter key
        // else:
        // choose an skill from the AI
        if (isFinishedTurn) {
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
            // rotate turnOrder:
            turnOrder.add(turnOrder.remove(0));
            // check whose move it is
            isPlayerTurn = turnOrder.get(0) instanceof Ally;
            currMove = turnOrder.get(0);
            // clear all the selection things
            isSelectingItem = false;
            isSelectingTarget = false;
            isSelectingSkill = false;
            isFinishedTurn = false;
        }

        // if all enemies or all players are dead, exit back to the world map
        updateWin(game);


    }

    public void updateDead() {
        // assuming no resurrection
        for (int j = 0; j < Resources.party.length; j++) {
            if (Resources.party[j].battleEntity.currHP <= 0) {
                Resources.party[j] = null;
            }
        }
        for (Enemy enemy : Resources.currEnemies) {
            if (enemy.currHP == 0) {
                enemy.isDead = true;
            }
        }
    }

    public void updateWin(StateBasedGame game) {
        boolean alliesDead = true;
        boolean enemiesDead = true;
        for (Entity entity : Resources.party) {
            if (entity.battleEntity != null) {
                alliesDead = false;
            }
        }
        for (Enemy enemy : Resources.currEnemies) {
            if (!enemy.isDead) {
                enemiesDead = false;
            }
        }
        if (enemiesDead) {
            for (Enemy enemy : Resources.currEnemies) {
                enemy.getDrops();
            }
        }
        if (alliesDead || enemiesDead) {
            game.enterState(TestingGame.MAP);
        }
        // TODO: modify the inventory with loot
    }
}
