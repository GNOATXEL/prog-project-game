package entity;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Coeur extends UnlivingEntity {

    GamePanel m_gp;
    private boolean picked;

    public Coeur(GamePanel a_gp, int x, int y) {
        super(x, y, 20, 20, true);
        picked = false;
        this.getCoeurImage();
        this.m_gp = a_gp;
        m_speed = 0;
    }

    public void quoicouPicked() {
        //gérer quand le joueur touche le coeur (et le ramasse du coupent)
        picked = true;
    }

    public void update() {
        if(picked) {
            position.setY(20);
            position.setX(740);
            m_gp.m_player.addLife(1);
        }
    }

    public void getCoeurImage() {
        //gestion des exceptions
        try {
            m_idleImage = ImageIO.read(new File("res/pasvivant/COEURPLEIN.png"));
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