package main;

import entity.Brick;
import entity.Player;
import entity.Sol;
import entity.Spike;
import entity.UnlivingEntity;
import lib.Vector2;
import tile.TileManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.HashSet;
import java.util.Objects;

/**
 * Panel principal du jeu contenant la map principale
 */
public class GamePanel extends JPanel implements Runnable {

    public final int MAX_SCREEN_COL = 20;
    public final int MAX_SCREE_ROW = 20;                        // ces valeurs donnent une résolution 4:3
    //Paramètres de l'écran
    final int ORIGINAL_TILE_SIZE = 20;                            // une tuile de taille 16x16
    final int SCALE = 2;                                        // échelle utilisée pour agrandir l'affichage
    public final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE;    // 40*40
    public final int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COL; // 800
    public final int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREE_ROW;    // 800

    public final int GRAVITY = 7;
    public final int TERMINAL_VELOCITY = 300;
    public Image HEART_ICON;
    public Image HEART_EMPTY_ICON;
    private Image gameOverBackground;

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
        boolean pickable = false; //TODO : mettre les pickables
//        System.out.println("unlivingEntities = " + unlivingEntities);
        m_player.update(pickable);

        if(m_keyH.takes_damage) {
            m_player.takingDamage(1);
            m_keyH.takes_damage = false;
        }

        if(m_player.m_vie <= 0) {
            gameOver();
        }
    }

    private void gameOver() {
        m_gameThread.interrupt();
        m_gameThread = null;

        try {
            gameOverBackground = ImageIO.read(Objects.requireNonNull(getClass().getResource("/game_over.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        m_player.update(false);
    }

    public boolean collideSol() {
        for (UnlivingEntity unlivingEntity :
                unlivingEntities) {
            Vector2 pos = m_player.futurePosition();
            if (unlivingEntity instanceof Sol && pos.getY() > unlivingEntity.position.getY() - unlivingEntity.height &&
                    pos.getX() < unlivingEntity.position.getX() + unlivingEntity.width &&
                    pos.getX() + m_player.width > unlivingEntity.position.getX() ) {
                return true;
            }
        }
        return false;
    }

    public boolean collideMP() {
        for (UnlivingEntity unlivingEntity :
                unlivingEntities) {
            Vector2 pos=m_player.futurePosition();
            if (unlivingEntity instanceof Brick && pos.getX() < unlivingEntity.position.getX() + unlivingEntity.width &&
                    pos.getX() + m_player.width > unlivingEntity.position.getX() &&
                    pos.getY() > unlivingEntity.position.getY() - unlivingEntity.height && //mettre -1 en Y et verif
                     pos.getY() + m_player.height  < unlivingEntity.position.getY()) {

//                System.out.println(m_player.futurePosition());
//                System.out.println(unlivingEntity.position);
//                System.out.println("----");
                return true;
            }
            if(unlivingEntity instanceof Spike && pos.getY() > unlivingEntity.position.getY() - unlivingEntity.height &&
                    pos.getX() < unlivingEntity.position.getX() + unlivingEntity.width &&
                    pos.getX() + m_player.width > unlivingEntity.position.getX()) {
                m_player.takingDamage(1);
                return false;
            }
        }
        return false;
    }

    /**
     * Affichage des éléments
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        if(m_gameThread != null) {
            m_tileM.draw(g2);
            m_player.draw(g2);
        }
        g2.drawImage(gameOverBackground, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, null);
        g2.dispose();
    }

}
