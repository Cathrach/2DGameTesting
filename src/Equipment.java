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
    int fixMP;
    int fixATK;
    int fixDEF;
    float ratioHP;
    float ratioMP;
    float ratioATK;
    float ratioDEF;

    @Override
    public String toString() {
        return "Equipment{" +
                "type=" + type +
                ", fixHP=" + fixHP +
                ", fixMP=" + fixMP +
                ", fixATK=" + fixATK +
                ", fixDEF=" + fixDEF +
                ", ratioHP=" + ratioHP +
                ", ratioMP=" + ratioMP +
                ", ratioATK=" + ratioATK +
                ", ratioDEF=" + ratioDEF +
                '}';
    }

    public Equipment(String n, int v, int q, String d, EquipType type, int fixHP, int fixMP, int fixATK, int fixDEF, float ratioHP, float ratioMP, float ratioATK, float ratioDEF) {
        super(n, v, q, d);
        this.type = type;
        this.fixHP = fixHP;
        this.fixMP = fixMP;
        this.fixATK = fixATK;
        this.fixDEF = fixDEF;
        this.ratioHP = ratioHP;
        this.ratioMP = ratioMP;
        this.ratioATK = ratioATK;
        this.ratioDEF = ratioDEF;
        itemType = String.valueOf(type);
    }
}