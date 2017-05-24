/**
 * Created by serinahu on 5/3/17.
 */
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
public class MapState extends BasicGameState {
    private int id;
    static int currentMapID;
    static Map currentMap;
    private Entity leader;
    private static int sinceLastEncounter;
    private final int timeToWait = 4000;
    public MapState(int id) {
        currentMapID = 0;
        this.id = id;
    }
    public int getID() {
        return id;
    }
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        currentMap = Resources.map_db[currentMapID];
        leader = Resources.party.get(0);
        sinceLastEncounter = 0;
    }
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        currentMap.render(0, 0);
        leader.render(container, game, g);
    }
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        Input input = container.getInput();
        if (input.isKeyPressed(Input.KEY_ESCAPE)) {
            PauseMenu.inMenu = PauseMenu.NONE;
            game.enterState(TestingGame.PAUSE_MENU);
        }
        leader.update(container, game, delta, this);
        // if the tile is an entry, change the current map ID and current map
        if (currentMap.isEntry(leader.xPos, leader.yPos)) {
            //int[] entryData = currentMap.getEntry(leader.xPos, leader.yPos);
            //changeMap(entryData[0]);
            // place the player in the new position
            //leader.xPos = currentMap.tileWidth * entryData[1];
            //leader.yPos = currentMap.tileHeight * entryData[2];
            String[] data = currentMap.getEntry(leader.xPos, leader.yPos);
            changeMap(Integer.parseInt(data[1]));
            // to reduce the # of individual Entrance tiles needed:
            if (data[0].equals("map")) {
                // the leader will only be changing which side of the screen they...
                // ...are on, so we only change the xPos or only change the yPos.
                // data[2] can be 0, 16, or -16 (16 is slightly less than the screen tileWidth)
                // data[3] can be 0, 11, or -11 (11 is slightly less than the screen tileHeight)
                leader.xPos += currentMap.tileWidth * Integer.parseInt(data[2]);
                leader.yPos += currentMap.tileHeight * Integer.parseInt(data[3]);
            } else if (data[0].equals("house")) {
                leader.xPos = currentMap.tileWidth * Integer.parseInt(data[2]);
                leader.yPos = currentMap.tileHeight * Integer.parseInt(data[3]);
            }
        }
        // if trigger (NPC, chest), do something

        // if there's a random encounter, change state to combat
        if (sinceLastEncounter >= timeToWait) {
            if (currentMap.isEncounter(leader.xPos, leader.yPos)) {
                sinceLastEncounter = 0;
                currentMap.encounter(leader.xPos, leader.yPos);
                game.enterState(TestingGame.COMBAT);
                Combat.enter(game);
            }
        } else {
            sinceLastEncounter += delta;
        }
    }
    public static void changeMap(int newMapID) {
        currentMapID = newMapID;
        currentMap = Resources.map_db[currentMapID];
    }
    public boolean isBlocked(float xPos, float yPos) {
        return currentMap.isBlocked(xPos, yPos);
    }
    public boolean isContainer(float xPos, float yPos) { return currentMap.isContainer(xPos, yPos); }
    public String getItem(float xPos, float yPos) { return currentMap.getItem(xPos, yPos); }
    public int getHeight() {
        return currentMap.pixelHeight;
    }
    public int getWidth() {
        return currentMap.pixelWidth;
    }
}