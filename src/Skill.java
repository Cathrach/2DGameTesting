/**
 * Created by serinahu on 5/5/17.
 */

import org.newdawn.slick.*;

public class Skill {
    private String name;
    private Image icon;
    // 0: immediate (like buffs)
    // otherwise: cast after delay turns (as shown in turnOrder list)
    private int delay;

    // animation

    // target (for battle) (use enum)
    private TargetType target;

    // skill effects

    public void use(BattleEntity caster, BattleEntity target) {
        // apply each skill effect: check target of skill with the parameters and add a skill effect on them
    }
}
