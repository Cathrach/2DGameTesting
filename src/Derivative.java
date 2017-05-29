import java.util.*;

/**
 * Created by serinahu on 5/22/17.
 */
public class Derivative extends Enemy {
    private boolean usedDerive;

    public Derivative() {
        super("Derivative", false,
                "images/sprites/enemies/Derivative.png",
                60, 10, 15, 10,
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
        int index = (int) (Math.random() * Resources.party.size());
        ArrayList<Entity> temp = new ArrayList<>(Resources.party);
        temp.sort(new BattleEntityComparator(SortOrder.DESC, Stat.HP));
        if (this.currMP >= 5 && this.currHP <= this.getHP() * 0.5) {
            if (!usedDerive) {
                usedDerive = true;
                return new BattleAction(this, skills.get(1), temp.get(0).battleEntity);
            } else {
                // 50% chance to derive, 50% chance to normal attack
                double rng = Math.random();
                if (rng < 0.5) {
                    return new BattleAction(this, skills.get(1), temp.get(0).battleEntity);
                } else {
                    // attack random person
                    return new BattleAction(this, skills.get(0), temp.get(index).battleEntity);
                }
            }
        }
        return new BattleAction(this, skills.get(0), temp.get(index).battleEntity);
    }

    public Derivative(Enemy anotherDerivative) {
        super(anotherDerivative);
        usedDerive = false;
    }

    @Override
    public String toString() {
        return "Derivative{" +
                "usedDerive=" + usedDerive +
                '}';
    }
}
