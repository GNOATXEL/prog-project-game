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
    KeyHandler m_keyH;
    int vertical_speed;
    int compteurSaut;
    int acc;
    int y;
    boolean isFalling;

    public int m_vie;
    long lastDamageTaken;

    int tile = 0;

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
        isFalling=false;
        m_vie=3;
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
        lastDamageTaken = new Date().getTime();
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
    public void update(boolean pickable) {
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
            }
        }else {
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
        }

        System.out.println(futurePosition());
    }

    public void fall() {
        for(int i = 0 ; i<m_gp.GRAVITY;i++){
            if(!m_gp.collide()) {
                position.addY(1);
            } else {
                return;
            }
        }
    }

    public boolean takingDamage(int dmg) {
        long currentTime = new Date().getTime();

        position.setX(50);
        position.setY(525);

        if(lastDamageTaken + 1 * 1000 < currentTime) {
            lastDamageTaken = currentTime;

            if (m_vie - dmg > 0) {
                m_vie -= dmg;
                return true;
            }
            m_vie = 0;
        }
        return false;
    }

    public void nextTile(){
        tile++;
    }

    public int getTile(){
        return tile;
    }

    public Vector2 futurePosition() {
        Vector2 res=new Vector2(m_keyH.directions.scalarMultiplication(m_speed));
        if(isFalling) res.addY(1);
        return position.addVector(res);
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
            a_g2.drawImage(m_gp.HEART_ICON, m_gp.TILE_SIZE * (i+1), m_gp.TILE_SIZE, m_gp.TILE_SIZE, m_gp.TILE_SIZE, null);
        }
        for (int i = 0; i < (3-m_vie); i++) {
            a_g2.drawImage(m_gp.HEART_EMPTY_ICON, m_gp.TILE_SIZE * (3 - i), m_gp.TILE_SIZE, m_gp.TILE_SIZE, m_gp.TILE_SIZE, null);
        }

    }


}
