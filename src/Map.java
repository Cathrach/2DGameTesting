/**
 * Created by serinahu on 5/8/17.
 */
import org.newdawn.slick.*;
import org.newdawn.slick.tiled.*;

public class Map {
    private TiledMap map;
    // dimensions in tiles
    int height;
    int width;
    int tileHeight;
    int tileWidth;
    // dimensions in pixels
    int pixelHeight;
    int pixelWidth;
    // list of trigger-able things
    public Map() throws SlickException {
        this("maps/map0.tmx");
    }
    public Map(String filePath) throws SlickException {
        map = new TiledMap(filePath);
        height = map.getHeight();
        width = map.getWidth();
        tileHeight = map.getTileHeight();
        tileWidth = map.getTileWidth();
        pixelHeight = height * tileHeight;
        pixelWidth = width * tileWidth;
    }
    public void render(int xPos, int yPos) {
        map.render(xPos, yPos);
    }
    public boolean isBlocked(float xPos, float yPos) {
        // check if touching the bounds of the screen
        if (xPos < 0 || yPos < 0 || xPos > pixelWidth || yPos > pixelHeight) {
            return true;
        }
        int tileID = map.getTileId((int) xPos / tileWidth, (int) yPos / tileHeight, map.getLayerIndex("Blocked"));
        return map.getTileProperty(tileID, "Blocked", "false").equals("true");
    }
    public boolean isEntry(float xPos, float yPos) {
        if (xPos < 0 || yPos < 0 || xPos > pixelWidth || yPos > pixelHeight) {
            return false;
        }
        int tileID = map.getTileId((int) xPos / tileWidth, (int) yPos / tileHeight, map.getLayerIndex("Entrances"));
        return !map.getTileProperty(tileID, "entryInfo", "").equals("");
    }
    public String[] getEntry(float xPos, float yPos) {
        // TODO: add a trigger that needs to be true in order to unlock a map
        int tileID = map.getTileId((int) xPos / tileWidth, (int) yPos / tileHeight, map.getLayerIndex("Entrances"));
        String[] data = map.getTileProperty(tileID, "entryInfo", "").split("_");
        return data;
    }
    public boolean isEncounter(float xPos, float yPos) {
        double rng = Math.random();
        if (xPos < 0 || yPos < 0 || xPos > pixelWidth || yPos > pixelHeight) {
            return false;
        }
        int tileID = map.getTileId((int) xPos / tileWidth, (int) yPos / tileHeight, map.getLayerIndex("Encounters"));
        float encounterChance = Float.parseFloat(map.getTileProperty(tileID, "encounterChance", "0"));
        return rng < encounterChance;
    }
    public void encounter(float xPos, float yPos) {
        int tileID = map.getTileId((int) xPos / tileWidth, (int) yPos / tileHeight, map.getLayerIndex("Encounters"));
        String[] data = map.getTileProperty(tileID, "encounterInfo", "").split("_");
        Combat.currEnemies.clear();
        for (String index : data) {
            switch (Integer.parseInt(index)) {
                case 0:
                    Combat.currEnemies.add(Resources.enemy_db[0]);
                    break;
                case 1:
                    Combat.currEnemies.add(Resources.enemy_db[1]);
                    break;
                case 2:
                    Combat.currEnemies.add(Resources.enemy_db[2]);
                    break;
                case 3:
                    Combat.currEnemies.add(Resources.enemy_db[3]);
                    break;
                case 4:
                    Combat.currEnemies.add(Resources.enemy_db[4]);
                    break;
                case 5:
                    Combat.currEnemies.add(Resources.enemy_db[5]);
                case 6:
                    Combat.currEnemies.add(Resources.enemy_db[6]);
                default:
                    break;
            }
            //Combat.currEnemies.add(new Enemy(Resources.enemy_db[Integer.parseInt(index)]));
        }
    }
    public boolean isContainer(float xPos, float yPos){
        int tileID = map.getTileId((int) xPos / tileWidth, (int) yPos / tileHeight, map.getLayerIndex("Containers"));
        return map.getTileProperty(tileID, "isContainer", "false").equals("true");
    }
    public String getItem(float xPos, float yPos){
        int x = (int) xPos / tileWidth;
        int y = (int) yPos / tileHeight;

        // retrieve the tileID of the container.
        int tileID = map.getTileId(x, y, map.getLayerIndex("Containers"));
        String itemName = map.getTileProperty(tileID, "item", "#2 Pencil");

        // set the container tileID to 23, which is the empty container.
        map.setTileId(x, y, map.getLayerIndex("Containers"), 27);

        // increment the Blocked tileID by 1 (closed chest -> open chest, etc)
        tileID = map.getTileId(x, y, map.getLayerIndex("Blocked"));
        map.setTileId(x, y, map.getLayerIndex("Blocked"), tileID+1);

        return itemName;
    }
    public boolean isCutscene(float xPos, float yPos) {
        int tileID = map.getTileId((int) xPos / tileWidth, (int) yPos / tileHeight, map.getLayerIndex("Blocked"));
        return map.getTileProperty(tileID, "isCutscene", "false").equals("true");
    }
    public int getCutscene(float xPos, float yPos) {
        int tileID = map.getTileId((int) xPos / tileWidth, (int) yPos / tileHeight, map.getLayerIndex("Blocked"));
        return Integer.parseInt(map.getTileProperty(tileID, "scene", "none"));
    }
}