package entity;

import lib.Vector2;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

public class Enemy extends LivingEntity{
    Vector2 m_position;
    int m_maxi;

    public Enemy(GamePanel a_gp, int larg, int haut, int x, int y) {
        super(a_gp, larg, haut);
        this.getImage();
        m_position=new Vector2(x,y);
        m_maxi=16;
    }

    public Vector2 futurePosition() {
        Random r=new Random();
        boolean b=r.nextBoolean();
        Vector2 direction=new Vector2(0,0);
        if (b) {
            direction.setX(5);
        }
        else {
            direction.setX(-5);
        }
        if (direction.getX()+ position.getX()<=m_position.getX()+m_maxi) {
            direction.addVector(position);
        }
        return direction;
    }

    public void getImage() {
        //guestion des exceptions
        try {
            m_idleImage = ImageIO.read(Objects.requireNonNull(getClass().getResource("/player/mechant.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
