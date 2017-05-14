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
    public Consumable(String n, int v, int q){  //
        super(n, v, q);
    }
    public void use(BattleEntity target) {
        target.currHP -= fixDamage;
        target.currHP += fixHeal;
        target.currHP += (target.getHP() * ratioHeal);
        target.baseHP += fixHP;
        target.baseATK += fixATK;
        target.baseDEF += fixDEF;
    }
}