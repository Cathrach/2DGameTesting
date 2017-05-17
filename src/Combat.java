/**
 * Created by serinahu on 5/4/17.
 */

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

import java.util.ArrayList;
import java.util.List;

public class Combat extends BasicGameState {
    static CombatKeyboard listener;
    static List<Enemy> currEnemies;
    static boolean isCombat;
    private int id;
    static boolean isPlayerTurn;
    static boolean isSelectingTarget;
    static boolean isSelectingItem;
    static boolean isSelectingSkill;
    static boolean isFinishedTurn;
    static BattleEntity currMove;
    static final int ATTACK = 0;
    static final int DEFEND = 1;
    static final int ITEM = 2;
    static final int SKILL = 3;
    static final int FLEE = 4;
    static final String[] skillMenu = {"Attack", "Defend", "Item", "Skill", "Flee"};
    static int highlightedActionID;
    static int selectedActionID;
    static BattleAction selectedAction;
    static int highlightedSkillID;
    static int highlightedItemID;
    static Consumable selectedItem;
    static Skill selectedSkill;
    static int highlightedTargetID;
    static List<Consumable> consumablesOnly;
    static List<BattleEntity> selectedTargets;
    static List<BattleEntity> possibleTargets;
    static List<BattleEntity> turnOrder;
    static List<BattleAction> actionOrder;

    public Combat(int id) {
        this.id = id;
        turnOrder = new ArrayList<>();
        actionOrder = new ArrayList<>();
    }

    @Override
    public int getID() {
        return id;
    }

    public static void initListener(GameContainer container) {
        listener = new CombatKeyboard();
        container.getInput().addKeyListener(listener);
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        isCombat = false;

    }

