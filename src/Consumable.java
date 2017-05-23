import java.lang.annotation.Target;

/**
 * Created by serinahu on 5/8/17.
 */
public class Consumable extends Item {
    private boolean isTargetEnemy;
    // all immediate use
    private int fixDamage;
    private int fixHeal;
    private float ratioHeal;
    private int fixMPHeal;
    private float ratioMPHeal;
    private int fixHP;
    private int fixMP;
    private int fixATK;
    private int fixDEF;
    private TargetType targetType;
    public Consumable(String n, int v, int q, String d, TargetType targetType, int fixDamage, int fixHeal, float ratioHeal, int fixMPHeal, float ratioMPHeal, int fixHP, int fixMP, int fixATK, int fixDEF) {  //
        super(n, v, q, d);
        this.fixDamage = fixDamage;
        this.fixHeal = fixHeal;
        this.ratioHeal = ratioHeal;
        this.fixMPHeal = fixMPHeal;
        this.ratioMPHeal = ratioMPHeal;
        this.fixHP = fixHP;
        this.fixMP = fixMP;
        this.fixATK = fixATK;
        this.fixDEF = fixDEF;
        this.targetType = targetType;
        itemType = "CONSUMABLE - " + String.valueOf(targetType);
    }
    public void use(BattleEntity target) {
        target.currHP -= fixDamage;
        target.currHP += fixHeal;
        target.currHP += (target.getHP() * ratioHeal);
        target.currMP += fixMPHeal;
        target.currMP += (target.getMP() * ratioMPHeal);
        target.baseHP += fixHP;
        target.baseMP += fixMP;
        target.baseATK += fixATK;
        target.baseDEF += fixDEF;
    }

    public TargetType getTargetType() {
        return targetType;
    }
}