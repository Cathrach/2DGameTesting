import org.newdawn.slick.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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
        // read skills from text file
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
        skill_db = new Skill[9];
        skill_db[0] = new Skill("Solve for X", new Image("skills/attack.png"),
                0, 0, 0, 1, 0, 1, TargetType.SINGLE_ENEMY,
                new SkillEffect[]{}, new TargetType[]{});
        skill_db[1] = new Skill("Simplify", new Image("skills/defend.png"),
                0, 0, 0, 0, 0, 0, TargetType.SINGLE_ENEMY,
                new SkillEffect[]{skilleffect_db[0]}, new TargetType[]{TargetType.SELF});
        skill_db[2] = new Skill("Derive", new Image("skills/attack.png"), 0, 10, 0, 1.5f, 0, 1, TargetType.SINGLE_ENEMY,
                new SkillEffect[]{}, new TargetType[]{});
        skill_db[3] = new Skill("Integrate", new Image("skills/attack.png"), 0, 20, 0, 2, 0, 1, TargetType.SINGLE_ENEMY,
                new SkillEffect[]{}, new TargetType[]{});
        skill_db[4] = new Skill("Bash", new Image("skills/attack.png"), 0, 50, 50, 0, 0, 1, TargetType.SINGLE_ENEMY,
                new SkillEffect[]{skilleffect_db[1]}, new TargetType[]{TargetType.SELF});
        skill_db[5] = new Skill("+C", new Image("skills/defend.png"), 1, 15, 0, 0, 0, 0, TargetType.ALL_ALLIES,
                new SkillEffect[]{skilleffect_db[2]}, new TargetType[]{TargetType.ALL_ALLIES});
        skill_db[6] = new Skill("Get Triggy", new Image("skills/attack.png"), 0, 20, 0, 1, 0, 0.5f, TargetType.SINGLE_ENEMY,
                new SkillEffect[]{}, new TargetType[]{});
        skill_db[7] = new Skill("Go on a Tangent", new Image("skills/attack.png"), 0, 20, 0, 0, 0, 0, TargetType.ALL_ENEMIES,
                new SkillEffect[]{skilleffect_db[3]}, new TargetType[]{TargetType.ALL_ENEMIES});
        skill_db[8] = new Skill("Hint Hint Hint", new Image("skills/defend.png"), 1, 30, 0, 0, 0, 0, TargetType.SINGLE_ENEMY,
                new SkillEffect[]{}, new TargetType[]{});

        // read items from text file; initialize list of items with quantity 0 each
        BufferedReader equipmentReader = new BufferedReader(new FileReader("db/equipment.txt"));
        BufferedReader consumableReader = new BufferedReader(new FileReader("db/consumables.txt"));
        int numEquips = Integer.parseInt(equipmentReader.readLine());
        int numConsum = Integer.parseInt(consumableReader.readLine());
        int numItems = numEquips + numConsum;
        item_db = new Item[numItems];
        // read list of Equipment objects
        for (int i=0; i<numEquips; i++) {
            String[] itemData = equipmentReader.readLine().split(" >> ");
            item_db[i] = new Equipment(
                    itemData[0],                                // name
                    Integer.parseInt(itemData[1]),              // value
                    0,                                          // quantity
                    itemData[2],                                // description
                    Equipment.EquipType.valueOf(itemData[3]),   // equipment type
                    Integer.parseInt(itemData[4]),              // fixHP
                    Integer.parseInt(itemData[5]),              // fixMP
                    Integer.parseInt(itemData[6]),              // fixATK
                    Integer.parseInt(itemData[7]),              // fixDEF
                    Float.parseFloat(itemData[8]),              // ratioHP
                    Float.parseFloat(itemData[9]),              // ratioMP
                    Float.parseFloat(itemData[10]),              // ratioATK
                    Float.parseFloat(itemData[11])              // ratioDEF
            );
        }
        // read list of Consumable objects
        for (int i=0; i<numConsum; i++) {
            String[] itemData = consumableReader.readLine().split(" >> ");
            item_db[i + numEquips] = new Consumable(
                    itemData[0],                                // name
                    Integer.parseInt(itemData[1]),              // value
                    0,                                          // quantity
                    itemData[2],                                // description
                    TargetType.valueOf(itemData[3]),            // target type
                    Integer.parseInt(itemData[4]),              // fixDamage
                    Integer.parseInt(itemData[5]),              // fixHeal
                    Integer.parseInt(itemData[6]),              // ratioHeal
                    Integer.parseInt(itemData[7]),              // fixHP
                    Integer.parseInt(itemData[8]),              // fixATK
                    Integer.parseInt(itemData[9])               // fixDEF
            );
        }

        // read dialogue from text file
        dialogue_db = new Dialogue[1];
        dialogue_db[0] = new Dialogue(
                new DialogueLine[]{
                        new DialogueLine("It is Tuesday Morning, May 9th.", new Image("sprites/heroine.png")),
                        new DialogueLine("You will be taking the AP BC Calculus Exam.", new Image("sprites/heroine.png"), true, -1, "")
                },
                new String[]{},
                new int[]{}
        );

        // enemies
        enemy_db = new Enemy[1];
        enemy_db[0] = new Enemy("Slime", "sprites/enemies/03_slime.png", 10, 0, 3, 3, new EnemyDrop[]{new EnemyDrop(item_db[1], 0.5f)}, 10);

        // maps
        BufferedReader mapReader = new BufferedReader(new FileReader("db/maps.txt"));
        int numMaps = Integer.parseInt(mapReader.readLine());
        map_db = new Map[numMaps];
        for (int i = 0; i < numMaps; i++) {
            map_db[i] = new Map("maps/" + mapReader.readLine() + ".tmx");
        }

        // check if there's a "save"; if not, make a new entity
        Resources.party = new Entity[4];
        Resources.party[0] = new Entity(337, 254, 1, 0, new Ally("Heroine", "sprites/testing_player.png"));
        // similarly for other databases
        Inventory.addItem("#2 Pencil", 1);
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