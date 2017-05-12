import java.util.ArrayList;

/**
 * Created by serinahu on 5/8/17.
 */
public class Enemy extends BattleEntity {
    Item[] drops;
    int goldDropped;

    public BattleAction decideAction() {
        // default: just attack
        return new BattleAction(this, skills.get(0), Resources.party[0].battleEntity);
    }

    public Enemy() {
        super();
    }

    public Enemy(String name, int baseHP, int baseMP, int baseATK, int baseDEF, Item[] drops, int goldDropped) {
        this.name = name;
        this.isPlayer = false;
        this.drops = drops;
        this.goldDropped = goldDropped;
        this.baseHP = baseHP;
        this.baseMP = baseMP;
        this.baseATK = baseATK;
        this.baseDEF = baseDEF;
        this.currHP = baseHP;
        this.currMP = baseMP;
    }

    public Enemy(Enemy anotherEnemy) {
        this.name = name;
        baseHP = anotherEnemy.baseHP;
        baseMP = anotherEnemy.baseMP;
        baseATK = anotherEnemy.baseATK;
        baseDEF = anotherEnemy.baseDEF;
        currHP = baseHP;
        currMP = baseMP;
        ratioSkillHP = 1;
        ratioSkillATK = 1;
        ratioSkillDEF = 1;
        skills = anotherEnemy.skills;
    }
}
