/**
 * Created by serinahu on 5/4/17.
 */


public class BattleEntity {
    private String name;
    private int currHP;
    private int baseHP;
    private int baseATK;
    private int baseDEF;

    // equips here

    // skills (?) here

    public BattleEntity(String name) {
        this.name = name;
        currHP = 20;
        baseHP = 20;
        baseATK = 5;
        baseDEF = 3;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCurrHP() {
        return currHP;
    }

    public void setCurrHP(int currHP) {
        this.currHP = currHP;
    }

    public int getBaseHP() {
        return baseHP;
    }

    public void setBaseHP(int baseHP) {
        this.baseHP = baseHP;
    }

    public int getBaseATK() {
        return baseATK;
    }

    public void setBaseATK(int baseATK) {
        this.baseATK = baseATK;
    }

    public int getBaseDEF() {
        return baseDEF;
    }

    public void setBaseDEF(int baseDEF) {
        this.baseDEF = baseDEF;
    }
}
