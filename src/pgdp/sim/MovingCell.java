package pgdp.sim;

import java.util.*;

public abstract class MovingCell implements Cell {
    private long food;
    public MovingCell() {
        food = initialFood();
    }

    public abstract boolean canEat(Cell other);
    public abstract int foodConsumption();
    public abstract int consumedFood();
    public abstract int reproductionCost();
    public abstract int initialFood();
    public abstract Cell getNew();
    public void move(Cell[] cells, Cell[] newCells,int width, int height, int x, int y) {
        //TODO
        //eiens der 8 felder um Cell finden
        int num = RandomGenerator.nextInt(7);
        //falls >= 4 eins größer machen sodass index wieder stimmt
        if (num >= 4) {
            num++;
        }

        //falls innerhalb des Spielfelds
        //berechne position im ganzen Spielfeld
        int p_change = num - 4;
        if (place(cells, newCells, width, height, x, y)) {

        }

        /*System.out.println("Cells: " + Arrays.toString(cells));
        System.out.println("newCells: " + Arrays.toString(newCells));
        System.out.println("width: " + width);
        System.out.println("height: " + height);
        System.out.println("x: " + x);
        System.out.println("y " + y);*/
    }
    public void eat (Cell[] cells, Cell[] newCells,int width, int height, int x, int y) {
        //TODO
    }
    public void tick(Cell[] cells, Cell[] newCells,int width, int height, int x, int y) {
        //TODO
    }
    public int priority() { //implementiert
        return 1;
    }
}
