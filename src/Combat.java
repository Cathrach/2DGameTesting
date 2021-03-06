/**
 * Created by serinahu on 5/4/17.
 */

import org.newdawn.slick.*;
import org.newdawn.slick.geom.Polygon;
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
    static Image background;
    static int map;
    static String message;

    static List<BattleEntity> livingAllies;

    public Combat(int id) {
        currEnemies = new ArrayList<>();
        this.id = id;
        turnOrder = new ArrayList<>();
        actionOrder = new ArrayList<>();
        livingAllies = new ArrayList<>();
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
        background = new Image("images/backgrounds/backgroundSet.png");
        message = "";
    }

    public static void enter(StateBasedGame game) {
        isCombat = true;
        consumablesOnly = new ArrayList<>();
        message = "";
        map = MapState.currentMapID;
        for (Item item : Inventory.items) {
            if (item instanceof Consumable) {
                consumablesOnly.add((Consumable) item);
            }
        }
        // should be done whenever combat is entered.....
        turnOrder = new ArrayList<>();
        actionOrder = new ArrayList<>();
        highlightedActionID = 0;
        highlightedItemID = 0;
        highlightedSkillID = 2;
        turnOrder.clear();
        actionOrder.clear();
        livingAllies.clear();
        // determine which allies are still alive (can be revived OUTSIDE of combat)
        for (Entity entity : Resources.party) {
            if (entity.battleEntity.getHP() > 0) {
                livingAllies.add(entity.battleEntity);
            }
        }
        // might change for ambushes, etc.
        for (BattleEntity entity : livingAllies) {
            if (entity != null) {
                turnOrder.add(entity);
            }
        }
        turnOrder.addAll(Combat.currEnemies);
        currMove = turnOrder.get(0);
        isPlayerTurn = turnOrder.get(0) instanceof Ally;
        isSelectingItem = false;
        isSelectingTarget = false;
        isSelectingSkill = false;
        isFinishedTurn = false;
        possibleTargets = new ArrayList<>();
        selectedTargets = new ArrayList<>();
        game.enterState(TestingGame.COMBAT);
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        // draw background depending on the map
        if (map < 12) {
            background.getSubImage(640, 960, 640, 480).draw(0,0);   // town
        } else if (map < 18) {
            background.getSubImage(2*640, 480, 640, 480).draw(0,0); // forest
        } else if (map < 22) {
            background.getSubImage(640, 480, 640, 480).draw(0,0);   // lake/field
        } else if (map == 22 || (map >= 28 && map <= 30)) {
            background.getSubImage(2*640, 2*480, 640, 480).draw(0,0);// building
        } else if (map < 25) {
            background.getSubImage(0, 480, 640, 480).draw(0,0);     // dunes
        } else if (map < 33) {
            background.getSubImage(0,0,640,480).draw(0,0);          // shore
        } else if (map < 36) {
            background.getSubImage(2*640, 0, 640, 480).draw(0,0);   // caves
        } else if (map < 41) {
            background.getSubImage(0, 2*480, 640, 480).draw(0,0);   // mountains
        } else {
            background.getSubImage(640, 0, 640, 480).draw(0,0);     // castle
        }

        // display enemies on one side and players on another
        for (int i = 0; i < currEnemies.size(); i++) {
            Enemy enemy = currEnemies.get(i);
            if (!enemy.isDead) {
                enemy.battler.draw(400 + (i%2)*100, 150 + i*50);
                // draw name label for the enemy
                g.setColor(new Color(0, 0, 0, 70));
                g.fillRect(400 + (i%2)*100, 125 + i*50, 100, 25);
                g.setColor(Color.white);
                g.drawString(enemy.getName(), 405 + (i%2)*100, 125 + i*50);
            }
        }
        for (int i = 0; i < livingAllies.size(); i++) {
            BattleEntity entity = livingAllies.get(i);
            if (entity != null && entity.currHP > 0) {
                entity.battler.draw(50 + (i%2)*100, 150 + i*50);
                // draw name label for entity
                g.setColor(new Color(0, 0, 0, 70));
                g.fillRect(45 + (i%2)*100, 120 + i*50, 100, 25);
                g.setColor(Color.white);
                g.drawString(entity.getName(), 50 + (i%2)*100, 125 + i*50);
                // draw HUD for entity
                g.setColor(new Color(0, 0, 0, 70));
                g.fillRect(i*100, 35, 100, 65);
                g.setColor(Color.white);
                g.drawString(entity.getName(), 5+i*100, 40);
                g.drawString("HP " + String.valueOf(entity.getCurrHP()) + "/" + String.valueOf(entity.getHP()), 5+i*100, 60);
                g.drawString("MP " + String.valueOf(entity.getCurrMP()) + "/" + String.valueOf(entity.getMP()), 5+i*100, 80);
            }
        }

        // draw a shaded transparent background for the selection menu
        g.setColor(new Color(0, 0, 0, 70));
        g.fillRect(0, 430, 640, 80);
        g.setColor(Color.white);

        // if player's turn, render the skill menu
        if (isPlayerTurn) {
            if (isSelectingTarget) {
                int x_value;
                if (possibleTargets.get(0) instanceof Enemy) {
                    x_value = 400 + (highlightedTargetID%2)*100;
                } else {
                    x_value = 50 + (highlightedTargetID%2)*100;
                }
                // draw arrow above target
                g.fill(new Polygon(new float[]{x_value+25, 115+highlightedTargetID*50, x_value+75, 115 + highlightedTargetID*50, x_value + 50, 140 + highlightedTargetID*50}));
                g.drawString("SELECT A TARGET", 25, 450);

            } else if (isSelectingItem) {
                // if there are no items, tell the player and exit the item menu; resume player's turn
                // otherwise, draw the item menu
                if (consumablesOnly.size() > 0) {
                    g.drawString("SELECT AN ITEM", 25, 450);
                    // draws menu of consumable items, draws rect around selected item
                    g.setColor(new Color(0, 0, 0, 150));
                    g.fillRect(120, 100, 400, 280);
                    g.setColor(Color.white);
                    g.drawString("Press DELETE or BACKSPACE to cancel", 125, 105);
                    g.drawString("SELECT AN ITEM:", 125, 150);
                    for (Consumable item : consumablesOnly) {
                        g.drawString(item.getName() + " x " + item.getQuantity(), 125, 170 + consumablesOnly.indexOf(item) * 20);
                    }
                    g.drawRect(125, 170 + highlightedItemID * 20, 390, 20);
                } else {
                    message = "NO CONSUMABLE ITEMS TO DISPLAY";
                    isSelectingItem = false;
                }
            } else if (isSelectingSkill) {
                if (currMove.skills.size() > 2) {
                    g.drawString("SELECT A SKILL", 25, 450);
                    // draw menu window
                    g.setColor(new Color(0, 0, 0, 150));
                    g.fillRect(120, 100, 400, 280);
                    g.setColor(Color.white);
                    g.drawString("Press DELETE or BACKSPACE to cancel", 125, 105);
                    g.drawString("SELECT A SKILL:", 125, 150);
                    // draw rectangle around highlighted skill
                    g.drawRect(125, 170 + (highlightedSkillID-2)*20, 390, 20);
                    // draw each skill in the list (start from 3rd element bc the first 2 are the generic ATTACK & DEFEND skills in the menu)
                    for (int i=2; i<Combat.currMove.skills.size(); i++) {
                        g.drawString(Combat.currMove.skills.get(i).getName(), 125, 170+(i-2)*20);
                    }
                } else {
                    message = "NO SPECIAL SKILLS TO DISPLAY";
                    isSelectingSkill = false;
                }
            } else {
                // draw skill menu: attack, defend, item, skill, flee
                g.drawString("ATTACK", 25, 450);
                g.drawString("DEFEND", 100, 450);
                g.drawString("ITEM", 175, 450);
                g.drawString("SKILL", 250, 450);
                g.drawString("FLEE", 325, 450);
                // draw a rectangle around the highlighted action
                g.drawRect(15 + 75 * highlightedActionID, 440, 75, 35);
            }
        }

        // draw message (tells the player about how the battle is progressing)
        g.setColor(new Color(0, 0, 0, 70));
        g.fillRect(0, 390, 640, 25);
        g.setColor(Color.white);
        g.drawString(message, 25, 395);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        // this should be handled in the Ally/Enemy things.
        // if player move:
        // change selected skill w/arrow keys
        // get selected skill w/enter key
        // else:
        // choose an skill from the AI
        if (!isPlayerTurn) {
            selectedAction = ((Enemy) turnOrder.get(0)).decideAction();
            isPlayerTurn = turnOrder.get(0) instanceof Ally;
            isFinishedTurn = true;
        }
        if (isFinishedTurn) {
            // move around actions in the actionOrder list
            if (selectedAction != null) {
                int i = 0;
                while (i < actionOrder.size() && actionOrder.get(i).skill.delay < selectedAction.skill.delay) {
                    i++;
                }
                actionOrder.add(i, selectedAction);
            }
            // execute actions
            for (int i = 0; i < actionOrder.size(); i++) {
                BattleAction battleAction = actionOrder.get(i);
                // delete enemies and players if necessary (check if HP <= 0)
                if (battleAction.skill.delay == 0) {
                    message = battleAction.caster.getName() + " used " + battleAction.skill.getName() + ". ";
                    battleAction.skill.use(battleAction.caster, battleAction.target);
                    actionOrder.remove(i);
                    i--;
                    updateDead();
                }
            }
            for (BattleAction battleAction : actionOrder) {
                battleAction.delay--;
            }
            // consume skill effects for entity that just moved
            currMove.consumeSkillEffects();
            updateDead();
            // rotate turnOrder:
            turnOrder.add(turnOrder.remove(0));
            // check whose move it is
            isPlayerTurn = turnOrder.get(0) instanceof Ally;
            currMove = turnOrder.get(0);
            message += currMove.getName() + "'s turn.";
            // clear all the selection things
            highlightedTargetID = 0;
            highlightedItemID = 0;
            highlightedSkillID = 0;
            highlightedActionID = 0;
            selectedItem = null;
            selectedSkill = null;
            selectedAction = null;
            possibleTargets.clear();
            selectedTargets.clear();
            isSelectingItem = false;
            isSelectingTarget = false;
            isSelectingSkill = false;
            isFinishedTurn = false;
        }
        updateWin(game);
    }

    public void updateDead() {
        // assuming no resurrection; remove dead from turn order list
        for (int j = 0; j < livingAllies.size(); j++) {
            if (livingAllies.get(j) != null && livingAllies.get(j).currHP <= 0) {
                livingAllies.get(j).currHP = 0;
                turnOrder.remove(livingAllies.get(j));
                livingAllies.remove(j);
                j--;
            }
        }
        for (int i = 0; i < currEnemies.size(); i++) {
            Enemy enemy = Combat.currEnemies.get(i);
            if (enemy.currHP <= 0) {
                enemy.getDrops();
                for (int j = 0; j < Resources.enemy_db.length; j++) {
                    if (Resources.enemy_db[j].name.equals(enemy.name)) {
                        Resources.enemy_db[j].timesKilled++;
                    }
                }
                turnOrder.remove(enemy);
                currEnemies.remove(i);
            }
        }
    }

    public void updateWin(StateBasedGame game) {
        boolean alliesDead = livingAllies.size() == 0;
        boolean enemiesDead = Combat.currEnemies.size() == 0;
        if (enemiesDead || !isCombat) {
            isCombat = false;
            game.enterState(TestingGame.MAP);
        } else if (alliesDead) {
            isCombat = false;
            game.enterState(TestingGame.DEATH_SCREEN);
        }

    }

    public void animate(Graphics g, float xPos, float yPos) {
        // draw series of progressively smaller circles centered at given position
        g.setColor(Color.black);
        g.setLineWidth(5);
        for (int i=TestingGame.WIDTH; i>0; i--) {
            g.drawOval(xPos - i/2, yPos - i/2, i*2, i*2);
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
                    if (Combat.selectedSkill.MPCost > Combat.currMove.currMP) {
                        Combat.message = "Not enough MP!";
                    } else {
                        Combat.highlightedTargetID = 0;
                        Combat.isSelectingTarget = true;
                        for (Enemy enemy : Combat.currEnemies) {
                            if (!enemy.isDead) {
                                Combat.possibleTargets.add(enemy);
                            }
                        }
                    }
                    Combat.isSelectingSkill = false;
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
                            for (BattleEntity entity : Combat.livingAllies) {
                                Combat.possibleTargets.add(entity);
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
                            for (BattleEntity entity : Combat.livingAllies) {
                                Combat.possibleTargets.add(entity);
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
                    Combat.highlightedTargetID = 0;
                    Combat.isSelectingTarget = true;
                }
            } else if (Combat.isSelectingTarget) {
                if (key == Input.KEY_DOWN) {
                    if (Combat.possibleTargets.get(0) instanceof Ally) {
                        if (Combat.highlightedTargetID < Combat.livingAllies.size() - 1) {
                            Combat.highlightedTargetID++;
                        }
                    } else {
                        if (Combat.highlightedTargetID < Combat.currEnemies.size() - 1) {
                            Combat.highlightedTargetID++;
                        }
                    }
                } else if (key == Input.KEY_UP) {
                    if (Combat.possibleTargets.get(0) instanceof Ally) {
                        if (Combat.highlightedTargetID > 0) {
                            Combat.highlightedTargetID--;
                        }
                    } else {
                        if (Combat.highlightedTargetID > 0) {
                            Combat.highlightedTargetID--;
                        }
                    }
                } else if (key == Input.KEY_DELETE || key == Input.KEY_BACK) {
                    Combat.isSelectingTarget = false;
                } else if (key == Input.KEY_ENTER) {
                    if (Combat.possibleTargets.get(0) instanceof Ally) {
                        Combat.selectedTargets.add(Combat.livingAllies.get(Combat.highlightedTargetID)); // group selecion?
                    } else {
                        Combat.selectedTargets.add(Combat.currEnemies.get(Combat.highlightedTargetID));
                    }
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
                    Combat.isSelectingTarget = false;
                    Combat.isFinishedTurn = true;
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
                            for (Enemy enemy : Combat.currEnemies) {
                                if (!enemy.isDead) {
                                    Combat.possibleTargets.add(enemy);
                                }
                            }
                            Combat.highlightedTargetID = 0;
                            Combat.isSelectingTarget = true;
                            break;
                        case Combat.DEFEND:
                            // cast Combat.DEFEND on self: immediate use
                            Combat.currMove.use(Combat.currMove.skills.get(1), Combat.currMove);
                            // temporary patch
                            Combat.message = Combat.currMove.getName() + " defended with " + Combat.currMove.skills.get(1).getName() + ". ";
                            Combat.isFinishedTurn = true;
                            break;
                        case Combat.ITEM:
                            // select item to use
                            Combat.highlightedItemID = 0;
                            Combat.isSelectingItem = true;
                            break;
                        case Combat.SKILL:
                            // select skill to use
                            Combat.highlightedSkillID = 2;
                            Combat.isSelectingSkill = true;
                            break;
                        case Combat.FLEE:
                            // flee somehow
                            if (Combat.currEnemies.size() < Combat.livingAllies.size()/2) {
                                Combat.isCombat = false;
                            } else {
                                Combat.message = "CANNOT FLEE, THERE ARE TOO MANY ENEMIES";
                            }
                            break;
                        default:
                            // shouldn't happen but
                            System.out.println("Error: Combat.selectedActionID is not in array bounds");
                    }
                }
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