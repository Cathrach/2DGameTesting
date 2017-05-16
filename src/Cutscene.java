import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

/**
 * Created by serinahu on 5/9/17.
 */
public class Cutscene extends BasicGameState {
    private int id;
    static boolean isCutscene;
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
        timeElapsedSinceLastPress = 0;
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        currDialogue.render(g);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        Input input = container.getInput();
        if (timeElapsedSinceLastPress == 0) {
            if (input.isKeyPressed(Input.KEY_ENTER) || input.isKeyPressed(Input.KEY_SPACE)) {
                System.out.println("key pressed");
                currDialogue.nextLine(game);
            }
            timeElapsedSinceLastPress += delta;
        } else if (timeElapsedSinceLastPress > 0 && timeElapsedSinceLastPress < timeToWait) {
            timeElapsedSinceLastPress += delta;
        } else if (timeElapsedSinceLastPress >= timeToWait) {
            timeElapsedSinceLastPress = 0;
        }

    }

    public static void changeDialogue(int dialogueID) {
        timeElapsedSinceLastPress = 0;
        currDialogue = Resources.dialogue_db[dialogueID];
        currDialogue.reset();
    }

}
