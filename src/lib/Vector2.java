package lib;

/*
 * This class represents a pair of int seen as a vector, i.e. with their associated operations.
 * Through the program, these may be used to represent position or size.
 */
public class Vector2 {
    private int positionX;
    private int positionY;

    public Vector2() {
        this.positionX = 0;
        this.positionY = 0;
    }

    public Vector2(int x, int y) {
        positionX = x;
        positionY = y;
    }

    public Vector2(Vector2 v) {
        positionX = v.getX();
        positionY = v.getY();
    }

    public int getX() {
        return positionX;
    }

    public void setX(int x) {
        this.positionX = x;
    }

    public int getY() {
        return positionY;
    }

    public void setY(int y) {
        this.positionY = y;
    }

    public boolean equals(Vector2 p) {
        return (this.positionX == p.positionX && this.positionY == p.positionY);
    }

    public void addX(int x) {
        this.positionX += x;
    }

    public void addY(int y) {
        this.positionY += y;
    }

    public Vector2 addVector(Vector2 v) {
        Vector2 newVector = new Vector2(this);
        newVector.addX(v.getX());
        newVector.addY(v.getY());
        return newVector;
    }

    public Vector2 subVector(Vector2 v) {
        return this.addVector(v.reverse());
    }

    public Vector2 scalarMultiplication(int v) {
        return new Vector2(this.getX() * v, this.getY() * v);
    }

    public Vector2 vectorMultiplication(Vector2 v) {
        return new Vector2(this.getX() * v.getX(), this.getY() * v.getY());
    }

    public Vector2 reverse() {
        Vector2 newVector = new Vector2();
        newVector.setX(-this.getX());
        newVector.setY(-this.getY());
        return newVector;
    }

    public Vector2 absoluteValue() {
        Vector2 newVector = new Vector2();
        newVector.setX(Math.abs(getX()));
        newVector.setY(Math.abs(getY()));
        return newVector;
    }

    public String toString() {
        return "(" + this.getX() + ", " + this.getY() + ")";
    }

    /*
     * The Euclidian norm is the usual distance between the vector seen as a
     * position and the position (0,0).
     */
    public int euclidianNorm() {
        int norm = (int) Math.sqrt(this.getX() * this.getX() + this.getY() * this.getY());
        return norm;
    }

    public void euclidianNormalize(int newNorm) {
        int norm = euclidianNorm();
        // Null vectors remain null.
		/*if (Math.abs(norm) < 2 * int.MIN_VALUE)
		{
			return;
		}*/
        int scalingFactor = newNorm / norm;
        this.setX(this.getX() * scalingFactor);
        this.setY(this.getY() * scalingFactor);
    }

    public int distance(Vector2 vector) {
        Vector2 diffVector = vector.subVector(this);
        return diffVector.euclidianNorm();
    }

}
