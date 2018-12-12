package cs2113.zombies;

import cs2113.util.Helper;

import java.awt.Color;
import java.lang.reflect.Array;
import java.util.ArrayList;


public class City {

	/** walls is a 2D array with an entry for each space in the city.
	 *  If walls[x][y] is true, that means there is a wall in the space.
	 *  else the space is free. Humans should never go into spaces that
	 *  have a wall.
	 */
	private boolean walls[][];
	private int width, height;
	private ArrayList<Human> humanList = new ArrayList<>();
	private ArrayList<Zombie> zombieList = new ArrayList<>();

	/**
	 * Create a new City and fill it with buildings and people.
	 * @param w width of city
	 * @param h height of city
	 * @param numB number of buildings
	 * @param numP number of people
	 */
	public City(int w, int h, int numB, int numP) {
		width = w;
		height = h;
		walls = new boolean[w][h];

		randomBuildings(numB);
		populate(numP);
	}

	// Constructor that only takes in 3 args and calls special feature buildSmile() instead of randomBuildings()
	public City(int w, int h, int numP) {
	    width = w;
        height = h;
        walls = new boolean[w][h];

        buildSmile();
        populate(numP);
    }

	// Check if there is wall at location (x,y). Treats out of bounds as a wall.
	public boolean isWall(int x, int y) {
		if ((x < 0) || (x >= width - 1) || (y < 0) || (y >= height - 1)) {
			return true;
		}
		else {
			return walls[x][y];
		}
	}


	/**
	 * Generates numPeople random people distributed throughout the city.
	 * People must not be placed inside walls!
	 *
	 * @param numPeople the number of people to generate
	 */
	private void populate(int numPeople) {
		// Generate numPeople new humans randomly placed around the city.
		for(int i = 0; i < numPeople; i++) {

			int x = Helper.nextInt(width);
			int y = Helper.nextInt(height);

			// Keep generating new coordinates until it is not a wall.
			while (walls[x][y]) {
				x = Helper.nextInt(width);
				y = Helper.nextInt(height);
			}
			humanList.add(new Human(x,y));

		}

		// Generate a single zombie in a random location.
		int x = Helper.nextInt(width);
		int y = Helper.nextInt(height);
		while (walls[x][y]) {
			x = Helper.nextInt(width);
			y = Helper.nextInt(height);
		}
		zombieList.add(new Zombie(x,y));

	}


	/**
	 * Generates a random set of numB buildings.
	 *
	 * @param numB the number of buildings to generate
	 */
	private void randomBuildings(int numB) {
		/* Create buildings of a reasonable size for this map */
		int bldgMaxSize = width/6;
		int bldgMinSize = width/50;

		/* Produce a bunch of random rectangles and fill in the walls array */
		for(int i=0; i < numB; i++) {
			int tx, ty, tw, th;
			tx = Helper.nextInt(width);
			ty = Helper.nextInt(height);
			tw = Helper.nextInt(bldgMaxSize) + bldgMinSize;
			th = Helper.nextInt(bldgMaxSize) + bldgMinSize;

			for(int r = ty; r < ty + th; r++) {
				if(r >= height)
					continue;
				for(int c = tx; c < tx + tw; c++) {
					if(c >= width)
						break;
					walls[c][r] = true;
				}
			}
		}
	}

    // Turn infected humans into zombies using array list modification
	public void zombify() {
		ArrayList<Human> eatenHumans = new ArrayList<>();
		for (Human h : humanList) {
			if (h.isEaten(this)) {
				// Add to temp array list of dead people
				eatenHumans.add(h);
			}
		}

		humanList.removeAll(eatenHumans);
		// Turn all of the eaten humans into zombies
		for (Human human : eatenHumans) {
			zombieList.add(new Zombie(human.getX(), human.getY()));
		}

	}

	/**
	 * Updates the state of the city for a time step.
	 */
	public void update() {
        // Move humans, zombies
        for (Human h : humanList) {
            if (h.shouldRun(this)) {
                h.runAway(this);
            }
            else {
			h.moveRandomly(this);
            }
		}
		for (Zombie z : zombieList) {
		    if (z.shouldChase(this)) {
		        z.chase(this);
            }
            else {
			z.moveRandomly(this);
            }
		}

		// Handle infection process
		this.zombify();
    }

	/**
	 * Draw the buildings and all humans.
	 */
	public void draw() {
		/* Clear the screen */
		ZombieSim.dp.clear(Color.black);

		drawWalls();

		for (Human h: humanList) {
			h.draw();
		}

		for (Zombie z: zombieList) {
			z.draw();
		}
	}

	/**
	 * Draw the buildings.
	 * First set the color for drawing, then draw a dot at each space
	 * where there is a wall.
	 */
	private void drawWalls() {
		ZombieSim.dp.setPenColor(Color.DARK_GRAY);
		for(int r = 0; r < height; r++)
		{
			for(int c = 0; c < width; c++)
			{
				if(walls[c][r])
				{
					ZombieSim.dp.drawDot(c, r);
				}
			}
		}
	}

	public ArrayList<Human> getHumans() {
	    return this.humanList;
    }

    public ArrayList<Zombie> getZombies() {
	    return this.zombieList;
    }

	// Add zombie on click
	public void addZombie(int x, int y) {
		if (!isWall(x, y)) {
			zombieList.add(new Zombie(x, y));
		}
	}

	// Generate buildings in shape of smiley face
    public void buildSmile() {

		// Left eye
		for(int r = 35; r < 80; r++) {
			for(int c = 35; c < 80; c++) {
				walls[c][r] = true;
			}
		}
		// Right eye
		for(int r = 35; r < 80; r++) {
			for(int c = 120; c < 165; c++) {
				walls[c][r] = true;
			}
		}
		// Right smile
		for(int r = 130; r < 180; r++) {
			for(int c = 160; c < 180; c++) {
				walls[c][r] = true;
			}
		}
		// Middle smile
		for(int r = 160; r < 180; r++) {
			for(int c = 20; c < 160; c++) {
				walls[c][r] = true;
			}
		}
		// Left smile
		for(int r = 130; r < 180; r++) {
			for(int c = 20; c < 40; c++) {
				walls[c][r] = true;
			}
		}
	}

}
