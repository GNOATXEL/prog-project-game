package main;

import lib.Vector2;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Gestionnaire d'évènements (touche clavier)
 *
 */
public class KeyHandler implements KeyListener{
	public Vector2 directions = new Vector2(0, 0);

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// récupère le code du bouton appuyé
		int code = e.getKeyCode();
//		System.out.println(code);

		switch (code) {
			// Q
			case 81 -> directions.setX(-10);
			// D
			case 68 -> directions.setX(10);
			// espace
			case 32 -> directions.setY(-10);
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// récupère le code du bouton appuyé
		int code = e.getKeyCode();

		switch (code) {
			// Q
			case 81: directions.setX(0);
				// D
			case 68: directions.setX(0);
				// espace
			case 32: directions.setY(0);
		}
	}

}
