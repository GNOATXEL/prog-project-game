package main;

import entity.*;
import lib.Vector2;
import musica.MusicPlayer;
import tile.TileManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;

/**
 * Panel principal du jeu contenant la map principale
 */
public class GamePanel extends JPanel implements Runnable {

    public final int MAX_SCREEN_COL = 20;
    public final int MAX_SCREE_ROW = 20;                        // ces valeurs donnent une résolution 4:3
    public final int GRAVITY = 7;
    //Paramètres de l'écran
    final int ORIGINAL_TILE_SIZE = 20;                            // une tuile de taille 16x16
    final int SCALE = 2;                                        // échelle utilisée pour agrandir l'affichage
    public final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE;    // 40*40
    public final int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COL; // 800
    public final int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREE_ROW;    // 800
    public Image HEART_ICON;
    public Image HEART_EMPTY_ICON;
    // FPS : taux de rafraichissement
    int m_FPS;
    // Création des différentes instances (Player, KeyHandler, TileManager, GameThread ...)
    KeyHandler m_keyH;
    Thread m_gameThread;
    public Player m_player;
    int m_panel;
    TileManager[] m_tileM;
    HashSet<UnlivingEntity> unlivingEntities;
    HashSet<UnlivingEntity> unlivingEntities2;
    HashSet<UnlivingEntity> unlivingEntities3;
    HashSet<UnlivingEntity> unlivingEntities4;
    HashSet<UnlivingEntity> unlivingEntities5;
    HashSet<UnlivingEntity> unlivingEntities6;
    HashSet<UnlivingEntity> unlivingEntities7;
    ArrayList<HashSet<UnlivingEntity>> m_unlivingEntitiesList = new ArrayList<>(7);
    HashSet<UnlivingEntity> currentUnlivingEntities;
    private Image gameOverBackground;

    /**
     * Constructeur
     */
    public GamePanel() {
        m_FPS = 60;
        m_keyH = new KeyHandler();
        m_player = new Player(this, m_keyH, TILE_SIZE, TILE_SIZE);
        //m_player = new Player(this, m_keyH, 16, 32);
        m_tileM = new TileManager[7];
        m_panel = 0;

        m_tileM[0] = new TileManager(this, "/maps/map1_part1.txt");
        m_tileM[1] = new TileManager(this, "/maps/map1_part2.txt");
        m_tileM[2] = new TileManager(this, "/maps/map1_part3.txt");
        m_tileM[3] = new TileManager(this, "/maps/map1_part4.txt");
        m_tileM[4] = new TileManager(this, "/maps/map1_part5.txt");
        m_tileM[5] = new TileManager(this, "/maps/map1_part6.txt");
        m_tileM[6] = new TileManager(this, "/maps/map1_part7.txt");


        Cleent cle = new Cleent(this, 170, 550);
        m_tileM[1].addUnlivingEntities(cle);


        Coeur coeur = new Coeur(this, 60, 300);
        m_tileM[3].addUnlivingEntities(coeur);

        unlivingEntities = m_tileM[0].getUnlivingEntities();
        unlivingEntities2 = m_tileM[1].getUnlivingEntities();
        unlivingEntities3 = m_tileM[2].getUnlivingEntities();
        unlivingEntities4 = m_tileM[3].getUnlivingEntities();
        unlivingEntities5 = m_tileM[4].getUnlivingEntities();
        unlivingEntities6 = m_tileM[5].getUnlivingEntities();
        unlivingEntities7 = m_tileM[6].getUnlivingEntities();

        m_unlivingEntitiesList.add(0, unlivingEntities);
        m_unlivingEntitiesList.add(1, unlivingEntities2);
        m_unlivingEntitiesList.add(2, unlivingEntities3);
        m_unlivingEntitiesList.add(3, unlivingEntities4);
        m_unlivingEntitiesList.add(4, unlivingEntities5);
        m_unlivingEntitiesList.add(5, unlivingEntities6);
        m_unlivingEntitiesList.add(6, unlivingEntities7);

        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(m_keyH);
        this.setFocusable(true);

        setCurrentUnlivingEntities(m_panel);
    }


    /**
     * Lancement du thread principal
     */
    public void startGameThread() {
        m_gameThread = new Thread(this);
        m_gameThread.start();
    }

    public void setCurrentUnlivingEntities(int i) {
        currentUnlivingEntities = m_unlivingEntitiesList.get(i);
    }

