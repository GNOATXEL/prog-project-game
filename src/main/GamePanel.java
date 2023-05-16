package main;

import entity.Player;
import entity.UnlivingEntity;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;

/**
 * Panel principal du jeu contenant la map principale
 */
public class GamePanel extends JPanel implements Runnable {

    public final int MAX_SCREEN_COL = 20;
    public final int MAX_SCREE_ROW = 20;                        // ces valeurs donnent une résolution 4:3
    //Paramètres de l'écran
    final int ORIGINAL_TILE_SIZE =20;                            // une tuile de taille 16x16
    final int SCALE = 2;                                        // échelle utilisée pour agrandir l'affichage
    public final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE;    // 48x48
    public final int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COL; // 768 pixels
    public final int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREE_ROW;    // 576 pixels

    public final int GRAVITY = 7;
    public final int TERMINAL_VELOCITY = 300;

    // FPS : taux de rafraichissement
    int m_FPS;

    // Création des différentes instances (Player, KeyHandler, TileManager, GameThread ...)
    KeyHandler m_keyH;
    Thread m_gameThread;
    Player m_player;
    TileManager m_tileM;

    HashSet<UnlivingEntity> unlivingEntities;

    /**
     * Constructeur
     */
    public GamePanel() {
        m_FPS = 60;
        m_keyH = new KeyHandler();
        m_player = new Player(this, m_keyH, TILE_SIZE, TILE_SIZE);
        m_tileM = new TileManager(this);

        unlivingEntities = m_tileM.getUnlivingEntities();

        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(m_keyH);
        this.setFocusable(true);
    }

    /**
     * Lancement du thread principal
     */
    public void startGameThread() {
        m_gameThread = new Thread(this);
        m_gameThread.start();
    }

    public void run() {

        double drawInterval = 1000000000 / m_FPS; // rafraichissement chaque 0.0166666 secondes
        double nextDrawTime = System.nanoTime() + drawInterval;

        while (m_gameThread != null) { //Tant que le thread du jeu est actif

            //Permet de mettre à jour les différentes variables du jeu
            this.update();

            //Dessine sur l'écran le personnage et la map avec les nouvelles informations. la méthode "paintComponent" doit obligatoirement être appelée avec "repaint()"
            this.repaint();

            //Calcule le temps de pause du thread
            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime / 1000000;

                if (remainingTime < 0) {
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime);
                nextDrawTime += drawInterval;

            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }


    /**
     * Mise à jour des données des entités
     */
    public void update() {
        boolean collision = false;
        boolean pickable = false;
//        System.out.println("unlivingEntities = " + unlivingEntities);
        for (UnlivingEntity unlivingEntity :
                unlivingEntities) {
            if (unlivingEntity.position.getX() <= m_player.futurePosition().getX() + m_player.width &&
                    unlivingEntity.position.getX() + unlivingEntity.width >= m_player.futurePosition().getX() &&
                    unlivingEntity.position.getY() <= m_player.futurePosition().getY() + m_player.height &&
                    unlivingEntity.height + unlivingEntity.position.getY() >= m_player.futurePosition().getY()) {

//                System.out.println(m_player.futurePosition());
//                System.out.println(unlivingEntity.position);
//                System.out.println("----");
                collision = true;
                if(unlivingEntity.pickable) pickable=true;
            }
        }
        m_player.update(collision, pickable);
    }

    /**
     * Affichage des éléments
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        m_tileM.draw(g2);
        m_player.draw(g2);
        g2.dispose();
    }

}
