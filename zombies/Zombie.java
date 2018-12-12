package cs2113.zombies;

import cs2113.util.Helper;

import java.awt.*;

public class Zombie extends Creature {

    public Zombie (int x, int y) {
        super(x, y);
    }

    /* Zombie's default movement with no zombies within 10 squares */
    public void moveRandomly(City city) {

        // 20 percent chance of turning
        if (Helper.nextDouble() <= .2) {
            this.changeDirection();
        }
        super.move(city, 1);

    }

    /* Move towards Human if seen within 10 squares of current direction, same as moving forward */
    public void chase(City city) {
        super.move(city, 1);
    }

    /* Loop through all humans and determine whether zombie should chase */
    public boolean shouldChase(City city) {
        for (Human h : city.getHumans()) {
            if (this.isNear(h, city)) {
                return true;
            }
        }
        return false;
    }

    public void draw() {
        ZombieSim.dp.setPenColor(Color.GREEN);
        ZombieSim.dp.drawDot(x, y);
    }
}
