/**
 * Created by serinahu on 5/6/17.
 */
public class SkillEffect {
    private int turns;
    // immediate: buffs
    // non-immediate: heals, poison, etc.
    private boolean immediate;

    public void addTo(BattleEntity target) {
        // inflict buffs
    }

    public void elapseTurn(BattleEntity target) {
        // poisons/heals that trigger every turn
        // remove a turn
        // if turns = 0, remove buffs
    }
}
