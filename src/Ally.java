import java.util.ArrayList;

/**
 * Created by serinahu on 5/8/17.
 */
public class Ally extends BattleEntity {

    public Ally() {
        super("player", true);
    }

    // equips here
    private ArrayList<Equipment> equips;

    public void equip(Equipment equip) {
        // check if equips already has something of the same type
        // if so, unequip that thing
        // add this equip to array list
    }

    public void unequip(int equip_index) {
        equips.remove(equip_index);
    }
}