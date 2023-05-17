package entity;

import lib.Vector2;
import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;

public class Player extends LivingEntity {
    public static int VIE_MAX = 3;
    public int m_vie;
    KeyHandler m_keyH;
    int compteurSaut;
    long lastDamageTaken;

    public BufferedImage m_idleImage2;

    boolean hasCleent=false;

    int tile = 0;
    private Vector2[] positionsDepart;

    /**
     * Constructeur de Player
     *
     * @param a_gp   GamePanel, pannel principal du jeu
     * @param a_keyH KeyHandler, gestionnaire des touches
     */
    public Player(GamePanel a_gp, KeyHandler a_keyH, int larg, int haut) {
        super(a_gp, larg, haut);

        this.getImage();
        this.m_keyH = a_keyH;
        this.setDefaultValues();

        width = larg;
        height = haut;
        m_vie = VIE_MAX;
    }

    /**
     * Initialisation des données membres avec des valeurs par défaut
     */
    protected void setDefaultValues() {
        positionsDepart = new Vector2[]{new Vector2(50, 525), new Vector2(50, 50), new Vector2(30, 150),
                new Vector2(650, 50), new Vector2(720, 520), new Vector2(720, 50), new Vector2(720, 520)};
        position = positionsDepart[0];
        m_speed = 4;
        compteurSaut = 0;
        lastDamageTaken = new Date().getTime();
    }

    /**
     * Récupération de l'image du personnage
     */
    public void getImage() {
        // gestion des exceptions
        try {
            m_idleImage = ImageIO.read(Objects.requireNonNull(getClass().getResource("/player/joueur1.png")));
            m_idleImage2 = ImageIO.read(Objects.requireNonNull(getClass().getResource("/player/joueur2.png")));
        } catch (IOException e) {
            System.out.println("Image du héros non trouvée.");
        }
    }

    /**
     * Mise à jour des données du joueur
     */
    public void update(boolean pickable) {
        if (!m_gp.collideSol()) { //on n'est pas sur le sol
            if (m_keyH.is_jumping && compteurSaut < 30) { //mais on saute donc normal
                if (!m_gp.collideMP()) position = futurePosition();
                compteurSaut++;
            } else {
                if (!m_gp.collideMP()) position = futurePosition();
                compteurSaut++;
                fall();
            }
        } else { //sur le sol empêche de jump dans les murs
            if (!m_gp.collideMP()) { //et pas dans un obstacle
                position = futurePosition();
                compteurSaut = 0;
            } else {
                if (pickable) position = futurePosition();
                if (!m_keyH.is_jumping) compteurSaut = 0;
            }
        }
    }

    public void fall() {
        for (int i = 0; i < m_gp.GRAVITY - 1; i++) {
            if (!m_gp.collideSol()) {
                position.addY(1);
            } else {
                return;
            }
        }
    }

    public void takingDamage(int dmg) {
        long currentTime = new Date().getTime();

        position = positionsDepart[getTile()];

        if (lastDamageTaken + 1000 < currentTime) {
            lastDamageTaken = currentTime;

            if (m_vie - dmg > 0) {
                m_vie -= dmg;
                return;
            }
            m_vie = 0;
        }
    }

    public void addLife(int hp) {
        m_vie += hp;
    }

    public void nextTile() {
        tile++;
    }

    public int getTile() {
        return tile;
    }

    public void setCleent(boolean oui) {
        hasCleent=oui;
    }

    public boolean statusCleent() {
        return hasCleent;
    }

    public Vector2 futurePosition() {
        return position.addVector(m_keyH.directions.scalarMultiplication(m_speed));
    }

    /**
     * Affichage de l'image du joueur dans la fenêtre du jeu
     *
     * @param a_g2 Graphics2D
     */
    public void draw(Graphics2D a_g2) {
        // récupère l'image du joueur
        BufferedImage l_image = m_idleImage;

        // affiche le personnage avec l'image "image", avec les coordonnées x et y, et de taille tileSize (16x16) sans échelle, et 48x48 avec échelle)
        a_g2.drawImage(l_image, position.getX(), position.getY(), m_gp.TILE_SIZE, m_gp.TILE_SIZE, null);

        // affiche la vie
        for (int i = 0; i < m_vie; i++) {
            a_g2.drawImage(m_gp.HEART_ICON, m_gp.TILE_SIZE * (i + 1), m_gp.TILE_SIZE, m_gp.TILE_SIZE, m_gp.TILE_SIZE, null);
        }
        for (int i = 0; i < (VIE_MAX - m_vie); i++) {
            a_g2.drawImage(m_gp.HEART_EMPTY_ICON, m_gp.TILE_SIZE * (VIE_MAX - i), m_gp.TILE_SIZE, m_gp.TILE_SIZE, m_gp.TILE_SIZE, null);
        }

    }


}
