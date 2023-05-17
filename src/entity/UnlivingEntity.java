package entity;

import lib.Vector2;

public class UnlivingEntity extends Entity {
    public UnlivingEntity(int x, int y, int taille, int hauteur) {
        position = new Vector2(x, y);
        width = taille;
        height = hauteur;
    }

}
