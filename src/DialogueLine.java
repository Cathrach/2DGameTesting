import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

/**
 * Created by serinahu on 5/12/17.
 */
public class DialogueLine {
    String[] lineSplit;
    Image speaker;
    boolean isLast;
    String triggerChange;
    int mapID;

    public DialogueLine(String line, Image speaker) {
        this.lineSplit = line.split("//");
        this.speaker = speaker;
        this.isLast = false;
        this.mapID = -1;
        this.triggerChange = "";
    }

    public DialogueLine(String line, Image speaker, boolean isLast, int mapID, String triggerChange) {
        this.lineSplit = line.split("//");
        this.speaker = speaker;
        this.isLast = isLast;
        if (this.isLast) {
            this.mapID = mapID;
        } else {
            this.mapID = -1; // -1 for old map
        }
        this.triggerChange = triggerChange;
    }

    public void render(Graphics g) {
        // render line in a rectangle
        g.drawRect(0, 400, 640, 80);
        for (int i=0; i<lineSplit.length; i++) {
            g.drawString(lineSplit[i], 20, 420+i*15);
        }
        // render speaker
        this.speaker.draw(0, 0);
    }
}
