/**
 * Created by serinahu on 5/10/17.
 */
public class BattleAction {
    BattleEntity caster;
    Skill skill;
    BattleEntity target;
    int delay;

    public BattleAction(BattleEntity caster, Skill skill, BattleEntity target) {
        this.caster = caster;
        this.skill = skill;
        this.target = target;
        this.delay = skill.delay;
    }

    @Override
    public String toString() {
        return "BattleAction{" +
                "caster=" + caster +
                ", skill=" + skill.getName() +
                ", target=" + target +
                '}';
    }
}
