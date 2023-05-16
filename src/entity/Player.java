package entity;

import lib.Vector2;
import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends LivingEntity {
    KeyHandler m_keyH;
    int vertical_speed;
    int compteurSaut;

    int m_vie;

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
        m_vie=3;
    }

    /**
     * Initialisation des données membres avec des valeurs par défaut
     */
    protected void setDefaultValues() {
        // TODO: à modifier sûrement
        position = new Vector2(50, 520);
        m_speed = 4;
        compteurSaut = 0;
    }

    /**
     * Récupération de l'image du personnage
     */
    public void getImage() {
        //gestion des exceptions
        try {
            m_idleImage = ImageIO.read(Objects.requireNonNull(getClass().getResource("/player/superhero.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Mise à jour des données du joueur
     */
    public void update(boolean collision, boolean pickable) {
        if (!collision) {
            if (m_keyH.is_jumping && compteurSaut < 20) {
                position = futurePosition();
                compteurSaut++;

            } else {
                position = fall();
            }
        } else if (collision && pickable) {
            position = futurePosition();
            vertical_speed = 0;
            if(!m_keyH.is_jumping) compteurSaut = 0;
        } else {
            position = futurePosition();
            vertical_speed = 0;
            if(!m_keyH.is_jumping) compteurSaut = 0;
        }

        System.out.println(futurePosition());
    }

    public Vector2 fall() {
        Vector2 chute = new Vector2(0, m_gp.GRAVITY);
        return position.addVector(chute);
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
        a_g2.drawImage(l_image, position.getX(), position.getY(), m_gp.TILE_SIZE, m_gp.TILE_SIZE, null);
    }
}
