package tile;

import entity.Brick;
import entity.UnlivingEntity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;

/**
 * Gestionnaire des tiles du jeu
 */
public class TileManager {
    GamePanel m_gp;            //panel du jeu principal
    Tile[] m_tile;            //tableau de toutes les tiles possibles dans le jeu
    int m_maxTiles = 25;    //nombre maximum de tiles chargeable dans le jeu
    int m_mapTileNum[][];    //répartition des tiles dans la carte du jeu
    HashSet<UnlivingEntity> unlivingEntities;

    /**
     * Constructeur
     *
     * @param gp
     */
    public TileManager(GamePanel gp) {
        this.m_gp = gp;
        m_tile = new Tile[m_maxTiles];
        m_mapTileNum = new int[gp.MAX_SCREEN_COL][gp.MAX_SCREEN_ROW];
        unlivingEntities = new HashSet<>();

        this.getTileImage();
        this.loadMap("/maps/map1_part1.txt");
    }

    public HashSet<UnlivingEntity> getUnlivingEntities() {
        return unlivingEntities;
    }

    /**
     * Chargement de toutes les tuiles du jeu
     */
    public void getTileImage() {
        try {
            m_tile[0] = new Tile();
            m_tile[0].m_image = ImageIO.read(getClass().getResource("/tiles/FOND1.png"));

            m_tile[13] = new Tile();
            m_tile[13].m_image = ImageIO.read(getClass().getResource("/tiles/FOND2.png"));

            m_tile[18] = new Tile();
            m_tile[18].m_image = ImageIO.read(getClass().getResource("/tiles/FOND3.png"));

            m_tile[19] = new Tile();
            m_tile[19].m_image = ImageIO.read(getClass().getResource("/tiles/FOND4.png"));

            m_tile[1] = new Tile();
            m_tile[1].m_image = ImageIO.read(getClass().getResource("/tiles/BRICK.png"));

            m_tile[2] = new Tile();
            m_tile[2].m_image = ImageIO.read(getClass().getResource("/tiles/T.png"));

            m_tile[3] = new Tile();
            m_tile[3].m_image = ImageIO.read(getClass().getResource("/tiles/TR.png"));

            m_tile[4] = new Tile();
            m_tile[4].m_image = ImageIO.read(getClass().getResource("/tiles/TL.png"));

            m_tile[5] = new Tile();
            m_tile[5].m_image = ImageIO.read(getClass().getResource("/tiles/L.png"));

            m_tile[6] = new Tile();
            m_tile[6].m_image = ImageIO.read(getClass().getResource("/tiles/R.png"));

            m_tile[7] = new Tile();
            m_tile[7].m_image = ImageIO.read(getClass().getResource("/tiles/B.png"));

            m_tile[8] = new Tile();
            m_tile[8].m_image = ImageIO.read(getClass().getResource("/tiles/BR.png"));

            m_tile[9] = new Tile();
            m_tile[9].m_image = ImageIO.read(getClass().getResource("/tiles/BL.png"));

            m_tile[10] = new Tile();
            m_tile[10].m_image = ImageIO.read(getClass().getResource("/tiles/FOND2PIC.png"));

            m_tile[14] = new Tile();
            m_tile[14].m_image = ImageIO.read(getClass().getResource("/tiles/FOND1PIC.png"));

            m_tile[17] = new Tile();
            m_tile[17].m_image = ImageIO.read(getClass().getResource("/tiles/FOND3PIC.png"));

            m_tile[11] = new Tile();
            m_tile[11].m_image = ImageIO.read(getClass().getResource("/tiles/FOND4PIC.png"));

            m_tile[20] = new Tile();
            m_tile[20].m_image = ImageIO.read(getClass().getResource("/tiles/FOND1PIC2.png"));

            m_tile[21] = new Tile();
            m_tile[21].m_image = ImageIO.read(getClass().getResource("/tiles/FOND2PIC2.png"));

            m_tile[22] = new Tile();
            m_tile[22].m_image = ImageIO.read(getClass().getResource("/tiles/FOND3PIC2.png"));

            m_tile[23] = new Tile();
            m_tile[23].m_image = ImageIO.read(getClass().getResource("/tiles/FOND4PIC2.png"));

            m_tile[12] = new Tile();
            m_tile[12].m_image = ImageIO.read(getClass().getResource("/tiles/PLATEFORMEMILIEU.png"));

            m_tile[15] = new Tile();
            m_tile[15].m_image = ImageIO.read(getClass().getResource("/tiles/PLATEFORMELEFT.png"));

            m_tile[16] = new Tile();
            m_tile[16].m_image = ImageIO.read(getClass().getResource("/tiles/PLATEFORMERIGHT.png"));


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Lecture du fichier txt contenant la map et chargement des tuiles correspondantes.
     */
    public void loadMap(String filePath) {
        //charger le fichier txt de la map
        try {

            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            // Parcourir le fichier txt pour récupérer les valeurs
            while (col < m_gp.MAX_SCREEN_COL && row < m_gp.MAX_SCREEN_ROW) {
                String line = br.readLine();
                while (col < m_gp.MAX_SCREEN_COL) {
                    String[] numbers = line.split("\t");
                    int num = Integer.parseInt(numbers[col]);
                    m_mapTileNum[col][row] = num;

                    UnlivingEntity entity = switch (num) {
                        case 0, 13, 18, 19 -> new UnlivingEntity(0, 0, 0, 0, false);
                        default ->
                                new Brick(m_gp.TILE_SIZE * col, m_gp.TILE_SIZE * row, m_gp.TILE_SIZE, m_gp.TILE_SIZE);
                    };


                    unlivingEntities.add(entity);

                    col++;
                }
                if (col == m_gp.MAX_SCREEN_COL) {
                    col = 0;
                    row++;
                }
            }

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Affichage de la carte avec les différentes tuiles
     *
     * @param g2
     */
    public void draw(Graphics2D g2) {
        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;

        while (col < m_gp.MAX_SCREEN_COL && row < m_gp.MAX_SCREEN_ROW) {
            int tileNum = m_mapTileNum[col][row];

            g2.drawImage(m_tile[tileNum].m_image, x, y, m_gp.TILE_SIZE, m_gp.TILE_SIZE, null);
            col++;
            x += m_gp.TILE_SIZE;
            if (col == m_gp.MAX_SCREEN_COL) {
                col = 0;
                row++;
                x = 0;
                y += m_gp.TILE_SIZE;
            }
        }

    }
}
