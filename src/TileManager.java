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
        loadMap();

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
    }

    public void draw(Graphics2D g2D) {
        int col = 0;
        int row = 0;

        while (col < GamePanel.MAX_SCREEN_COL && row < GamePanel.MAX_SCREEN_ROW) {
            int screenX = (col * GamePanel.TILE_SIZE) - player.getworldX() + player.;
            int worldY = row * GamePanel.TILE_SIZE;

            g2D.drawImage(tiles[mapTileNum[col][row]].getImage(), screenX, , GamePanel.TILE_SIZE, GamePanel.TILE_SIZE, null);
            col++;

            if (col == GamePanel.MAX_SCREEN_COL) {
                col = 0;
                row++;
            }
        }
    }

    private void loadMap() {
        try {
            InputStream is = getClass().getResourceAsStream("Maps/starter_map.txt");
            BufferedReader br =  new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while (col < GamePanel.MAX_WORLD_COL && row < GamePanel.MAX_WORLD_ROW) {
                String line = br.readLine();
                while (col < GamePanel.MAX_WORLD_COL) {
                    String[] numbers = line.split(" ");

                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[col][row] = num;
                    col++;
                }
                if (col == GamePanel.MAX_WORLD_COL) {
                    col = 0;
                    row++;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
