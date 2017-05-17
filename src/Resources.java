import org.newdawn.slick.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Dictionary;
/**
 * Created by serinahu on 5/9/17.
 */
public class Resources {
    static int money;
    static Entity[] party;
    static Enemy[] enemy_db;
    // everything has an ID which corresponds to their position in this array!
    static Map[] map_db;
    static Item[] item_db;
    static Skill[] skill_db;
    static SkillEffect[] skilleffect_db;
    static Dialogue[] dialogue_db;
    static Dictionary<String, Boolean> triggers;
    public static void init() throws SlickException, IOException {
        BufferedReader mapReader = new BufferedReader(new FileReader("db/maps.txt"));
        int numMaps = Integer.parseInt(mapReader.readLine());
        map_db = new Map[numMaps];
        for (int i = 0; i < numMaps; i++) {
            map_db[i] = new Map("maps/" + mapReader.readLine() + ".tmx");
        }
        // check if there's a "save"; if not, make a new entity
        Resources.party = new Entity[4];
        Resources.party[0] = new Entity(0, 0, 1, 0, new Ally("heroine"));
        // similarly for other databases
        BufferedReader skillEffectReader = new BufferedReader(new FileReader("db/skilleffects.txt"));
        int numSkillEffects = Integer.parseInt(skillEffectReader.readLine());
        skilleffect_db = new SkillEffect[numSkillEffects];
        for (int i = 0; i < numSkillEffects; i++) {
            String[] effectData = skillEffectReader.readLine().split(" ");
            skilleffect_db[i] = new SkillEffect(Integer.parseInt(effectData[0]), // turns
                    Integer.parseInt(effectData[1]), // fix HP
                    Integer.parseInt(effectData[2]), // fix MP
                    Integer.parseInt(effectData[3]), // fix ATK
                    Integer.parseInt(effectData[4]), // fix DEF
                    Float.parseFloat(effectData[5]), // ratio HP
                    Float.parseFloat(effectData[6]), // ratio MP
                    Float.parseFloat(effectData[7]), // ratio ATK
                    Float.parseFloat(effectData[8]), // ratio DEF
                    Integer.parseInt(effectData[9]), // fix heal
                    Float.parseFloat(effectData[10]), // ratio heal
                    Integer.parseInt(effectData[11])); // poison damage
        }
        skill_db = new Skill[2];
        skill_db[0] = new Skill("Attack", new Image("skills/attack.png"),
                0, 0, 0, 1, 0, 0, TargetType.SINGLE_ENEMY,
                new SkillEffect[]{}, new TargetType[]{});
        skill_db[1] = new Skill("Defend", new Image("skills/defend.png"),
                0, 0, 0, 0, 0, 0, TargetType.SINGLE_ENEMY,
                new SkillEffect[]{skilleffect_db[0]}, new TargetType[]{});
        item_db = new Item[3];
        item_db[0] = new Equipment("#2 Pencil", 50, 0, "A #2 Pencil: No other marks will be accepted~", Equipment.EquipType.WEAPON, 0, 0, 5, 0, 1, 1, 1, 1);
        item_db[1] = new Consumable("Water", 10, 0, "Rather scarce since potatoes keep boiling it.", TargetType.SINGLE_ALLY, 5, 0, 0, 0, 0, 0);
        item_db[2] = new Equipment("Pen", 100, 0, "Blue or black pens will make your free response easier to read!", Equipment.EquipType.WEAPON, 0, 0, 10, 0, 1, 1, 1, 1);
        // check if save; if not, inventory is empty (will contain a weapon later?)
        dialogue_db = new Dialogue[1];
        dialogue_db[0] = new Dialogue(
                new DialogueLine[]{
                        new DialogueLine("It is Tuesday Morning, May 9th.", new Image("sprites/heroine.png")),
                        new DialogueLine("You will be taking the AP BC Calculus Exam.", new Image("sprites/heroine.png"), true, -1, "")
                },
                new String[]{},
                new int[]{}
        );

        Inventory.addItem("pencil", 1);
    }

    public static int getItemID(String itemName) {
        for (int i = 0; i < item_db.length; i++) {
            if (item_db[i].getName().equals(itemName)) {
                return i;
            }
        }
        return -1;
    }
}