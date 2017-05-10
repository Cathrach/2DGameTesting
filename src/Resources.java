import org.newdawn.slick.*;

import java.util.ArrayList;

/**
 * Created by serinahu on 5/9/17.
 */
public class Resources {
    static int money;
    static Entity[] party;
    static ArrayList<Enemy> currEnemies; // when making enemies, PLEASE clone the enemy! don't just add an element from the database!
    static Enemy[] enemy_db;

    // everything has an ID which corresponds to their position in this array!
    static Map[] map_db;
    static Item[] item_db;
    static Skill[] skill_db;
    static SkillEffect[] skilleffect_db;

    public static void init() throws SlickException {
        map_db = new Map[2];
        map_db[0] = new Map("maps/sample.tmx");
        map_db[1] = new Map("maps/next.tmx");
        // check if there's a "save"; if not, make a new entity
        Resources.party = new Entity[4];
        Resources.party[0] = new Entity(0, 0, new BattleEntity("square", true));
        // similarly for other databases

        skill_db = new Skill[1];
        skill_db[0] = new Skill("Attack", new Image(""),
                0, 0, 1, 0, 0, TargetType.SINGLE_ENEMY,
                new SkillEffect[]{}, new TargetType[]{});
    }
}
