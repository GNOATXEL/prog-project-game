package main;

import lib.Vector2;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Gestionnaire d'évènements (touche clavier)
 */
public class KeyHandler implements KeyListener {
    public Vector2 directions = new Vector2(0, 0);
    public boolean is_jumping = false;
    public boolean takes_damage = false;

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
            case 81 -> directions.setX(-1);
            // D
            case 68 -> directions.setX(1);
            // espace
            case 32 -> {
                is_jumping = true;
                directions.setY(-1);
            }
            //bas
            case 83 -> directions.setY(1);
            case KeyEvent.VK_V -> takes_damage = true;

        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        // récupère le code du bouton appuyé
        int code = e.getKeyCode();

        switch (code) {
            // Q
            case 81 -> directions.setX(0);
            // D
            case 68 -> directions.setX(0);
            // espace
            case 32 -> {
                directions.setY(0);
                is_jumping = false;
            }
            //bas
            case 83 -> directions.setY(0);
            case KeyEvent.VK_V -> takes_damage = false;

        }
    }

}
