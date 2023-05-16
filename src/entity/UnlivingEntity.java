package entity;

import lib.Vector2;

public class UnlivingEntity extends Entity {
    public boolean pickable;
    public UnlivingEntity(int x, int y, int taille, int hauteur, boolean pick) {
        position = new Vector2(x, y);
        width = taille;
        height = hauteur;
        pickable = pick;
    }
}
