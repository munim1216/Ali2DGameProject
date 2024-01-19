package main;

import entities.Player;
import interactables.Chest_Interactable;
import interactables.Door_Interactable;
import interactables.Key_Interactable;
import interactables.WingedBoot_Interactable;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class TileManager {
    private GamePanel gp;
    private Player player;
    private Tile[] tiles;
    private int mapTileNum[][];

    public TileManager(GamePanel gp, Player player) {
        this.gp = gp;
        this.player = player;

        tiles = new Tile[27];
        mapTileNum = new int[GamePanel.MAX_WORLD_COL][GamePanel.MAX_WORLD_ROW];

        try {
            BufferedImage image = ImageIO.read(new File("resources/sprites/Tiles/cave_1.png"));
            tiles[10] = new Tile(Utility.scale(image), false);
            image = ImageIO.read(new File("resources/sprites/Tiles/dark_gray_brick_1.png"));
            tiles[11]= new Tile(Utility.scale(image), false);
            image = ImageIO.read(new File("resources/sprites/Tiles/grass_1.png"));
            tiles[12] = new Tile(Utility.scale(image), false);
            image = ImageIO.read(new File("resources/sprites/Tiles/gray_brick_1.png"));
            tiles[13] = new Tile(Utility.scale(image), false);
            image = ImageIO.read(new File("resources/sprites/Tiles/stone_path_1.png"));
            tiles[14] = new Tile(Utility.scale(image), false);
            image = ImageIO.read(new File("resources/sprites/Tiles/tree_1.png"));
            tiles[15] = new Tile(Utility.scale(image), true);
            image = ImageIO.read(new File("resources/sprites/Tiles/wall_1.png"));
            tiles[16] = new Tile(Utility.scale(image), true);
            image = ImageIO.read(new File("resources/sprites/Tiles/wall_2.png"));
            tiles[17]= new Tile(Utility.scale(image), true);
            image = ImageIO.read(new File("resources/sprites/Tiles/wall_3.png"));
            tiles[18] = new Tile(Utility.scale(image), true);
            image = ImageIO.read(new File("resources/sprites/Tiles/wall_4.png"));
            tiles[19] = new Tile(Utility.scale(image), true);
            image = ImageIO.read(new File("resources/sprites/Tiles/wall_turn_bottom_left.png"));
            tiles[20] = new Tile(Utility.scale(image), true);
            image = ImageIO.read(new File("resources/sprites/Tiles/wall_turn_bottom_right.png"));
            tiles[21] = new Tile(Utility.scale(image), true);
            image = ImageIO.read(new File("resources/sprites/Tiles/wall_turn_top_left.png"));
            tiles[22]= new Tile(Utility.scale(image), true);
            image = ImageIO.read(new File("resources/sprites/Tiles/wall_turn_top_right.png"));
            tiles[23] = new Tile(Utility.scale(image), true);
            image = ImageIO.read(new File("resources/sprites/Tiles/water_1.png"));
            tiles[24] = new Tile(Utility.scale(image), true);
            image = ImageIO.read(new File("resources/sprites/Tiles/water_2.png"));
            tiles[25] = new Tile(Utility.scale(image), true);
            image = ImageIO.read(new File("resources/sprites/Tiles/sand_1.png"));
            tiles[26] = new Tile(Utility.scale(image), false);
        }   catch (IOException e) {
            e.printStackTrace();
        }

        loadMap();
        new Key_Interactable(5 * GamePanel.TILE_SIZE,3 * GamePanel.TILE_SIZE); // test code to ensure the key is being drawn correctly
        new Key_Interactable(7 * GamePanel.TILE_SIZE,4 * GamePanel.TILE_SIZE); // test code to ensure the key is being drawn correctly
        new Key_Interactable(25 * GamePanel.TILE_SIZE,5 * GamePanel.TILE_SIZE); // test code to ensure the key is being drawn correctly
        new Key_Interactable(20 * GamePanel.TILE_SIZE,16 * GamePanel.TILE_SIZE); // test code to ensure the key is being drawn correctly

        new Key_Interactable(23 * GamePanel.TILE_SIZE,10 * GamePanel.TILE_SIZE); // test code to ensure the key is being drawn correctly
        new Key_Interactable(23 * GamePanel.TILE_SIZE,12 * GamePanel.TILE_SIZE); // test code to ensure the key is being drawn correctly
        new Key_Interactable(23 * GamePanel.TILE_SIZE,14 * GamePanel.TILE_SIZE); // test code to ensure the key is being drawn correctly
        new Key_Interactable(23 * GamePanel.TILE_SIZE,16 * GamePanel.TILE_SIZE); // test code to ensure the key is being drawn correctly

        new WingedBoot_Interactable(26 * GamePanel.TILE_SIZE, 10 * GamePanel.TILE_SIZE); // test code to ensure boot can be drawn & picked up
        new WingedBoot_Interactable(26 * GamePanel.TILE_SIZE, 12 * GamePanel.TILE_SIZE); // test code to ensure boot can be drawn & picked up
        new WingedBoot_Interactable(26 * GamePanel.TILE_SIZE, 14 * GamePanel.TILE_SIZE); // test code to ensure boot can be drawn & picked up
        new WingedBoot_Interactable(26 * GamePanel.TILE_SIZE, 16 * GamePanel.TILE_SIZE); // test code to ensure boot can be drawn & picked up

        new Door_Interactable(15 * GamePanel.TILE_SIZE, 4 * GamePanel.TILE_SIZE);

        new Chest_Interactable(30 * GamePanel.TILE_SIZE,2 * GamePanel.TILE_SIZE); // test code to ensure the key is being drawn correctly
    }

    public int[][] getMapTileNum() {
        return mapTileNum;
    }

    public Tile[] getTiles() {
        return tiles;
    }

    public void draw(Graphics2D g2D) {
        int worldCol = 0;
        int worldRow = 0;

        while (worldCol < GamePanel.MAX_WORLD_COL && worldRow < GamePanel.MAX_WORLD_ROW) {
            BufferedImage image = tiles[mapTileNum[worldCol][worldRow]].getImage();
            int worldX = worldCol * GamePanel.TILE_SIZE;
            int worldY = worldRow * GamePanel.TILE_SIZE;
            int screenX = worldX - player.getWorldX() + Player.PLAYER_SCREEN_X;
            int screenY = worldY - player.getWorldY() + Player.PLAYER_SCREEN_Y;

            if (notOutOfBounds(worldX, worldY)) {
                g2D.drawImage(image, screenX, screenY,null); // midst of editing
            }
            worldCol++;

            if (worldCol == GamePanel.MAX_WORLD_COL) {
                worldCol = 0;
                worldRow++;
            }
        }
    }

    private void loadMap() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File("resources/txt_files/maps/map2.txt")));

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
        return (worldX + GamePanel.TILE_SIZE > player.getWorldX() - Player.PLAYER_SCREEN_X ) && (worldX - GamePanel.TILE_SIZE  < player.getWorldX() + Player.PLAYER_SCREEN_X) &&
                (worldY + GamePanel.TILE_SIZE  > player.getWorldY() - Player.PLAYER_SCREEN_Y) && (worldY - GamePanel.TILE_SIZE  < player.getWorldY() + Player.PLAYER_SCREEN_Y);
    }
}
