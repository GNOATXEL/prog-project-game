package entity;

import lib.Vector2;

import java.awt.image.BufferedImage;

/**
 * Entité de base du jeu
 *
 */
public abstract class Entity {
//	public int m_x, m_y;				//position sur la map
	public Vector2 position;
	public int m_speed;					//Déplacement de l'entité
	public BufferedImage m_idleImage;	//Une image de l'entité
}