    public void run() {

        double drawInterval = 1_000_000_000.0 / m_FPS; // rafraichissement chaque 0.0166666 secondes
        double nextDrawTime = System.nanoTime() + drawInterval;
        MusicPlayer zik = new MusicPlayer(String.valueOf(new File("res/zik/zikrandom.wav")));
        Thread OST = new Thread(zik);
        OST.start();

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
        setCurrentUnlivingEntities(m_panel);
        boolean pickable = false; //TODO : mettre les pickables
        m_player.update(pickable);

        if (m_player.futurePosition().getX() >= 780 && m_player.getTile() == 0) {
            m_player.nextTile();
            this.nextPanel();
            m_player.position.setX(50);
        } else if (m_player.futurePosition().getX() >= 780 && m_player.getTile() == 1) {
            m_player.nextTile();
            this.nextPanel();
            m_player.position.setX(50);
        } else if (m_player.futurePosition().getY() >= 780 && m_player.getTile() == 2) { //ne marchera pas car fall n'utilise pas futureposition (mais je l'ai pas sur ma version)
            m_player.nextTile();
            this.nextPanel();
            m_player.position.setY(50);
        } else if (m_player.futurePosition().getX() <= 20 && m_player.getTile() == 3) {
            m_player.nextTile();
            this.nextPanel();
            m_player.position.setX(750);
        } else if (m_player.futurePosition().getX() <= 20 && m_player.getTile() == 4) {
            m_player.nextTile();
            this.nextPanel();
            m_player.position.setX(750);
        } else if (m_player.futurePosition().getX() <= 20 && m_player.getTile() == 5) {
            m_player.nextTile();
            this.nextPanel();
            m_player.position.setX(750);
        }

        if (m_keyH.takes_damage) {
            m_player.takingDamage(1);
            m_keyH.takes_damage = false;
        }

        if(m_keyH.getHP) {
            m_player.addLife(1);
            m_keyH.getHP = false;
        }

        if (m_player.m_vie <= 0) {
            gameOver();
        }
        for (UnlivingEntity entity : currentUnlivingEntities){
            if(entity instanceof Cleent){
                if(entity.position.getX()-20<m_player.position.getX() && entity.position.getX()+40>m_player.position.getX() && entity.position.getY()-20<m_player.position.getY() && entity.position.getY()+40>m_player.position.getY()){
                    ((Cleent) entity).quoicouPicked();
                    ((Cleent) entity).update();
                }

                if(entity.position.getX()-20<m_player.position.getX() && entity.position.getX()+40>m_player.position.getX() && entity.position.getY()-20<m_player.position.getY() && entity.position.getY()+40>m_player.position.getY()){
                    ((Coeur) entity).quoicouPicked();
                    ((Coeur) entity).update();
                }
            }
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
    }

    public boolean collideSol() {
        for (UnlivingEntity unlivingEntity :
                currentUnlivingEntities) {
            Vector2 pos = m_player.futurePosition();
            if (unlivingEntity instanceof Sol && pos.getY() > unlivingEntity.position.getY() - unlivingEntity.height &&
                    pos.getX() < unlivingEntity.position.getX() + unlivingEntity.width &&
                    pos.getX() + m_player.width > unlivingEntity.position.getX()) {
                return true;
            } else if (unlivingEntity instanceof Spike && pos.getX() < unlivingEntity.position.getX() + unlivingEntity.width &&
                    pos.getX() + m_player.width > unlivingEntity.position.getX() &&
                    pos.getY() < unlivingEntity.position.getY() + unlivingEntity.height && //mettre -1 en Y et verif
                    m_player.height + pos.getY() > unlivingEntity.position.getY()) {
                m_player.takingDamage(1);
            }
        }
        return false;
    }

    public boolean collideMP() {
        for (UnlivingEntity unlivingEntity :
                currentUnlivingEntities) {
            Vector2 pos = m_player.futurePosition();
            if (unlivingEntity instanceof Brick && pos.getX() < unlivingEntity.position.getX() + unlivingEntity.width &&
                    pos.getX() + m_player.width > unlivingEntity.position.getX() &&
                    pos.getY() > unlivingEntity.position.getY() - unlivingEntity.height && //mettre -1 en Y et verif
                    pos.getY() + m_player.height < unlivingEntity.position.getY()) {
                return true;
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
        if (m_gameThread != null) {
            m_tileM[m_panel].draw(g2);
            m_player.draw(g2);
            for (UnlivingEntity entity : currentUnlivingEntities) {
                drawEntity(g2, entity);
            }
        }
        g2.drawImage(gameOverBackground, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, null);
        g2.dispose();
    }

    public void nextPanel() {
        m_panel++;
    }

    public void drawEntity(Graphics2D a_g2, UnlivingEntity entity) {
        // récupère l'image du joueur
        BufferedImage l_image = entity.m_idleImage;
        // affiche le personnage avec l'image "image", avec les coordonnées x et y, et de taille tileSize (16x16) sans échelle, et 48x48 avec échelle)
        a_g2.drawImage(l_image, entity.position.getX(), entity.position.getY(), this.TILE_SIZE, this.TILE_SIZE, null);
    }
}