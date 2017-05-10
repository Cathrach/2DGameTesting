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

    EquipType type;
    int fixHP;
    int fixATK;
    int fixDEF;
    float ratioHP;
    float ratioATK;
    float ratioDEF;

    public Equipment(EquipType type, int fixHP, int fixATK, int fixDEF, float ratioHP, float ratioATK, float ratioDEF) {
        this.type = type;
        this.fixHP = fixHP;
        this.fixATK = fixATK;
        this.fixDEF = fixDEF;
        this.ratioHP = ratioHP;
        this.ratioATK = ratioATK;
        this.ratioDEF = ratioDEF;
    }
}
