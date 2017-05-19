/**
 * Created by serinahu on 5/5/17.
 */

import org.newdawn.slick.*;

public class Skill {
    private String name;
    private Image icon;
    // 0: immediate (like buffs)
    // otherwise: cast after delay turns (as shown in turnOrder list)
    int delay;

    private int fixDamage;
    int MPCost;
    private float ratioDamage;
    private float ratioRecover;
    private float ignoreDefense; // 1 for no ignore, 0.75 for 25% ignore, etc.

    // animation

    // target (for battle) (use enum)
    private TargetType target;

    // skill effects
    SkillEffect[] skillEffects;
    TargetType[] targetTypes;

    public Skill(String name, Image icon, int delay, int MPCost, int fixDamage, float ratioDamage, float ratioRecover, float ignoreDefense, TargetType target, SkillEffect[] skillEffects, TargetType[] targetTypes) {
        this.name = name;
        this.icon = icon;
        this.delay = delay;
        this.MPCost = MPCost;
        this.fixDamage = fixDamage;
        this.ratioDamage = ratioDamage;
        this.ratioRecover = ratioRecover;
        this.ignoreDefense = ignoreDefense;
        this.target = target;
        this.skillEffects = skillEffects;
        this.targetTypes = targetTypes;
    }

    public void use(BattleEntity caster, BattleEntity target) {
        // TODO: Critical hits
        caster.currMP -= MPCost;
        int damage = (int) (fixDamage + caster.getATK() * ratioDamage - target.getDEF() * ignoreDefense);
        target.currHP -= damage;
        caster.currHP = Math.min(caster.getHP(), caster.currHP + damage * (int) ratioRecover);
        // apply each skill effect: check target of skill with the parameters and add a skill effect on them
        for (int i = 0; i < skillEffects.length; i++) {
            SkillEffect skillEffect = skillEffects[i];
            TargetType targetType = targetTypes[i];
            switch (targetType) {
                case SELF:
                    caster.addSkillEffect(skillEffect);
                    break;
                case SINGLE_ALLY:
                    target.addSkillEffect(skillEffect);
                    break;
                case SINGLE_ENEMY:
                    target.addSkillEffect(skillEffect);
                    break;
                case ALL_ALLIES:
                    for (Entity entity : Resources.party) {
                        if (!entity.battleEntity.equals(caster)) {
                            entity.battleEntity.addSkillEffect(skillEffect);
                        }
                    }
                    break;
                case ALL_ENEMIES:
                    for (Enemy enemy : Combat.currEnemies) {
                        enemy.addSkillEffect(skillEffect);
                    }
                    break;
                default:
                    target.addSkillEffect(skillEffect);
            }
        }
    }

    public Image getIcon() {
        return icon;
    }

    public String getName() {

        return name;
    }
}
