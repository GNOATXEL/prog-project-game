package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import javax.imageio.ImageIO;

import lib.Vector2;
import main.GamePanel;
import main.KeyHandler;

/**
 * Défintition du comportement d'un joueur
 *
 */
public class Player extends Entity{

	GamePanel m_gp;
	KeyHandler m_keyH;
	Vector2 position;
	
	/**
	 * Constructeur de Player
	 * @param a_gp GamePanel, pannel principal du jeu
	 * @param a_keyH KeyHandler, gestionnaire des touches 
	 */
	public Player(GamePanel a_gp, KeyHandler a_keyH) {
		this.m_gp = a_gp;
		this.m_keyH = a_keyH;
		this.setDefaultValues();
		this.getPlayerImage();
	}
	
	/**
	 * Initialisation des données membres avec des valeurs par défaut
	 */
	protected void setDefaultValues() {
		// TODO: à modifier sûrement
		position = new Vector2(100, 100);
		m_speed = 4;
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
		position = position.addVector(m_keyH.directions);
		/*System.out.println("m_keyH.directions = " + m_keyH.directions);
		System.out.println("position = " + position);*/
	}
	
	/**
	 * Affichage de l'image du joueur dans la fenêtre du jeu
	 * @param a_g2 Graphics2D 
	 */
	public void draw(Graphics2D a_g2) {
		// récupère l'image du joueur
		BufferedImage l_image = m_idleImage;
		// affiche le personnage avec l'image "image", avec les coordonnées x et y, et de taille tileSize (16x16) sans échelle, et 48x48 avec échelle)
		a_g2.drawImage(l_image, position.getX(), position.getY(), m_gp.TILE_SIZE, m_gp.TILE_SIZE, null);
	}
	
	
}
