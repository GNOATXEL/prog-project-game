package main;

import lib.Vector2;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Gestionnaire d'évènements (touche clavier)
 *
 */
public class KeyHandler implements KeyListener{
	private boolean leftPressed;
	private boolean rightPressed;
	private boolean jumpPressed;

	public KeyHandler() {
		leftPressed = false;
		rightPressed = false;
		jumpPressed = false;
	}

	public boolean isLeftPressed() {
		return leftPressed;
	}

	public boolean isRightPressed() {
		return rightPressed;
	}

	public boolean isJumpPressed() {
		return jumpPressed;
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_Q) {
			leftPressed = true;
		} else if (key == KeyEvent.VK_D) {
			rightPressed = true;
		} else if (key == KeyEvent.VK_SPACE) {
			jumpPressed = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_Q) {
			leftPressed = false;
		} else if (key == KeyEvent.VK_D) {
			rightPressed = false;
		} else if (key == KeyEvent.VK_SPACE) {
			jumpPressed = false;
		}
	}

}
