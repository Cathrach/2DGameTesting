/**
 * Created by serinahu on 5/13/17.
 */
public class EnemyDrop {
    Item item;
    float chance;
    public EnemyDrop(Item item, float chance) {
        this.item = new Consumable("health potion", 0, 1);  //
        this.chance = chance;
    }
}