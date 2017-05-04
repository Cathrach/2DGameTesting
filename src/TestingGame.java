/**
 * Created by serinahu on 5/3/17.
 */

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public class TestingGame extends StateBasedGame {

    public static final String name = "Testing";
    public static final int MAIN_MENU = 0;
    public static final int WORLD_MAP = 1;

    public static final int WIDTH = 640;
    public static final int HEIGHT = 480;
    public static final int FPS = 60;

    public TestingGame(String name) {
        super(name);
        this.addState(new MainMenu(MAIN_MENU));
        this.addState(new WorldMap(WORLD_MAP));
        this.enterState(MAIN_MENU);
    }

    @Override
    public void initStatesList(GameContainer container) throws SlickException {
        getState(MAIN_MENU).init(container, this);
        getState(WORLD_MAP).init(container, this);
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
