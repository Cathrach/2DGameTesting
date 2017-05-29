/**
 * Created by serinahu on 5/22/17.
 */
public class Limit extends Enemy {
    public Limit() {
        super("Limit", false,
                "images/sprites/enemies/Limit.png",
                40, 0, 5, 3,
                new EnemyDrop[]{
                    new EnemyDrop(Resources.item_db[1], 0.5f)
                },
                10
        );
        this.addSkill(0);
    }

    public Limit(Enemy anotherLimit) {
        super(anotherLimit);
    }

    @Override
    public BattleAction decideAction() {
        int index = (int) (Math.random() * Resources.party.size());
        return new BattleAction(this, skills.get(0), Resources.party.get(index).battleEntity);
    }
}
