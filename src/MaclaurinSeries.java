/**
 * Created by serinahu on 5/25/17.
 */
public class MaclaurinSeries extends Enemy {
    public MaclaurinSeries() {
        super("Maclaurin Series", false,
                "images/sprites/enemies/MacLaurinSeries.png",
                140, 30, 50, 10,
                new EnemyDrop[]{
                        new EnemyDrop(Resources.item_db[1], 0.5f)
                },
                30
        );
        this.addSkill(0);
        this.addSkill(2);
    }

    @Override
    public BattleAction decideAction() {
        double rng = Math.random();
        int index = (int) (Math.random() * Resources.party.size());
        if (rng < 1.0 / 3 && this.currMP >= 5) {
            return new BattleAction(this, skills.get(1), Resources.party.get(index).battleEntity);
        } else {
            return new BattleAction(this, skills.get(0), Resources.party.get(index).battleEntity);
        }
    }
}
