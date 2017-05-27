/**
 * Created by serinahu on 5/8/17.
 */
import org.newdawn.slick.*;

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

    public Equipment(String n, int v, int q, String d, EquipType type, int fixHP, int fixMP, int fixATK, int fixDEF, float ratioHP, float ratioMP, float ratioATK, float ratioDEF) throws SlickException {
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
        setInfo();
    }
    public String setInfo() {
        info = String.valueOf(type) + " | ";
        if (fixHP != 0) { info += "+" + fixHP + " base HP "; }
        if (fixMP != 0) { info += "+" + fixMP + " base MP "; }
        if (fixATK != 0) { info += "+" + fixATK + " ATK "; }
        if (fixDEF != 0) { info += "+" + fixDEF + " DEF "; }
        if (ratioHP != 1.0) { info += "x" + ratioHP + " HP "; }
        if (ratioMP != 1.0) { info += "x" + ratioMP + " MP "; }
        if (ratioATK != 1.0) { info += "x" + ratioATK + " ATK "; }
        if (ratioDEF != 1.0) { info += "+" + ratioDEF + " DEF "; }
        return info;
    }
}