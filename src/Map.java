/**
 * Created by serinahu on 5/8/17.
 */
import org.newdawn.slick.*;
import org.newdawn.slick.tiled.*;
import static java.lang.Integer.parseInt;
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
        this("maps/sample.tmx");
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
    public int[] getEntry(float xPos, float yPos) {
        int tileID = map.getTileId((int) xPos / tileWidth, (int) yPos / tileHeight, map.getLayerIndex("Entrances"));
        String[] data = map.getTileProperty(tileID, "entryInfo", "").split("_");
        int[] new_data = {parseInt(data[0]), parseInt(data[1]), parseInt(data[2])};
        return new_data;
    }
    public boolean isEncounter(float xPos, float yPos) {
        if (xPos < 0 || yPos < 0 || xPos > pixelWidth || yPos > pixelHeight) {
            return false;
        }
        int tileID = map.getTileId((int) xPos / tileWidth, (int) yPos / tileHeight, map.getLayerIndex("Entrances"));
        float encounterChance = Float.parseFloat(map.getTileProperty(tileID, "encounterChance", "0"));
        return Math.random() < encounterChance;
    }
    public void encounter(float xPos, float yPos) {
        int tileID = map.getTileId((int) xPos / tileWidth, (int) yPos / tileHeight, map.getLayerIndex("Encounters"));
        String[] data = map.getTileProperty(tileID, "encounterInfo", "").split("_");
        Resources.currEnemies.clear();
        for (String index : data) {
            Resources.currEnemies.add(new Enemy(Resources.enemy_db[Integer.parseInt(index)]));
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
        String itemName = map.getTileProperty(tileID, "item", "gold");

        // set the container tileID to 23, which is the empty container.
        map.setTileId(x, y, map.getLayerIndex("Containers"), 23);

        // increment the Blocked tileID by 1 (closed chest -> open chest, etc)
        tileID = map.getTileId(x, y, map.getLayerIndex("Blocked"));
        map.setTileId(x, y, map.getLayerIndex("Blocked"), tileID+1);

        return itemName;
    }
}