import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.util.ArrayList;

/**
 * Created by serinahu on 5/4/17.
 */


public class BattleEntity {
    String name;
    int currHP;
    int baseHP;
    int currMP;
    int baseMP;
    int baseATK;
    int baseDEF;
    int fixSkillHP;
    float ratioSkillHP;
    int fixSkillMP;
    float ratioSkillMP;
    int fixSkillATK;
    float ratioSkillATK;
    int fixSkillDEF;
    float ratioSkillDEF;
    boolean isPlayer;
    Image battler;
    String battlerPath;

    // skills here
    ArrayList<Skill> skills;
    // current skill effects here
    ArrayList<SkillEffect> currSkillEffects;

    public BattleEntity() {
        this("default", false, "images/sprites/enemies/Limit.png", 20, 10, 5, 3);
    }

    public BattleEntity(String name, boolean isPlayer, String battlerPath, int baseHP, int baseMP, int baseATK, int baseDEF) {
        this.name = name;
        this.currHP = baseHP;
        this.baseHP = baseHP;
        this.currMP = baseMP;
        this.baseMP = baseMP;
        this.baseATK = baseATK;
        this.baseDEF = baseDEF;
        ratioSkillHP = 1;
        ratioSkillMP = 1;
        ratioSkillATK = 1;
        ratioSkillDEF = 1;
        this.isPlayer = isPlayer;
        skills = new ArrayList<>();
        currSkillEffects = new ArrayList<>();
        this.battlerPath = battlerPath;
        try {
            this.battler = new Image(battlerPath);
        } catch (SlickException e) {
            e.printStackTrace();
        }
        addSkill(0);
        addSkill(1);
    }

    public int getHP() {
        return (int) Math.floor((baseHP + fixSkillHP) * ratioSkillHP);
    }

    public int getCurrHP() { return currHP; }

    public int getATK() {
        return (int) Math.floor((baseATK + fixSkillATK) * ratioSkillATK);
    }

    public int getDEF() {
        return (int) Math.floor((baseDEF + fixSkillDEF) * ratioSkillDEF);
    }

    public int getMP() {
        return (int) Math.floor((baseMP + fixSkillMP) * ratioSkillMP);
    }

    public int getCurrMP() { return currMP; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // get/modify skills list
    public void addSkill(int skillID) {
        skills.add(Resources.skill_db[skillID]);
    }

    // use skill
    public void use(Skill skill, BattleEntity target) {
        skill.use(this, target);
    }

    public void addSkillEffect(SkillEffect skillEffect) {
        // add to the skill effect list
        // trigger any buffs, etc
        skillEffect.addTo(this);
    }

    // consume skill effects after turn (poison, heals, etc)
    public void consumeSkillEffects() {
        for (int i = 0; i < currSkillEffects.size(); i++) {
            // elapse turn for each skill
            // if skill has no turns left, remove from list
            SkillEffect skillEffect = currSkillEffects.get(i);
            System.out.println(skillEffect);
            skillEffect.elapseTurn(this);
            if (skillEffect.turns <= 0) {
                currSkillEffects.remove(i);
            }
        }
    }
}