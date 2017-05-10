/**
 * Created by serinahu on 5/10/17.
 */
public class BattleAction {
    BattleEntity caster;
    Skill skill;
    BattleEntity target;

    public BattleAction(BattleEntity caster, Skill skill, BattleEntity target) {
        this.caster = caster;
        this.skill = skill;
        this.target = target;
    }


}
