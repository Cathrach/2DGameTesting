import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created by serinahu on 5/12/17.
 */
public class Dialogue {
    // an array of lines
    DialogueLine[] lines;
    int currLineID;
    String[] conditions;
    int[] startLineIDsIfCondition;

    public Dialogue(DialogueLine[] lines) {
        this.lines = lines;
        this.conditions = new String[]{};
    }

    public Dialogue(DialogueLine[] lines, String[] conditions, int[] startLineIDsIfCondition) {
        this.lines = lines;
        this.conditions = conditions;
        this.startLineIDsIfCondition = startLineIDsIfCondition;
        this.currLineID = 0;
        for (int i = 0; i < conditions.length; i++) {
            if (Resources.triggers.get(conditions[i])) {
                currLineID = startLineIDsIfCondition[i];
            }
        }
    }

    public void reset() {
        this.currLineID = 0;
        for (int i = 0; i < conditions.length; i++) {
            if (Resources.triggers.get(conditions[i])) {
                currLineID = startLineIDsIfCondition[i];
            }
        }
    }

    // go to next line
    public void nextLine(StateBasedGame game) {
        if (lines[currLineID].isLast) {
            if (lines[currLineID].mapID >= 0) {
                MapState.changeMap(lines[currLineID].mapID);
            }
            game.enterState(TestingGame.MAP);
        } else {
            currLineID++;
        }
    }

    // render dialogue
    public void render(Graphics g) {
        lines[currLineID].render(g);
    }
}
