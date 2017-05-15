import org.newdawn.slick.*;
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
    static Dialogue currDialogue;
    static Dictionary<String, Boolean> triggers;
    public static void init() throws SlickException {
        map_db = new Map[2];
        map_db[0] = new Map("maps/sample.tmx");
        map_db[1] = new Map("maps/next.tmx");
        // check if there's a "save"; if not, make a new entity
        Resources.party = new Entity[4];
        Resources.party[0] = new Entity(0, 0, 1, 0, new Ally("square", true));
        // similarly for other databases
        skilleffect_db = new SkillEffect[1];
        skilleffect_db[0] = new SkillEffect(0,0, 0, 0, 0, 0, 0, 0, 1.5f, 0, 0, 0);
        skill_db = new Skill[2];
        skill_db[0] = new Skill("Attack", new Image("skills/attack.png"),
                0, 0, 0, 1, 0, 0, TargetType.SINGLE_ENEMY,
                new SkillEffect[]{}, new TargetType[]{});
        skill_db[1] = new Skill("Defend", new Image("skills/defend.png"),
                0, 0, 0, 0, 0, 0, TargetType.SINGLE_ENEMY,
                new SkillEffect[]{skilleffect_db[0]}, new TargetType[]{});
        item_db = new Item[3];
        item_db[0] = new Equipment("pencil", 50, 0, Equipment.EquipType.WEAPON, 0, 5, 0, 0, 0, 0);
        item_db[1] = new Consumable("health potion", 10, 0, TargetType.SINGLE_ALLY, 5, 0, 0, 0, 0, 0);
        item_db[2] = new Equipment("iron sword", 100, 0, Equipment.EquipType.WEAPON, 0, 10, 0, 0, 0, 0);
        // check if save; if not, inventory is empty (will contain a weapon later?)
        currDialogue = new Dialogue(new DialogueLine[]{});

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