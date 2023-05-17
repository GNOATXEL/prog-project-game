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
    int vertical_speed;
    int compteurSaut;
    int acc;
    int y;


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
        position = new Vector2(50, 525);
        m_speed = 4;
        compteurSaut = 0;
        acc = m_gp.GRAVITY;
        y=0;
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

    /**
     * Mise à jour des données du joueur
     */
    public void update(boolean pickable) {
        if(!m_gp.collideSol()) { //on est pas sur le sol
            if(m_keyH.is_jumping && compteurSaut < 20) { //mais on saute donc normal
                position=futurePosition();
                compteurSaut++;
            } else {
                if(!m_gp.collideMP()) position= futurePosition();
                fall();
            }
        } else { //sur le sol
            if(!m_gp.collideMP()){ //et pas dans un obstacle
                position=futurePosition();
                compteurSaut=0;
            } else{
                if(pickable) position=futurePosition();
                if(!m_keyH.is_jumping) compteurSaut=0;
            }
        }
       /*
        if (!m_gp.collide()) {
            if (m_keyH.is_jumping && compteurSaut < 20) {
                isFalling=false;
                position = futurePosition();
                compteurSaut++;
                acc=0;
                vertical_speed=0;
            } else {
                isFalling=true;
                position=futurePosition();
                fall();
            }*/
        /*}else {
            isFalling=false;
            if (pickable) {
                position = futurePosition();
                vertical_speed = 0;
                acc = 0;
            }
            if (!m_keyH.is_jumping) {
                compteurSaut = 0;
                vertical_speed = 0;
                acc = 0;
            }
        }*/

        System.out.println(futurePosition());
    }

    public void fall() {
        for(int i = 0 ; i<m_gp.GRAVITY-1;i++){
            if(!m_gp.collideSol()) {
                position.addY(1);
            } else {
                return;
            }
        }
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
    }


}
