/**
 * Created by serinahu on 5/8/17.
 */
public class Equipment extends Item {

    public enum EquipType {
        WEAPON,
        SHIELD,
        ARMOR,
        ACCESSORY
    }

    private EquipType type;
    private int HP;
    private int ATK;
    private int DEF;
}
