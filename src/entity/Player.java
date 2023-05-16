package entity;

import lib.Vector2;
import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

/**
 * Défintition du comportement d'un joueur
 */
public class Player extends Entity {

    GamePanel m_gp;
    KeyHandler m_keyH;
    int compteurSaut;
    private double acc_y;

    /**
     * Constructeur de Player
     *
     * @param a_gp   GamePanel, pannel principal du jeu
     * @param a_keyH KeyHandler, gestionnaire des touches
     */
    public Player(GamePanel a_gp, KeyHandler a_keyH, int larg, int haut) {
        this.m_gp = a_gp;
        this.m_keyH = a_keyH;
        this.setDefaultValues();
        this.getPlayerImage();

        width = larg;
        height = haut;
    }

    /**
     * Initialisation des données membres avec des valeurs par défaut
     */
    protected void setDefaultValues() {
        // TODO: à modifier sûrement
        position = new Vector2(50, 550);
        m_speed = 4;
        compteurSaut = 0;
    }

    /**
     * Récupération de l'image du personnage
     */
    public void getPlayerImage() {
        //gestion des exceptions
        try {
            m_idleImage = ImageIO.read(Objects.requireNonNull(getClass().getResource("/player/superhero.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void pre_update() {
        acc_y = m_gp.GRAVITY;

        if (m_keyH.directions.getY() == 0 && m_keyH.is_jumping) {
            acc_y = -m_gp.ACCELERATION;
        }

        m_keyH.directions.addY(m_speed * acc_y);
        //position.addY(m_keyH.directions.getY());
    }

    public void touches_ground() {
        System.out.println("sol touché");
        if (acc_y != m_gp.GRAVITY)
            position.addY(-1 * m_speed);

        m_keyH.directions.setY(0);
        acc_y = 0;
    }

    /**
     * Mise à jour des données du joueur
     */
    public void update(boolean collision, boolean pickable) {


        if (!collision) {
            System.out.println("jpp");
            position = futurePosition();
        }


        //System.out.println(futurePosition());
    }

    public Vector2 futurePosition() {
        return position.addVector(m_keyH.directions);
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
        a_g2.drawImage(l_image, (int) position.getX(), (int) position.getY(), m_gp.TILE_SIZE, m_gp.TILE_SIZE, null);
    }
}
