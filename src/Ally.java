import java.util.ArrayList;

/**
 * Created by serinahu on 5/8/17.
 */
public class Ally extends BattleEntity {

    int fixEquipHP;
    float ratioEquipHP;
    int fixEquipATK;
    float ratioEquipATK;
    int fixEquipDEF;
    float ratioEquipDEF;

    public Ally() {
        super("player", true);
        ratioEquipHP = 1;
        ratioEquipATK = 1;
        ratioEquipDEF = 1;
    }

    // equips here
    ArrayList<Equipment> equips;

    public void equip(Equipment equip) {
        // check if equips already has something of the same type
        // if so, unequip that thing
        // add this equip to array list
    }

    public void unequip(int equip_index) {
        equips.remove(equip_index);
    }

    @Override
    public int getHP() {
        return (int) Math.floor((baseHP + fixEquipHP + fixSkillHP) * (ratioEquipHP * ratioSkillHP));
    }

    @Override
    public int getATK() {
        return (int) Math.floor((baseATK + fixEquipATK + fixSkillATK) * (ratioEquipATK * ratioSkillATK));
    }

    @Override
    public int getDEF() {
        return (int) Math.floor((baseDEF + fixEquipDEF + fixSkillDEF) * (ratioEquipDEF * ratioSkillDEF));
    }
}