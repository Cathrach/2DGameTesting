import java.lang.annotation.Target;

/**
 * Created by serinahu on 5/8/17.
 */
public class Consumable extends Item {
    private boolean isTargetEnemy;
    // all immediate use
    private int fixDamage;
    private int fixHeal;
    private int ratioHeal;
    private int fixHP;
    private int fixATK;
    private int fixDEF;
    private TargetType targetType;
    public Consumable(String n, int v, int q, TargetType targetType, int fixDamage, int fixHeal, int ratioHeal, int fixHP, int fixATK, int fixDEF) {  //
        super(n, v, q);
        this.fixDamage = fixDamage;
        this.fixHeal = fixHeal;
        this.ratioHeal = ratioHeal;
        this.fixHP = fixHP;
        this.fixATK = fixATK;
        this.fixDEF = fixDEF;
        this.targetType = targetType;
    }
    public void use(BattleEntity target) {
        target.currHP -= fixDamage;
        target.currHP += fixHeal;
        target.currHP += (target.getHP() * ratioHeal);
        target.baseHP += fixHP;
        target.baseATK += fixATK;
        target.baseDEF += fixDEF;
    }

    public TargetType getTargetType() {
        return targetType;
    }
}