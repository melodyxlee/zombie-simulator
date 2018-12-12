package cs2113.zombies;

import cs2113.util.Helper;

import java.awt.*;

public class Human extends Creature{

    // Constructor inherited from Creature
    public Human(int x, int y) {
        super(x, y);
    }

    /* Human's default movement with no zombies within 10 squares */
    public void moveRandomly(City city) {

        // 10 percent chance of turning
        if (Helper.nextDouble() <= .1) {
            this.changeDirection();
        }
        super.move(city, 1);
    }

    /* Human's movement if it sees a zombie within 10 squares */
    public void runAway(City city) {
        this.reverseDirection();
        super.move(city, 2);
    }

    /* Checks against every zombie and returns true if the human should run away */
    public boolean shouldRun(City city) {
        for (Zombie z : city.getZombies()) {
            if (this.isNear(z, city)) {
                return true;
            }
        }
        return false;
    }

    /* Checks against every zombie and returns true if the human gets infected */
    public boolean isEaten(City city) {
        for (Zombie z : city.getZombies()) {
            if (this.isAdjacent(z)) {
                return true;
            }
        }
        return false;
    }


    public void draw () {
        ZombieSim.dp.setPenColor(Color.WHITE);
        ZombieSim.dp.drawDot(x, y);
    }


}
