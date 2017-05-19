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
        for (Entity entity : Resources.party) {
            // just one for now
            RoundedRectangle frame = new RoundedRectangle(10, 30, 350, 100, 10);
            g.draw(frame);
            if (entity != null) {
                g.drawString(entity.battleEntity.name, 25, 35);
                entity.getSprite().getSubImage(0, 0, 32, 45).draw(25, 60);
                g.drawString("HP: " + entity.battleEntity.currHP + "/" + entity.battleEntity.getHP(), 70, 60);
                g.drawString("MP: " + entity.battleEntity.currMP + "/" + entity.battleEntity.getMP(), 70, 85);
                g.drawString("ATK: " + entity.battleEntity.getATK(), 170, 60);
                g.drawString("DEF: " + entity.battleEntity.getDEF(), 170, 85);
                for (int i = 0; i < entity.battleEntity.equips.size(); i++) {
                    Equipment equipment = entity.battleEntity.equips.get(i);
                    g.drawString(equipment.getName(), 270, 35 + 25 * i);
                }
            }
        }
        RoundedRectangle skillFrame = new RoundedRectangle(365, 30, 275, 420, 10);
        g.draw(skillFrame);
        g.drawString("SKILLS", 380, 35);
        for (int i = 0; i < Resources.party[0].battleEntity.skills.size(); i++) {
            Skill skill = Resources.party[0].battleEntity.skills.get(i);
            skill.getIcon().getScaledCopy(20, 20).draw(380, 60 + 25 * i);
            g.drawString(skill.getName(), 410, 60 + 25 * i);
        }
        g.drawString("Press ESC to return to menu", 10, 10);
    }
    public static void update(GameContainer container, int delta) {
        // No need for updates because user can do these
    }
}