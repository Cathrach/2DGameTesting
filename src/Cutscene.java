import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

/**
 * Created by serinahu on 5/9/17.
 */
public class Cutscene extends BasicGameState {
    private int id;
    static boolean isCutscene;
    static boolean isShop;
    static Dialogue currDialogue;
    private static int timeElapsedSinceLastPress;
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
        isShop = false;
        timeElapsedSinceLastPress = 0;
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        if (isShop) {
            Shop.render(g);
        } else {
            Resources.map_db[MapState.currentMapID].render(0,0);
            Resources.party.get(0).render(container, game, g);
            currDialogue.render(g);
        }
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        Input input = container.getInput();
        if (timeElapsedSinceLastPress >= timeToWait) {
            if (input.isKeyPressed(Input.KEY_ENTER) || input.isKeyPressed(Input.KEY_SPACE)) {
                currDialogue.nextLine(game);
                timeElapsedSinceLastPress = 0;
            }
        } else if (timeElapsedSinceLastPress >= 0 && timeElapsedSinceLastPress < timeToWait) {
            timeElapsedSinceLastPress += delta;
        }
    }

    public static void changeDialogue(int dialogueID) {
        timeElapsedSinceLastPress = 0;
        currDialogue = Resources.dialogue_db[dialogueID];
        currDialogue.reset();
    }

}
