/**
 * Created by serinahu on 5/3/17.
 */

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public class MainMenu extends BasicGameState {

    private int id;
    private static final int BUTTONS = 2;
    private static final int PLAY = 0;
    private static final int EXIT = 1;
    private String[] buttons = new String[BUTTONS];
    private int selectedButton;
    private Color selectedColor = new Color(255, 0, 0);

    public MainMenu(int id) {
        this.id = id;
    }

    @Override
    public int getID() {
        return id;
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        buttons[0] = "Play";
        buttons[1] = "Exit";
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        for (int i = 0; i < BUTTONS; i++) {
            if (selectedButton == i) {
                g.setColor(selectedColor);
                g.drawString(buttons[i], 200, i * 50 + 100);
                g.setColor(new Color(Color.white));
            } else {
                g.drawString(buttons[i], 200, i * 50 + 100);
            }
        }
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {

        Input input = container.getInput();
        if (input.isKeyPressed(Input.KEY_DOWN)) {
            if (selectedButton != BUTTONS - 1) {
                selectedButton++;
            }
        }
        if (input.isKeyPressed(Input.KEY_UP)) {
            if (selectedButton != 0) {
                selectedButton--;
            }
        }
        if (input.isKeyPressed(Input.KEY_ENTER)) {
            switch (selectedButton) {
                case PLAY:
                    game.enterState(TestingGame.MAP);
                    break;
                case EXIT:
                    container.exit();
                    break;
            }
        }
    }
}
