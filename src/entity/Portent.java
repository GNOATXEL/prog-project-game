package entity;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Portent extends UnlivingEntity{

    private boolean open;

    GamePanel m_gp;

    public Portent(GamePanel a_gp, int x, int y){
        super(x,y,20,20, false);
        open=false;
        this.getPorteImage();
        this.m_gp = a_gp;
        m_speed = 0;
    }

    public void quoicOpen() {
        //gérer quand le joueur il a une clé + il touche la porte
        open = true;
    }


    public void update(){
        //faudra faire que ça update la collision
    }

    public void getPorteImage() {
        //gestion des exceptions

        try {
             if(!open) {
                 m_idleImage = ImageIO.read(Objects.requireNonNull(getClass().getResource("/pasvivant/portefermee.png")));
             } else {
                 m_idleImage = ImageIO.read(Objects.requireNonNull(getClass().getResource("/pasvivant/porteouverte.png")));
             }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D a_g2) {
        // récupère l'image du joueur
        BufferedImage l_image = m_idleImage;
        // affiche le personnage avec l'image "image", avec les coordonnées x et y, et de taille tileSize (16x16) sans échelle, et 48x48 avec échelle)
        a_g2.drawImage(l_image, position.getX(), position.getY(), m_gp.TILE_SIZE, m_gp.TILE_SIZE, null);
    }
}
