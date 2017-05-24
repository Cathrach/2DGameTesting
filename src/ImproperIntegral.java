import java.util.*;

/**
 * Created by serinahu on 5/23/17.
 */
public class ImproperIntegral extends Enemy {
    private boolean firstTurnParts, usedIntegrate;
    public ImproperIntegral() {
        super("Improper Integral",
                "sprites/enemies/04_dwarf.png",
                100, 40, 40, 15,
                new EnemyDrop[]{
                        new EnemyDrop(Resources.item_db[1], 0.5f)
                },
                30
        );
        this.addSkill(0);
        this.addSkill(3);
        this.addSkill(9);
        firstTurnParts = false;
        usedIntegrate = false;
    }

    @Override
    public BattleAction decideAction() {
        int index = (int) (Math.random() * Resources.party.size());
        ArrayList<Entity> temp = new ArrayList<>(Resources.party);
        temp.sort(new BattleEntityComparator(SortOrder.DESC, Stat.DEF));
        if (this.currMP >= 50 && !firstTurnParts) {
            firstTurnParts = true;
            return new BattleAction(this, skills.get(2), temp.get(0).battleEntity);
        }
        if (this.currMP >= 20 && this.currHP <= this.getHP() * 0.5) {
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

    public ImproperIntegral(Enemy anotherIntegral) {
        super(anotherIntegral);
        firstTurnParts = false;
        usedIntegrate = false;
    }
}
