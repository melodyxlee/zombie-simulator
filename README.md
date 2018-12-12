# Zombie Simulator - Design Document
#### UML Diagram
<img src= "Project 2 Zombies UML Diagram FINAL (for real this time) (1).png">

#### Code Components
- *ZombieSim:* This class handles the GUI and the "game loop" that continuously updates the simulation by calling `world.update()` and `world.draw`. It implements the `KeyListener` and `MouseListener` interfaces to be able to repsond to keyboard and mouse events such as pressing space bar to reset the simulation and clicking within the window to add a zombie if the mouse location is valid. 
- *City:* The City class handles the array lists of humans and zombies, the boolean 2d array `walls[][]`, and calls methods on creatures so that they move and behave appropriately. 
    - `isWall` checks if there is a wall at a specific pair of x and y coordinates, and treats out of bounds as a wall so that it returns false for x and y values that go beyond the size of the `walls` array. 
    - `populate` generates new humans and makes sure they are not placed in buildings by continuing to come up with new x and y values until they are a valid location. Once instantiated, a human is added to the `humanList` array list that keeps track of all of the humans in the city. The method also instantiates one zombie and places it in the `zombieList` array list. 
    - `update` loops through the humans in `humanList` and zombies in `zombieList`. It calls methods from either the Human, Zombie, or Creature class to check for different situations such as whether a human should run away or if a human should become a zombie, etc and then makes the creature move accordingly. It also calls `zombify` to modify the human and zombie array lists when a human is infected.
    - `zombify` handles the infection process during which a human who has been eaten is added to another array list `eatenHumans`, all of the eaten humans are removed from `humanList`, and a zombie with the dead human's location is created and added to `zombieList` for every eaten human.
- *Creature:* This is an abstract class that is the parent class to Human and Zombie because these two classes are very similar and need some of the same fields and methods. It is abstract because there is no need to ever instantiate a Creature - only a Human or a Zombie. 
    - Both humans and zombies need `x`,`y`,`direction` that can be accessed to know the location of the creature and which way it is facing. `changeDirection` is also needed by both to randomly pick another direction to face. 
    - `isNear` calculates the difference in x and y distances of the current creature to another type of creature that is passed in as an argument and checks whether the difference is within 10 of the current creature, depending on the direction. For each direction, it loops through every space between the current creature and the other creature and returns false if the space is a wall. This is to ensure that the creature cannot "see" through walls. 
    - `isAdjacent` uses similar logic as `isNear` and returns true if the x or y distance differences is less than or equal to 1, meaning that the other creature is next to the current creature (not counting diagonals).
    - `move` simply moves the creature in the direction it is facing, and takes in the number of spaces it should move per call. It has `nextX` and `nextY` that it uses to check whether the space the human wants to move to is valid, i.e. there is no wall and it is not out of bounds.
    - The abstract classes `moveRandomly` and `draw` are implemented differently depending on the type of creature, hence they are labelled as abstract and must be implemented by child classes. 
- *Human:* 
    - `moveRandomly` moves the human normally using Creature's `move`, but with a 10 percent chance that it changes direction. 
    - `shouldRun`returns true if `isNear` is true, which means that a human sees a zombie within 10 spaces of the direction it is facing. The method loops through `zombieList` and checks the human against every zombie in the array list. 
    - `runAway` implements `move`, but direction is changed to the opposite of the current direction and the human jumps 2 spaces instead of just one. `runAway` is called in City's update method after checking if the human should run or not. 
    - `isEaten` is a boolean method that checks with the `isAdjacent` method in Creature and returns true if the human is adjacent to a zombie. 
- *Zombie:*
    - `moveRandomly` moves the zombie normally using Creature's `move`, but with a 20 percent chance that it changes direction. 
    - `shouldChase` is a boolean method that loops through `zombieList` and calls Creature's `isNear` method. If `isNear` returns true, then that means a human is within 10 spaces of the zombie in the direction it is facing, so `shouldChase` returns true. 
    - `chase` makes the zombie move in the direction it is facing (just calls `move` from Creature class). It is called after the City class checks if a zombie should chase a human. 
    
#### Special Feature
My cool extra feature is when the user presses the 's' key, the simulator resets and the buildings regenerate to form a smiley face :)

#### Ideas Incorporated from Phase 1 Solution
From the phase 1 solution, I implemented the idea of having `nextX` and `nextY` be another set of x and y coordinates in Creature's `move()` method to represent the potential next location of a human in order to check if it can validly move there. 
