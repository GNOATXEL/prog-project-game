package entity;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Coeur extends UnlivingEntity {

    GamePanel m_gp;
    private boolean picked;
    private int vie;

    public Coeur(GamePanel a_gp, int x, int y) {
        super(x, y, 20, 20, true);
        picked = false;
        this.getCoeurImage();
        this.m_gp = a_gp;
        m_speed = 0;
        vie = 1;
    }

    public void quoicouPicked() {
        //gérer quand le joueur touche le coeur (et le ramasse du coupent)
        picked = true;
        m_idleImage = null;
    }

    public void update() {
        if (picked) {
            m_gp.m_player.addLife(vie);
            vie = 0;
        }
    }

    public void getCoeurImage() {
        // gestion des exceptions
        try {
            m_idleImage = ImageIO.read(new File("res/tiles/COEURPLEIN.png"));
        } catch (IOException e) {
            System.out.println("Image non trouvée");
        }
    }
}