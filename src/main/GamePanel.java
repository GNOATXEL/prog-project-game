package main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;


import entity.Cleent;
import entity.Player;
import entity.UnlivingEntity;
import tile.TileManager;

import musica.MusicPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
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

    // FPS : taux de rafraichissement
    int m_FPS;

    // Création des différentes instances (Player, KeyHandler, TileManager, GameThread ...)
    KeyHandler m_keyH;
    Thread m_gameThread;
    Player m_player;
    int m_panel;
    TileManager[] m_tileM;

    HashSet<UnlivingEntity> unlivingEntities;
    HashSet<UnlivingEntity> unlivingEntities6;
    HashSet<UnlivingEntity> unlivingEntities2;
    HashSet<UnlivingEntity> unlivingEntities3;
    HashSet<UnlivingEntity> unlivingEntities4;
    HashSet<UnlivingEntity> unlivingEntities5;

    /**
     * Constructeur
     */
    public GamePanel() {
        m_FPS = 60;
        m_keyH = new KeyHandler();
        m_player = new Player(this, m_keyH, 16, 32);
        m_tileM = new TileManager[6];
        m_panel=0;

        m_tileM[0] = new TileManager(this,"/maps/map1_part1.txt");

        m_tileM[1] = new TileManager(this,"/maps/map1_part2.txt");

        Cleent cle = new Cleent(this, 170, 550);
        m_tileM[1].addUnlivingEntities(cle);

        m_tileM[2] = new TileManager(this,"/maps/map1_part3.txt");
        m_tileM[3] = new TileManager(this,"/maps/map1_part4.txt");
        m_tileM[4] = new TileManager(this,"/maps/map1_part5.txt");
        m_tileM[5] = new TileManager(this,"/maps/map1_part6.txt");


        unlivingEntities = m_tileM[0].getUnlivingEntities();
        unlivingEntities2 = m_tileM[1].getUnlivingEntities();
        unlivingEntities3 = m_tileM[2].getUnlivingEntities();
        unlivingEntities4 = m_tileM[3].getUnlivingEntities();
        unlivingEntities5 = m_tileM[4].getUnlivingEntities();
        unlivingEntities6 = m_tileM[5].getUnlivingEntities();

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
        MusicPlayer zik = new MusicPlayer(String.valueOf(Objects.requireNonNull(getClass().getResource("/zik/zikrandom.wav"))));
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
        boolean collision = false;
        for (UnlivingEntity unlivingEntity :
                unlivingEntities) {
            if (unlivingEntity.position.getX() < m_player.futurePosition().getX() + m_player.width &&
                    unlivingEntity.position.getX() + unlivingEntity.width > m_player.futurePosition().getX() &&
                    unlivingEntity.position.getY() < m_player.futurePosition().getY() + m_player.height &&
                    unlivingEntity.height + unlivingEntity.position.getY() > m_player.futurePosition().getY()) {
                collision = true;
            }
        }
        m_player.update(collision);
        if(m_player.futurePosition().getX()>=780 && m_player.getTile()==0){
            m_player.nextTile();
            this.nextPanel();
            m_player.position.setX(50);
        }
        else if(m_player.futurePosition().getX()>=780 && m_player.getTile()==1) {
            m_player.nextTile();
            this.nextPanel();
            m_player.position.setX(50);
        }

        else if(m_player.futurePosition().getY()>=780 && m_player.getTile()==2) { //ne marchera pas car fall n'utilise pas futureposition (mais je l'ai pas sur ma version)
            m_player.nextTile();
            this.nextPanel();
            m_player.position.setY(50);
        }

        else if(m_player.futurePosition().getX()<=20 && m_player.getTile()==3) {
            m_player.nextTile();
            this.nextPanel();
            m_player.position.setX(750);
        }

        else if(m_player.futurePosition().getX()<=20 && m_player.getTile()==4) {
            m_player.nextTile();
            this.nextPanel();
            m_player.position.setX(750);
        }
    }

    /**
     * Affichage des éléments
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        m_tileM[m_panel].draw(g2);
        m_player.draw(g2);
        if(this.m_panel==1) {
            for (UnlivingEntity entity : unlivingEntities2) {
                drawEntity(g2, entity);
            }
        }
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


    public void playMusic(String filePath) {
        try {
            File audioFile = new File(filePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }


}

