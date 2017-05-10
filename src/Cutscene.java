import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

/**
 * Created by serinahu on 5/9/17.
 */
public class Cutscene extends BasicGameState {
    private int id;

    public Cutscene(int id) {
        this.id = id;
    }
    @Override
    public int getID() {
        return id;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {

    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {

    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {

    }
}
