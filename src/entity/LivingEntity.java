package entity;

import lib.Vector2;
import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class LivingEntity extends Entity{
    GamePanel m_gp;

    public LivingEntity(GamePanel a_gp, int larg, int haut) {
        this.m_gp = a_gp;
        this.setDefaultValues();

        width = larg;
        height = haut;
    }

    protected void setDefaultValues() {
        position = new Vector2(100, 100);
        m_speed = 4;
    }

    public abstract void getImage();

    public void update(boolean collision) {
        if (!collision) position = futurePosition();
    }

    public abstract Vector2 futurePosition();

    public void draw(Graphics2D a_g2) {
        // récupère l'image de l'entité
        BufferedImage l_image = m_idleImage;
        // affiche l'entité avec l'image "image", avec les coordonnées x et y, et de taille tileSize (16x16) sans échelle, et 48x48 avec échelle)
        a_g2.drawImage(l_image, position.getX(), position.getY(), m_gp.TILE_SIZE, m_gp.TILE_SIZE, null);
    }
}
