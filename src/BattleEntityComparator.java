import java.util.Comparator;

/**
 * Created by serinahu on 5/23/17.
 */
public class BattleEntityComparator implements Comparator<Entity> {
    private SortOrder order; // either -1 or 1
    private Stat stat; // 0, 1, 2, or 3

    public BattleEntityComparator(SortOrder order, Stat stat) {
        this.order = order;
        this.stat = stat;
    }

    @Override
    public int compare(Entity o1, Entity o2) {
        switch (stat) {
            case HP:
                return order.value * (o1.battleEntity.currHP - o2.battleEntity.currHP);
            case MP:
                return order.value * (o1.battleEntity.getMP() - o2.battleEntity.getMP());
            case ATK:
                return order.value * (o1.battleEntity.getATK() - o2.battleEntity.getATK());
            case DEF:
                return order.value * (o1.battleEntity.getDEF() - o2.battleEntity.getDEF());
            default:
                return order.value * (o1.battleEntity.getHP() - o2.battleEntity.getHP());
        }
    }
}

enum SortOrder {
    ASC (1),
    DESC (-1);

    final int value;

    SortOrder(int value) {
        this.value = value;
    }
}

enum Stat {
    HP, MP, ATK, DEF;
}