    public static void enter(StateBasedGame game) {
        isCombat = true;
        for (Item item : Inventory.items) {
            if (item instanceof Consumable) {
                consumablesOnly.add((Consumable) item);
            }
        }
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
        turnOrder.addAll(Combat.currEnemies);
        currMove = turnOrder.get(0);
        isPlayerTurn = turnOrder.get(0) instanceof Ally;
        isSelectingItem = false;
        isSelectingTarget = false;
        isSelectingSkill = false;
        isFinishedTurn = false;
        game.enterState(TestingGame.COMBAT);
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
                // make sure to draw only the consumables!
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
            }
            // consume skill effects for entity that just moved
            currMove.consumeSkillEffects();
            // rotate turnOrder:
            turnOrder.add(turnOrder.remove(0));
            // check whose move it is
            isPlayerTurn = turnOrder.get(0) instanceof Ally;
            currMove = turnOrder.get(0);
            // clear all the selection things
            selectedItem = null;
            selectedSkill = null;
            selectedAction = null;
            selectedTargets.clear();
            isSelectingItem = false;
            isSelectingTarget = false;
            isSelectingSkill = false;
            isFinishedTurn = false;
        }
        updateDead();
        // if all enemies or all players are dead, exit back to the world map
        updateWin(game);
    }

    public void updateDead() {
        // assuming no resurrection; remove dead from turn order list
        for (int j = 0; j < Resources.party.length; j++) {
            if (Resources.party[j].battleEntity.currHP <= 0) {
                turnOrder.remove(Resources.party[j].battleEntity);
                Resources.party[j] = null;
            }
        }
        for (Enemy enemy : Combat.currEnemies) {
            if (enemy.currHP == 0) {
                turnOrder.remove(enemy);
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
        for (Enemy enemy : Combat.currEnemies) {
            if (!enemy.isDead) {
                enemiesDead = false;
            }
        }
        // modify inventory with loot
        if (enemiesDead) {
            for (Enemy enemy : Combat.currEnemies) {
                enemy.getDrops();
                for (int i = 0; i < Resources.enemy_db.length; i++) {
                    if (Resources.enemy_db[i].name.equals(enemy.name)) {
                        Resources.enemy_db[i].timesKilled++;
                    }
                }
            }
        }
        if (alliesDead || enemiesDead) {
            isCombat = false;
            game.enterState(TestingGame.MAP);
        }
    }
}

class CombatKeyboard implements KeyListener {

    @Override
    public void keyPressed(int key, char c) {

    }

    @Override
    public void keyReleased(int key, char c) {
        if (Combat.isPlayerTurn) {
            // if selecting a target, different things apply....
            if (Combat.isSelectingSkill) {
                if (key == Input.KEY_DOWN) {
                    if (Combat.highlightedSkillID < Combat.currMove.skills.size() - 1) {
                        Combat.highlightedSkillID++;
                    }
                } else if (key == Input.KEY_UP) {
                    if (Combat.highlightedSkillID > 2) {
                        Combat.highlightedSkillID--;
                    }
                } else if (key == Input.KEY_DELETE || key == Input.KEY_BACK) {
                    Combat.selectedSkill = null;
                    Combat.isSelectingSkill = false;
                } else if (key == Input.KEY_ENTER) {
                    Combat.selectedSkill = Combat.currMove.skills.get(Combat.highlightedSkillID);
                    Combat.isSelectingSkill = false;
                    Combat.isSelectingTarget = true;
                }
            } else if (Combat.isSelectingItem) {
                if (key == Input.KEY_DOWN) {
                    if (Combat.highlightedItemID < Combat.consumablesOnly.size() - 1) {
                        Combat.highlightedItemID++;
                    }
                } else if (key == Input.KEY_UP) {
                    if (Combat.highlightedItemID > 0) {
                        Combat.highlightedItemID--;
                    }
                } else if (key == Input.KEY_DELETE || key == Input.KEY_BACK) {
                    Combat.selectedItem = null;
                    Combat.isSelectingItem = false;
                } else if (key == Input.KEY_ENTER) {
                    Combat.selectedItem = Combat.consumablesOnly.get(Combat.highlightedItemID);
                    Combat.possibleTargets.clear();
                    switch (Combat.selectedItem.getTargetType()) {
                        case SELF:
                            Combat.possibleTargets.add(Combat.currMove);
                            break;
                        case SINGLE_ALLY:
                            for (Entity entity : Resources.party) {
                                Combat.possibleTargets.add(entity.battleEntity);
                            }
                            break;
                        case SINGLE_ENEMY:
                            for (Enemy enemy : Combat.currEnemies) {
                                if (!enemy.isDead) {
                                    Combat.possibleTargets.add(enemy);
                                }
                            }
                            break;
                        case ALL_ALLIES:
                            for (Entity entity : Resources.party) {
                                Combat.possibleTargets.add(entity.battleEntity);
                            }
                            break;
                        case ALL_ENEMIES:
                            for (Enemy enemy : Combat.currEnemies) {
                                if (!enemy.isDead) {
                                    Combat.possibleTargets.add(enemy);
                                }
                            }
                            break;
                    }
                    Combat.isSelectingItem = false;
                    Combat.isSelectingTarget = true;
                }
            } else if (Combat.isSelectingTarget) {

                if (key == Input.KEY_DOWN) {
                    if (Combat.highlightedTargetID < Combat.currEnemies.size() - 1) {
                        Combat.highlightedTargetID++;
                    }
                } else if (key == Input.KEY_UP) {
                    if (Combat.highlightedTargetID > 0) {
                        Combat.highlightedTargetID--;
                    }
                } else if (key == Input.KEY_DELETE || key == Input.KEY_BACK) {
                    Combat.isSelectingTarget = false;
                } else if (key == Input.KEY_ENTER) {
                    Combat.selectedTargets.add(Combat.currEnemies.get(Combat.highlightedTargetID));
                    Combat.isSelectingTarget = false;
                }
            } else if (!Combat.isSelectingTarget && Combat.selectedTargets.size() == 0) { // if a target hasn't been selected yet
                // process input
                if (key == Input.KEY_DOWN) {
                    if (Combat.highlightedActionID < 4) {
                        Combat.highlightedActionID++;
                    }
                } else if (key == Input.KEY_UP) {
                    if (Combat.highlightedActionID > 0) {
                        Combat.highlightedActionID--;
                    }
                } else if (key == Input.KEY_ENTER) {
                    Combat.selectedActionID = Combat.highlightedActionID;
                    switch (Combat.selectedActionID) {
                        // Combat.ATTACK
                        case Combat.ATTACK:
                            Combat.selectedSkill = Combat.currMove.skills.get(0);
                            Combat.isSelectingTarget = true;
                            break;
                        case Combat.DEFEND:
                            // cast Combat.DEFEND on self: immediate use
                            Combat.currMove.use(Combat.currMove.skills.get(1), Combat.currMove);
                            break;
                        case Combat.ITEM:
                            // select item to use
                            Combat.isSelectingItem = true;
                            break;
                        case Combat.SKILL:
                            // select skill to use
                            Combat.isSelectingSkill = true;
                            break;
                        case Combat.FLEE:
                            // flee somehow
                            break;
                        default:
                            // shouldn't happen but
                            System.out.println("Error: Combat.selectedActionID is not in array bounds");
                    }
                }
            } else {
                switch (Combat.selectedActionID) {
                    case Combat.ATTACK:
                        // Combat.ATTACK target: first element of target list
                        Combat.selectedAction = new BattleAction(Combat.currMove, Combat.selectedSkill, Combat.selectedTargets.get(0));
                        break;
                    case Combat.ITEM:
                        // use item on target: immediate use!
                        for (BattleEntity target : Combat.selectedTargets) {
                            Combat.selectedItem.use(target);
                            Inventory.removeItem(Combat.selectedItem.getName(), 1);
                            if (Combat.selectedItem.getQuantity() == 0) {
                                Combat.consumablesOnly.remove(Combat.highlightedItemID);
                            }
                        }
                        break;
                    case Combat.SKILL:
                        // use skill on all targets in target list
                        for (BattleEntity target : Combat.selectedTargets) {
                            Combat.selectedAction = new BattleAction(Combat.currMove, Combat.selectedSkill, target);
                        }
                        break;
                    default:
                        // should not occur
                        System.out.println("Error: selected a target when there was no need to");
                }
                Combat.isFinishedTurn = true;
            }
        }
    }

    @Override
    public void setInput(Input input) {

    }

    @Override
    public boolean isAcceptingInput() {
        return Combat.isCombat;
    }

    @Override
    public void inputEnded() {

    }

    @Override
    public void inputStarted() {

    }
}