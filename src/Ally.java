import java.util.ArrayList;

/**
 * Created by serinahu on 5/8/17.
 */
public class Ally extends BattleEntity {

    int fixEquipHP;
    float ratioEquipHP;
    int fixEquipMP;
    float ratioEquipMP;
    int fixEquipATK;
    float ratioEquipATK;
    int fixEquipDEF;
    float ratioEquipDEF;

    public Ally(String name, String battlerPath) {
        super(name, true, battlerPath, 20, 10, 5, 3);
        fixEquipHP = 0;
        fixEquipMP = 0;
        fixEquipATK = 0;
        fixEquipDEF = 0;
        ratioEquipHP = 1;
        ratioEquipMP = 1;
        ratioEquipATK = 1;
        ratioEquipDEF = 1;
        equips = new ArrayList<>();
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
        equips.add(equip);
        Inventory.removeItem(equip.getName(), 1);
        // add this equip to array list
        fixEquipHP += equip.fixHP;
        fixEquipMP += equip.fixMP;
        fixEquipATK += equip.fixATK;
        fixEquipDEF += equip.fixDEF;
        ratioEquipHP *= equip.ratioHP;
        ratioEquipMP *= equip.ratioMP;
        ratioEquipATK *= equip.ratioATK;
        ratioEquipDEF *= equip.ratioDEF;
    }

    public void unequip(int equip_index) {
        Equipment currEquip = equips.get(equip_index);
        Inventory.addItem(currEquip.getName(), 1);
        fixEquipHP -= currEquip.fixHP;
        fixEquipMP -= currEquip.fixMP;
        fixEquipATK -= currEquip.fixATK;
        fixEquipDEF -= currEquip.fixDEF;
        ratioEquipHP /= currEquip.ratioHP;
        ratioEquipMP /= currEquip.ratioMP;
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

    public int getMP() {
        return (int) Math.floor((baseMP + fixEquipMP + fixSkillMP) * (ratioEquipMP * ratioSkillMP));
    }

    @Override
    public String toString() {
        return "Ally{" +
                "fixEquipHP=" + fixEquipHP +
                ", ratioEquipHP=" + ratioEquipHP +
                ", fixEquipATK=" + fixEquipATK +
                ", ratioEquipATK=" + ratioEquipATK +
                ", fixEquipDEF=" + fixEquipDEF +
                ", ratioEquipDEF=" + ratioEquipDEF +
                ", equips=" + equips +
                '}';
    }
}