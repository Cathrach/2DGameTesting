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
        fixEquipHP = 0;
        fixEquipATK = 0;
        fixEquipDEF = 0;
        ratioEquipHP = 1;
        ratioEquipATK = 1;
        ratioEquipDEF = 1;
    }

    public Ally(String name, boolean isPlayer) {
        this.name = name;
        this.isPlayer = isPlayer;
    }

    // equips here
    ArrayList<Equipment> equips;

    public void equip(Equipment equip) {
        // check if equips already has something of the same type
        for (int i = 0; i < equips.size(); i++) {
            Equipment currEquip = equips.get(i);
            // if so, unequip that thing
            if (currEquip.type == equip.type) {
                unequip(i);
            }
        }
        // add this equip to array list
        fixEquipHP += equip.fixHP;
        fixEquipATK += equip.fixATK;
        fixEquipDEF += equip.fixDEF;
        ratioEquipHP *= equip.ratioHP;
        ratioEquipATK *= equip.ratioATK;
        ratioEquipDEF *= equip.ratioDEF;
    }

    public void unequip(int equip_index) {
        Equipment currEquip = equips.get(equip_index);
        fixEquipHP -= currEquip.fixHP;
        fixEquipATK -= currEquip.fixATK;
        fixEquipDEF -= currEquip.fixDEF;
        ratioEquipHP /= currEquip.ratioHP;
        ratioEquipATK /= currEquip.ratioATK;
        ratioEquipDEF /= currEquip.ratioDEF;
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