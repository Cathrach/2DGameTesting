import java.util.*;

/**
 * Created by serinahu on 5/23/17.
 */
public class IndefiniteIntegral extends Enemy {
    private boolean firstTurnC, usedIntegrate;
    public IndefiniteIntegral() {
        super("Indefinite Integral", false,
                "images/sprites/enemies/IndefiniteIntegral.png",
                90, 35, 35, 15,
                new EnemyDrop[]{
                        new EnemyDrop(Resources.item_db[1], 0.5f)
                },
                30
        );
        this.addSkill(0);
        this.addSkill(3);
        this.addSkill(5);
        firstTurnC = false;
        usedIntegrate = false;
    }

    @Override
    public BattleAction decideAction() {
        int index = (int) (Math.random() * Resources.party.size());
        ArrayList<Entity> temp = new ArrayList<>(Resources.party);
        if (this.currMP >= 15 && !firstTurnC) {
            firstTurnC = true;
            return new BattleAction(this, skills.get(2), this);
        }
        if (this.currMP >= 10 && this.currHP <= this.getHP() * 0.5) {
            // hit enemy with lowest def
            temp.sort(new BattleEntityComparator(SortOrder.ASC, Stat.DEF));
            if (!usedIntegrate) {
                usedIntegrate = true;
                return new BattleAction(this, skills.get(1), temp.get(0).battleEntity);
            } else {
                double rng = Math.random();
                if (rng < 0.5) {
                    return new BattleAction(this, skills.get(1), temp.get(0).battleEntity);
                } else {
                    return new BattleAction(this, skills.get(0), temp.get(index).battleEntity);
                }
            }
        }
        return new BattleAction(this, skills.get(0), temp.get(index).battleEntity);
    }

    public IndefiniteIntegral(Enemy anotherEnemy) {
        this();
    }
}
