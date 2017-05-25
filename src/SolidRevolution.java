/**
 * Created by serinahu on 5/23/17.
 */
public class SolidRevolution extends Enemy {
    public SolidRevolution() {
        super("Solid of Revolution",
                "images/sprites/enemies/04_dwarf.png",
                100, 40, 40, 15,
                new EnemyDrop[]{
                        new EnemyDrop(Resources.item_db[1], 0.5f)
                },
                30
        );
        this.addSkill(0);
        this.addSkill(3);
    }

    @Override
    public BattleAction decideAction() {
        double rng = Math.random();
        int index = (int) (Math.random() * Resources.party.size());
        if (rng < 1.0 / 3) {
            return new BattleAction(this, skills.get(1), Resources.party.get(index).battleEntity);
        } else {
            return new BattleAction(this, skills.get(0), Resources.party.get(index).battleEntity);
        }
    }

    public SolidRevolution(Enemy anotherSolid) {
        super(anotherSolid);
    }

}
