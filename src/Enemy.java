import java.util.ArrayList;
import java.util.Arrays;
import org.newdawn.slick.*;

/**
 * Created by serinahu on 5/8/17.
 */
public class Enemy extends BattleEntity {
    EnemyDrop[] drops;
    int goldDropped;
    boolean isDead;
    int timesKilled;

    public BattleAction decideAction() {
        // default: just attack
        return new BattleAction(this, skills.get(0), Resources.party.get(0).battleEntity);
    }

    public Enemy() {
        super();
    }

    public Enemy(String name, boolean isPlayer, String battlerPath, int baseHP, int baseMP, int baseATK, int baseDEF, EnemyDrop[] drops, int goldDropped) {
        super(name, isPlayer, battlerPath, baseHP, baseMP, baseATK, baseDEF);
        this.drops = drops;
        this.goldDropped = goldDropped;
        this.isDead = false;
        this.timesKilled = 0;
    }

    public void getDrops() {
        Resources.money += this.goldDropped;
        for (EnemyDrop drop : drops) {
            if (Math.random() < drop.chance) {
                Inventory.addItem(drop.item.getName(), 1);
            }
        }
    }

    @Override
    public String toString() {
        return "Enemy{" +
                "drops=" + Arrays.toString(drops) +
                ", goldDropped=" + goldDropped +
                ", isDead=" + isDead +
                ", timesKilled=" + timesKilled +
                ", currHP=" + currHP +
                ", baseHP=" + baseHP +
                ", currMP=" + currMP +
                ", baseMP=" + baseMP +
                ", baseATK=" + baseATK +
                ", baseDEF=" + baseDEF +
                ", skills=" + skills +
                '}';
    }
}
