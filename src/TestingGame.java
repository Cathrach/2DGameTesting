/**
 * Created by serinahu on 5/3/17.
 */

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public class TestingGame extends StateBasedGame {

    public static final String name = "Testing";
    public static final int MAIN_MENU = 0;
    public static final int WORLD_MAP = 1;
    public static final int TOWN_MAP = 2;
    public static final int PAUSE_MENU = 3;
    public static final int COMBAT = 4;

    public static final int WIDTH = 640;
    public static final int HEIGHT = 480;
    public static final int FPS = 60;

    static int money;
    static Entity[] party;
    // change to enemy class later
    static BattleEntity[] enemies;

    public TestingGame(String name) throws SlickException {
        super(name);
        this.addState(new MainMenu(MAIN_MENU));
        this.addState(new WorldMap(WORLD_MAP));
        this.addState(new PauseMenu(PAUSE_MENU));
        this.addState(new Combat(COMBAT));
        this.enterState(MAIN_MENU);
    }

    @Override
    public void initStatesList(GameContainer container) throws SlickException {
        getState(MAIN_MENU).init(container, this);
        getState(WORLD_MAP).init(container, this);
        getState(PAUSE_MENU).init(container, this);
        getState(COMBAT).init(container, this);
    }

    public static void main(String[] args)
    {
        AppGameContainer app;
        try
        {
            app = new AppGameContainer(new TestingGame(name));
            app.setDisplayMode(WIDTH, HEIGHT, false);
            app.setTargetFrameRate(FPS);
            app.setShowFPS(true);
            app.start();
        }
        catch (SlickException e)
        {
            e.printStackTrace();
        }
    }
}
