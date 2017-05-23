import java.util.Arrays;

/**
 * Created by serinahu on 5/22/17.
 */
public class Derivative extends Enemy {
    private boolean usedDerive;
    public Derivative() {
        super("Derivative",
                "sprites/enemies/dwarf.png",
                60, 20, 15, 10,
                new EnemyDrop[]{
                        new EnemyDrop(Resources.item_db[1], 0.5f)
                },
                50
        );
        this.addSkill(0);
        this.addSkill(2);
        usedDerive = false;
    }

    @Override
    public BattleAction decideAction() {
        Entity[] temp = Arrays.copyOf(Resources.party, Resources.party.length);
        Arrays.sort(temp, (a, b) -> b.battleEntity.currHP - a.battleEntity.currHP);
        if (this.currMP >= 10 && this.currHP <= this.baseHP * 0.5) {
            if (!usedDerive) {
                return new BattleAction(this, skills.get(1), temp[0].battleEntity);
            } else {
                // 50% chance to derive, 50% chance to normal attack
                if (Math.random() < 0.5) {
                    return new BattleAction(this, skills.get(1), temp[0].battleEntity);
                } else {
                    return new BattleAction(this, skills.get(0), temp[0].battleEntity);
                }
            }
        }
        return new BattleAction(this, skills.get(0), temp[0].battleEntity);
    }
}
