package cs2113.zombies;

import cs2113.util.Helper;

// Abstract parent class to Human and Zombie
public abstract class Creature {

    int x;
    int y;
    int direction;


    public Creature(int x, int y) {
        this.x = x;
        this.y = y;
        this.direction = Helper.nextInt(4);

    }
    /** Generates a new random direction for the creature
     *
     */
    protected void changeDirection() {
        this.direction = Helper.nextInt(4);
    }

    /** Reverses direction of the creature
     *  0 -> 2; 1 -> 3; 2 -> 0; 3 -> 1
     */
    protected void reverseDirection() {
        this.direction = (this.direction + 2) % 4;
    }

    /** Returns true if one type of creature sees another type within 10 squares
     * (only in direction it is facing)
     *
     * @param c either a Human or Zombie
     */
    public boolean isNear(Creature c, City city) {
        int xDist = this.x - c.getX();
        int yDist = this.y - c.getY();

        // North: other creature is above (yDist is positive)
        if (this.direction == 0 && xDist == 0 && yDist > 0 && yDist < 10) {
            // Check for wall within space between this creature and other creature
            for (int i = this.y; i > c.getY(); i--) {
                if (city.isWall(this.x, i)) {
                    return false;
                }
            }
            return true;
        }
        // East: other creature is to the right (xDist is negative)
        if (this.direction == 1 && yDist == 0 && xDist > -10 && xDist < 0) {
            for (int i = this.x; i < c.getX(); i++) {
                if (city.isWall(i, this.y)) {
                    return false;
                }
            }
            return true;
        }
        // South: other creature is below (yDist is negative)
        if (this.direction == 2 && xDist == 0 && yDist > -10 && yDist < 0) {
            for (int i = this.y; i < c.getY(); i++) {
                if (city.isWall(this.x, i)) {
                    return false;
                }
            }
            return true;
        }
        // West: other creature is to the left (xDist is positive)
        if (this.direction == 3 && yDist == 0 && xDist > 0 && xDist < 10) {
            for (int i = this.x; i > c.getX(); i--) {
                if (city.isWall(i, this.y)) {
                    return false;
                }
            }
            return true;
        }

        return false;
    }

    /** Returns true if one type of creature is in an adjacent square to another type
     *
     */
    public boolean isAdjacent(Creature c) {
        int xDist = Math.abs(this.x - c.getX());
        int yDist = Math.abs(this.y - c.getY());
        // If the different types of creatures are next to each other or colliding
        // Diagonals don't count
        if (xDist == 0 && yDist <= 1) {
            // Same column, only one row apart or zero
            return true;
        } else if (yDist == 0 && xDist <= 1) {
            // Same row, only one column apart or zero
            return true;
        }
        return false;
    }

    /** Moves creature by numSquares in the direction it is facing (if not wall).
     *
     *  ~the concept of nextX & nextY and checking them is from the phase 1 solution provided on the course website~
     */
    public void move(City city, int numSquares) {
        int nextX = this.x;
        int nextY = this.y;

        // North
        if (this.direction == 0) {
            nextY = this.y - numSquares;
        }
        // East
        else if (this.direction == 1) {
            nextX = this.x + numSquares;
        }
        // South
        else if (this.direction == 2) {
            nextY = this.y + numSquares;
        }
        // West
        else if (this.direction == 3) {
            nextX = this.x - numSquares;
        }
        if (city.isWall(nextX, nextY)) {
            this.changeDirection();
        }
        else {
            this.x = nextX;
            this.y = nextY;
        }
    }

    /** Abstract method that Human and Zombie must implement
     *
     */
    public abstract void moveRandomly(City city);
    public abstract void draw();


    /** Getter methods for x and y coordinates
     *
     */
    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

}
