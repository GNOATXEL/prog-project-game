package entity;

import lib.Vector2;
import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

/**
 * Défintition du comportement d'un joueur
 */
public class Player extends Entity {

    GamePanel m_gp;
    KeyHandler m_keyH;
    int compteurSaut;

    private static final float JUMP_FORCE = -10.0f; // Force du saut
    private static final float GRAVITY = 0.5f; // Force de gravité

    private final Vector2 velocity;

    /**
     * Constructeur de Player
     *
     * @param a_gp   GamePanel, pannel principal du jeu
     * @param a_keyH KeyHandler, gestionnaire des touches
     */
    public Player(GamePanel a_gp, KeyHandler a_keyH, int larg, int haut) {
        this.m_gp = a_gp;
        this.m_keyH = a_keyH;
        this.setDefaultValues();
        this.getPlayerImage();

        width = larg;
        height = haut;

        velocity = new Vector2(0, 0);
    }

    /**
     * Initialisation des données membres avec des valeurs par défaut
     */
    protected void setDefaultValues() {
        // TODO: à modifier sûrement
        position = new Vector2(50, 500);
        compteurSaut = 0;
    }

    /**
     * Récupération de l'image du personnage
     */
    public void getPlayerImage() {
        //gestion des exceptions
        try {
            m_idleImage = ImageIO.read(Objects.requireNonNull(getClass().getResource("/player/superhero.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Mise à jour des données du joueur
     */
    public void update() {
        // Gestion de la gravité
        applyGravity();

        // Gestion des mouvements horizontaux
        handleHorizontalMovement();

        // Gestion des collisions
        handleCollisions();

        if (m_keyH.isJumpPressed()) {
            // Perform jump action
            System.out.println("jump");
            if (isOnGround()) {
                System.out.println("osol");
                jump();
            }
        }

        position.addX(velocity.getX());
        position.addY(velocity.getY());

        // Other player update logic...
    }

    private boolean isOnGround() {
        // Vérifier la collision avec les plateformes ou le sol
        for (Entity entity : m_gp.unlivingEntities) {
            if (entity instanceof Brick) {
                if (collidesWith(entity) && position.getY() >= entity.position.getY() + entity.height) {
                    return true;
                }
            }
        }
        return false;
    }

    private void jump() {
        velocity.setY(-JUMP_FORCE); // Appliquer une force vers le haut pour le saut
    }

    private void applyGravity() {
        // Appliquer une force de gravité à la position du joueur
        velocity.addY(GRAVITY * m_gp.SCALE);
    }

    private void handleHorizontalMovement() {
        int moveSpeed = m_gp.SCALE; // Vitesse de déplacement horizontale

        if (m_keyH.isLeftPressed()) {
            velocity.setX(-moveSpeed); // Déplacer vers la gauche avec une vitesse négative
        } else if (m_keyH.isRightPressed()) {
            velocity.setX(moveSpeed); // Déplacer vers la droite avec une vitesse positive
        } else {
            velocity.setX(0); // Aucune touche de déplacement horizontale pressée, arrêter le mouvement horizontal
        }
    }

    private void handleCollisions() {
        for (Entity entity : m_gp.unlivingEntities) {
            if (entity != this && this.collidesWith(entity)) {
                if (entity instanceof Brick) {
                    handleBlockCollision((Brick) entity);
                } /*else if (entity instanceof Enemy) {
                    handleEnemyCollision((Enemy) entity);
                }*/
                // Ajoutez des conditions supplémentaires pour d'autres types d'entités, le cas échéant
            }
        }
    }

    private void handleBlockCollision(Brick block) {
        // Logique de gestion de collision avec un bloc
        // Par exemple, arrêter le mouvement horizontal ou vertical, rebondir, etc.

        // Déterminer la direction de la collision (gauche, droite, haut, bas)
        double dx = position.getX() - block.position.getX();
        double dy = position.getY() - block.position.getY();

        float intersectionX = (float) (Math.abs(dx) - (float) (width + block.width) / 2);
        float intersectionY = (float) (Math.abs(dy) - (float) (height + block.height) / 2);

        // Réajuster la position du joueur
        if (intersectionX > intersectionY) {
            if (dx > 0) {
                System.out.println("a");
                position.setX(block.position.getX() + (double) block.width / 2 + (double) width / 2);
            } else {
                System.out.println("b");
                position.setX(block.position.getX() - (double) block.width / 2 - (double) width / 2);
            }
        } else {
            if (dy > 0) {
                System.out.println("c");
                position.setY(block.position.getY() + (double) block.height / 2 + (double) height / 2);
            } else {
                System.out.println("d");
                position.setY(block.position.getY() - (double) block.height / 2 - (double) height / 2);
            }
        }
    }

    /*private void handleEnemyCollision(Enemy enemy) {
        // Logique de gestion de collision avec un ennemi
        // Par exemple, réduire les points de vie, déclencher une animation de mort, etc.
    }*/

    /**
     * Affichage de l'image du joueur dans la fenêtre du jeu
     *
     * @param a_g2 Graphics2D
     */
    public void draw(Graphics2D a_g2) {
        // récupère l'image du joueur
        BufferedImage l_image = m_idleImage;
        // affiche le personnage avec l'image "image", avec les coordonnées x et y, et de taille tileSize (16x16) sans échelle, et 48x48 avec échelle)
        a_g2.drawImage(l_image, (int) position.getX(), (int) position.getY(), m_gp.TILE_SIZE, m_gp.TILE_SIZE, null);
    }


}
