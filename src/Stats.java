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
        for (int i = 0; i < Resources.party.size(); i++) {
            Entity entity = Resources.party.get(i);
            // just one for now
            RoundedRectangle frame = new RoundedRectangle(10, 30 + 100 * i, 390, 100, 10);
            g.draw(frame);
            if (entity != null) {
                g.drawString(entity.battleEntity.name, 25, 35 + 100 * i);
                entity.getSprite().getSubImage(0, 0, 32, 45).draw(25, 60 + 100 * i);
                g.drawString("HP: " + entity.battleEntity.currHP + "/" + entity.battleEntity.getHP(), 70, 60 + 100 * i);
                g.drawString("MP: " + entity.battleEntity.currMP + "/" + entity.battleEntity.getMP(), 70, 85 + 100 * i);
                g.drawString("ATK: " + entity.battleEntity.getATK(), 170, 60 + 100 * i);
                g.drawString("DEF: " + entity.battleEntity.getDEF(), 170, 85 + 100 * i);
                for (int j = 0; j < entity.battleEntity.equips.size(); j++) {
                    Equipment equipment = entity.battleEntity.equips.get(j);
                    g.drawString(equipment.getName(), 250, 35 + 100*i + 25 * j);
                }
            }
        }
        RoundedRectangle skillFrame = new RoundedRectangle(405, 30, 235, 420, 10);
        g.draw(skillFrame);
        g.drawString("SKILLS", 420, 35);
        for (int i = 0; i < Resources.party.get(0).battleEntity.skills.size(); i++) {
            Skill skill = Resources.party.get(0).battleEntity.skills.get(i);
            skill.getIcon().getScaledCopy(20, 20).draw(420, 60 + 25 * i);
            g.drawString(skill.getName(), 445, 60 + 25 * i);
        }
        g.drawString("Press ESC to return to menu", 10, 450);
    }
    public static void update(GameContainer container, int delta) {
        // No need for updates because user can do these
    }
}