/**
 * Created by serinahu on 5/4/17.
 */


public class BattleEntity {
    private String name;
    private int currHP;
    private int baseHP;
    private int baseATK;
    private int baseDEF;
    private boolean isPlayer;

    // equips here

    // skills here

    // current skill effects here

    public BattleEntity(String name, boolean isPlayer) {
        this.name = name;
        currHP = 20;
        baseHP = 20;
        baseATK = 5;
        baseDEF = 3;
        this.isPlayer = isPlayer;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCurrHP() {
        return currHP;
    }

    public void setCurrHP(int currHP) {
        this.currHP = currHP;
    }

    public int getBaseHP() {
        return baseHP;
    }

    public void setBaseHP(int baseHP) {
        this.baseHP = baseHP;
    }

    public int getBaseATK() {
        return baseATK;
    }

    public void setBaseATK(int baseATK) {
        this.baseATK = baseATK;
    }

    public int getBaseDEF() {
        return baseDEF;
    }

    public void setBaseDEF(int baseDEF) {
        this.baseDEF = baseDEF;
    }

    // get/modify skills list

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
        // elapse turn for each skill
        // if skill has no turns left, remove from list
    }
}
