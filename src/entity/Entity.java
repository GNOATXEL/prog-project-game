package entity;

import lib.Vector2;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Entité de base du jeu
 */
public abstract class Entity {
    //	public int m_x, m_y;				//position sur la map
    public Vector2 position;
    // TODO : mettre dans LivingEntity
    public int m_speed;                    //Déplacement de l'entité
    public BufferedImage m_idleImage;    //Une image de l'entité

    public int width;
    public int height;

    public boolean collidesWith(Entity entity) {
        Rectangle thisBounds = new Rectangle((int) position.getX(), (int) position.getY(), width, height);
        Rectangle otherBounds = new Rectangle((int) entity.position.getX(), (int) entity.position.getY(), entity.width, entity.height);

        return thisBounds.intersects(otherBounds);
    }

}
