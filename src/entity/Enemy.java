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

    public int cooldown;


    public int m_vie;

    public Enemy(GamePanel a_gp, int larg, int haut, int x, int y,int vie) {
        super(a_gp, larg, haut);
        m_vie=vie;
        cooldown=0;
        this.getImage();
        position=new Vector2(x,y);
    }
    public void takeDamage(){
        if(cooldown==0){
            cooldown=50;
            m_vie--;
        }
    }

    public int getHP(){
        return m_vie;
    }
    public abstract Vector2 futurePosition();

    public void update(){
        compteur=compteur+1;
        if (cooldown > 0) {
            cooldown--;
            System.out.println(cooldown);
        }
        position=futurePosition();
    }

    public abstract void getImage();
    public abstract void draw(Graphics2D a_g2);
}