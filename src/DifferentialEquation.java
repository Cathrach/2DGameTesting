import java.util.ArrayList;

/**
 * Created by serinahu on 5/25/17.
 */
public class DifferentialEquation extends Enemy {
    private boolean usedBash;
    private boolean usedTriggy;

    public DifferentialEquation() {
        super("Differential Equation", false,
                "images/sprites/enemies/DifferentialEquation.png",
                120, 60, 15, 10,
                new EnemyDrop[]{
                        new EnemyDrop(Resources.item_db[1], 0.5f)
                },
                50
        );
        this.addSkill(0);
        this.addSkill(2);
        this.addSkill(3);
        this.addSkill(4);
        this.addSkill(6);
        usedBash = false;
        usedTriggy = false;
    }

    @Override
    public BattleAction decideAction() {
        double rng = Math.random();
        int index = (int) (Math.random() * Resources.party.size());
        ArrayList<Entity> temp = new ArrayList<>(Resources.party);
        if (!usedBash && this.currMP >= 25) {
            temp.sort(new BattleEntityComparator(SortOrder.ASC, Stat.HP));
            usedBash = true;
            return new BattleAction(this, this.skills.get(3), temp.get(0).battleEntity);
        }
        if (!usedTriggy && this.currHP <= 0.5 * this.getHP() && this.currMP >= 10) {
            temp.sort(new BattleEntityComparator(SortOrder.DESC, Stat.DEF));
            usedTriggy = true;
            return new BattleAction(this, this.skills.get(4), temp.get(0).battleEntity);
        }
        if (rng < 0.25 && this.currMP >= 5) {
            return new BattleAction(this, this.skills.get(1), temp.get(index).battleEntity);
        } else if (rng < 0.5 && this.currMP >= 10) {
            return new BattleAction(this, this.skills.get(2), temp.get(index).battleEntity);
        } else {
            return new BattleAction(this, this.skills.get(0), temp.get(index).battleEntity);
        }
    }
}
