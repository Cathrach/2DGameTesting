import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

/**
 * Created by serinahu on 5/9/17.
 */
public class Cutscene extends BasicGameState {
    private int id;
    static boolean isCutscene;
    static Dialogue currDialogue;
    private static int timeSinceLastPress;
    private final int timeToWait = 100;

    public Cutscene(int id) {
        this.id = id;
    }
    @Override
    public int getID() {
        return id;
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        isCutscene = false;
        timeSinceLastPress = 0;
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        if (game.getCurrentStateID() == TestingGame.CUTSCENE) {
            Resources.map_db[MapState.currentMapID].render(0,0);
            Resources.party.get(0).render(container, game, g);
            currDialogue.render(g);
        } else if (game.getCurrentStateID() == TestingGame.DEATH_SCREEN) {
            g.drawImage(new Image("images/backgrounds/deathScreen.png"), 0,0);
        }
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        if (game.getCurrentStateID() == TestingGame.CUTSCENE) {
            Input input = container.getInput();
            if (timeSinceLastPress > timeToWait) {
                if (input.isKeyPressed(Input.KEY_ENTER) || input.isKeyPressed(Input.KEY_SPACE)) {
                    currDialogue.nextLine(game);
                    timeSinceLastPress = 0;
                }
            } else {
                timeSinceLastPress += delta;
            }
        }
    }

    public static void changeDialogue(int dialogueID) {
        currDialogue = Resources.dialogue_db[dialogueID];
        currDialogue.reset();
        timeSinceLastPress = 0;
    }

    // patch for dialogue glitch where first dialogue lines were skipped
    public static void enter(GameContainer container, StateBasedGame game, int delta) {
        Input input = container.getInput();
        game.enterState(TestingGame.CUTSCENE);
        while (input.isKeyPressed(Input.KEY_ENTER) || input.isKeyPressed(Input.KEY_SPACE)) {
            timeSinceLastPress = 0;
        }
    }

}
