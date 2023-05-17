package entity;

import lib.Vector2;
import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class LivingEntity extends Entity{
    public int m_vie;

    BufferedImage m_idleImage2;

    BufferedImage l_image;


    GamePanel m_gp;
    int compteur;

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

    public BufferedImage getL_image(){
        return l_image;
    }


    public abstract Vector2 futurePosition();

    public abstract void draw(Graphics2D a_g2);{

    }
}
