/**
 * Created by serinahu on 5/3/17.
 */

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

import java.io.IOException;

public class TestingGame extends StateBasedGame {

    public static final String name = "Testing";
    public static final int MAIN_MENU = 0;
    public static final int MAP = 1;
    public static final int CUTSCENE = 2;
    public static final int PAUSE_MENU = 3;
    public static final int COMBAT = 4;
    public static final int SHOP = 5;
    public static final int DEATH_SCREEN = 6;

    public static final int WIDTH = 640;
    public static final int HEIGHT = 480;
    public static final int FPS = 60;

    public TestingGame(String name) throws SlickException {
        super(name);
        this.addState(new MainMenu(MAIN_MENU));
        this.addState(new MapState(MAP));
        this.addState(new Cutscene(CUTSCENE));
        this.addState(new PauseMenu(PAUSE_MENU));
        this.addState(new Combat(COMBAT));
        this.addState(new Shop(SHOP));
        this.addState(new Cutscene(DEATH_SCREEN));
        this.enterState(MAIN_MENU);
    }

    @Override
    public void initStatesList(GameContainer container) throws SlickException {
        try {
            Resources.init();
            getState(MAIN_MENU).init(container, this);
            getState(MAP).init(container, this);
            getState(CUTSCENE).init(container, this);
            getState(PAUSE_MENU).init(container, this);
            getState(COMBAT).init(container, this);
            getState(SHOP).init(container, this);
            Inventory.initListener(container);
            Combat.initListener(container);
            Shop.initListener(container);
        }
        catch (IOException e) {
            System.out.println("bad file path");
        }
    }

    public static void main(String[] args)
    {
        AppGameContainer app;
        try
        {
            app = new AppGameContainer(new TestingGame(name));
            app.setDisplayMode(WIDTH, HEIGHT, false);
            app.setTargetFrameRate(FPS);
            app.start();
        }
        catch (SlickException e)
        {
            e.printStackTrace();
        }
    }
}
