package entity;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Cleent extends UnlivingEntity{

    private boolean picked;

    GamePanel m_gp;

    public Cleent(GamePanel a_gp, int x, int y){
        super(x,y,20,20, false);
        picked=false;
        this.getCleentImage();
        this.m_gp = a_gp;
        m_speed = 0;
    }

    public void quoicouPicked(){
        //gérer quand le joueur touche la clé (et la ramasse du coupent)

        picked=true;
    }

    public void update(){
        if(picked=true){
            position.setX(24);
            position.setX(936);
        }

    }

    public void getCleentImage() {
        //gestion des exceptions
        try {
            m_idleImage = ImageIO.read(Objects.requireNonNull(getClass().getResource("/pasvivant/cleent.png")));
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
