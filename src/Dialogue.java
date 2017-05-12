import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created by serinahu on 5/12/17.
 */
public class Dialogue {
    // an array of lines
    DialogueLine[] lines;
    int currLineID;
    String condition;
    int startLineIDIfCondition;

    public Dialogue(DialogueLine[] lines, String condition, int startLineIDIfCondition) {
        this.lines = lines;
        this.condition = condition;
        this.startLineIDIfCondition = startLineIDIfCondition;
        if (Resources.triggers.get(condition)) {
            currLineID = startLineIDIfCondition;
        } else {
            currLineID = 0;
        }
    }

    public void reset() {
        if (Resources.triggers.get(condition)) {
            currLineID = startLineIDIfCondition;
        } else {
            currLineID = 0;
        }
    }

    // go to next line
    public void nextLine(StateBasedGame game) {
        if (lines[currLineID].isLast) {
            game.enterState(TestingGame.MAP);
        } else {
            currLineID++;
        }
    }

    // render dialogue
    public void render(Graphics g) {
        // draw the current line's speaker
        // draw the current line in a rectangle
    }
}
