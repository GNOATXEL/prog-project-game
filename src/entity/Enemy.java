package entity;

import lib.Vector2;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public abstract class Enemy extends LivingEntity{

    public int m_compteur;
    BufferedImage m_idleImage2;

    public Enemy(GamePanel a_gp, int larg, int haut, int x, int y) {
        super(a_gp, larg, haut);
        this.getImage();
        position=new Vector2(x,y);
    }

    public abstract Vector2 futurePosition();

    public void update(){
        compteur=compteur+1;
        position=futurePosition();
    }

    public abstract void getImage();
    public abstract void draw(Graphics2D a_g2);
}