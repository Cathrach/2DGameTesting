/**
 * Created by serinahu on 5/6/17.
 */
public class SkillEffect {
    int turns;
    // immediate: buffs
    private int fixHP;
    private int fixMP;
    private int fixATK;
    private int fixDEF;
    private float ratioHP;
    private float ratioMP;
    private float ratioATK;
    private float ratioDEF;
    // non-immediate: heals, poison, etc.
    private int fixHeal;
    private float ratioHeal;
    private int poisonDmg;

    public SkillEffect(int turns, int fixHP, int fixMP, int fixATK, int fixDEF, float ratioHP, float ratioMP, float ratioATK, float ratioDEF, int fixHeal, float ratioHeal, int poisonDmg) {
        this.turns = turns;
        this.fixHP = fixHP;
        this.fixMP = fixMP;
        this.fixATK = fixATK;
        this.fixDEF = fixDEF;
        this.ratioHP = ratioHP;
        this.ratioMP = ratioMP;
        this.ratioATK = ratioATK;
        this.ratioDEF = ratioDEF;
        this.fixHeal = fixHeal;
        this.ratioHeal = ratioHeal;
        this.poisonDmg = poisonDmg;
    }

    public void addTo(BattleEntity target) {
        // inflict buffs
        target.fixSkillHP += fixHP;
        target.fixSkillMP += fixMP;
        target.fixSkillATK += fixATK;
        target.fixSkillDEF += fixDEF;
        target.ratioSkillHP *= ratioHP;
        target.ratioSkillMP *= ratioMP;
        target.ratioSkillATK *= ratioATK;
        target.ratioSkillDEF *= ratioDEF;
    }

    public void elapseTurn(BattleEntity target) {
        // poisons/heals that trigger every turn
        target.currHP -= poisonDmg;
        target.currHP += fixHeal;
        target.currHP += (target.getHP() * ratioHeal);
        // remove a turn
        turns--;
        // if turns = 0, remove buffs
        if (turns <= 0) {
            target.fixSkillHP -= fixHP;
            target.fixSkillATK -= fixATK;
            target.fixSkillDEF -= fixDEF;
            target.ratioSkillHP /= ratioHP;
            target.ratioSkillATK /= ratioATK;
            target.ratioSkillDEF /= ratioDEF;
        }
    }
}
