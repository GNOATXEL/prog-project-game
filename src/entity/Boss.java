package entity;

import lib.Vector2;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Boss extends Enemy{
    public Boss(GamePanel a_gp, int larg, int haut, int x, int y) {
        super(a_gp, larg, haut, x, y);
        m_vie = 5;
    }

    public Vector2 futurePosition() {
        Vector2 direction=new Vector2(0,0);
        if (compteur<200) {
            direction.setX(2);
        }
        else {
            direction.setX(-2);
        }
        if (compteur>400){
            compteur=0;
        }
        System.out.println(direction);
        System.out.println(position);
        return new Vector2(direction.addVector(position));
    }

    public void draw(Graphics2D a_g2) {
        if (compteur<200) {
            // récupère l'image de l'entité
            BufferedImage l_image = m_idleImage;
            // affiche l'entité avec l'image "image", avec les coordonnées x et y, et de taille tileSize (16x16) sans échelle, et 48x48 avec échelle)
            a_g2.drawImage(l_image, position.getX(), position.getY(), m_gp.TILE_SIZE, m_gp.TILE_SIZE, null);
        }
        else {
            // récupère l'image de l'entité
            BufferedImage l_image = m_idleImage2;
            // affiche l'entité avec l'image "image", avec les coordonnées x et y, et de taille tileSize (16x16) sans échelle, et 48x48 avec échelle)
            a_g2.drawImage(l_image, position.getX(), position.getY(), m_gp.TILE_SIZE, m_gp.TILE_SIZE, null);
        }
    }

    public void getImage() {
        //gestion des exceptions
        try {
            m_idleImage = ImageIO.read(Objects.requireNonNull(getClass().getResource("/player/boss2.png")));
            m_idleImage2 = ImageIO.read(Objects.requireNonNull(getClass().getResource("/player/boss1.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
