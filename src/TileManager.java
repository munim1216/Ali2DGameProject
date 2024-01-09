import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {
    private GamePanel gp;
    private Player player;
    private Tile[] tiles;
    private int mapTileNum[][];

    public TileManager(GamePanel gp, Player player) {
        this.gp = gp;
        this.player = player;

        tiles = new Tile[10];
        mapTileNum = new int[GamePanel.MAX_WORLD_COL][GamePanel.MAX_WORLD_ROW];

        try {
            BufferedImage image = ImageIO.read(getClass().getResourceAsStream("Tiles/grass_1.png"));
            tiles[0] = new Tile(image, false);
            image = ImageIO.read(getClass().getResourceAsStream("Tiles/flower_grass_1.png"));
            tiles[1] = new Tile(image, false);
            image = ImageIO.read(getClass().getResourceAsStream("Tiles/water_1.png"));
            tiles[2] = new Tile(image, false);
            image = ImageIO.read(getClass().getResourceAsStream("Tiles/gray_brick_1.png"));
            tiles[3] = new Tile(image, false);
            image = ImageIO.read(getClass().getResourceAsStream("Tiles/forest_1.png"));
            tiles[4] = new Tile(image, false);
            image = ImageIO.read(getClass().getResourceAsStream("Tiles/sand_1.png"));
            tiles[5] = new Tile(image, false);
        }   catch (IOException e) {
            e.printStackTrace();
        }

        loadMap();
    }

    public void draw(Graphics2D g2D) {
        int worldCol = 0;
        int worldRow = 0;

        while (worldCol < GamePanel.MAX_WORLD_COL && worldRow < GamePanel.MAX_WORLD_ROW) {
            int worldX = worldCol * GamePanel.TILE_SIZE;
            int worldY = worldRow * GamePanel.TILE_SIZE;
            int screenX = worldX - player.getworldX() + Player.PLAYER_SCREEN_X;
            int screenY = worldY - player.getworldY() + Player.PLAYER_SCREEN_Y;

            if ()
            g2D.drawImage(tiles[mapTileNum[worldCol][worldRow]].getImage(), screenX, screenY, GamePanel.TILE_SIZE, GamePanel.TILE_SIZE, null);
            worldCol++;

            if (worldCol == GamePanel.MAX_WORLD_COL) {
                worldCol = 0;
                worldRow++;
            }
        }
    }

    private void loadMap() {
        try {
            InputStream is = getClass().getResourceAsStream("Maps/starter_map.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while (col < GamePanel.MAX_SCREEN_COL && row < GamePanel.MAX_SCREEN_ROW) {
                String line = br.readLine();

                while (col < GamePanel.MAX_WORLD_COL) {
                    String[] numbers = line.split(" ");

                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[col][row] = num;
                    col++;
                }
                if (col == 50) {
                    col = 0;
                    row++;
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean notOutOfBounds(int worldX, int worldY) {
        return (worldX < )
    }
}
