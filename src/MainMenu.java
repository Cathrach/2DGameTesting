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
    private Image background;

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
        background = new Image("images/backgrounds/startScreen.png");
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        g.drawImage(background, 0,0);
        // draw shaded rectangle behind button text
        g.setColor(new Color(0, 0, 0, 100));
        g.fillRect(360, 160, 180, 180);
        g.setColor(Color.white);
        for (int i = 0; i < BUTTONS; i++) {
            if (selectedButton == i) {
                g.setColor(selectedColor);
                g.drawString(buttons[i], 400, i * 50 + 200);
                g.setColor(new Color(Color.white));
            } else {
                g.drawString(buttons[i], 400, i * 50 + 200);
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
                    // game.enterState(TestingGame.MAP);
                    Cutscene.changeDialogue(0);
                    game.enterState(TestingGame.CUTSCENE);
                    break;
                case EXIT:
                    container.exit();
                    break;
            }
        }
    }
}
