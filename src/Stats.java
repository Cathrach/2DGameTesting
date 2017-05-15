/**
 * Created by amy on 5/14/17.
 */

import org.newdawn.slick.*;
import org.newdawn.slick.geom.*;
import java.util.ArrayList;
public class Stats {

    // I have no idea what I'm doing :)

    public static void init() {
        // list of current stats for each unit
    }
    public static void render(Graphics g) {
        // temporary! Will add GUI & fix up later
        // TODO: draw stats GUI
        g.drawString("STATS WIP", 200, 150);
        g.drawString("Press ESC to return to menu", 200, 180);
    }
    public static void update(GameContainer container, int delta) {
        // No need for updates because user can do these
    }
}