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
        //berechne index von dieser Zelle
        int ind = x + y * width;
        int m_ind = 0;
        int[] corners = new int[] {0, width - 1, width * (height - 1), width * height};

        //eines der 8 felder um Cell finden
        int num = RandomGenerator.nextInt(7);
        //falls >= 4 eins größer machen sodass index wieder stimmt
        if (num >= 4)
            num++;

        //berechne ob dorthin bewegt werden kann
        //berechne ob es sich um Ecke handelt
        switch(test_for_corner(corners, ind)) {
            case 0:
                if (Arrays.asList(0, 1, 2, 3, 4).contains(ind))
                    return;
            case 1:
                if (Arrays.asList(0, 1, 2, 5, 8).contains(ind))
                    return;
            case 2:
                if (Arrays.asList(0, 3, 6, 7, 8).contains(ind))
                    return;
            case 3:
                if (Arrays.asList(2, 5, 6, 7, 8).contains(ind))
                    return;
        }

        //berechne ob this sich am Rand befindet und move zu einem feld außerhalb führt
        switch(test_for_border(width, height, ind)) {
            case 0:
                if (Arrays.asList(0, 3, 6).contains(ind))
                    return;
            case 1:
                if (Arrays.asList(2, 5, 8).contains(ind))
                    return;
            case 2:
                if (Arrays.asList(0, 1, 2).contains(ind))
                    return;
            case 3:
                if (Arrays.asList(6, 7, 8).contains(ind))
                    return;
        }

        //sonst move
        switch(num) {
            case 0:
                m_ind = ind - width - 1;
                break;
            case 1:
                m_ind = ind - width;
                break;
            case 2:
                m_ind = ind - width + 1;
                break;
            case 3:
                m_ind = ind - 1;
                break;
            case 5:
                m_ind = ind + 1;
                break;
            case 6:
                m_ind = ind + width - 1;
                break;
            case 7:
                m_ind = ind + width;
                break;
            case 8:
                m_ind = ind + width + 1;
                break;
        }
        //prüfe ob cell frei
        if (cells[m_ind] == null && newCells[m_ind] == null) {
            newCells[m_ind] = this;
            cells[ind] = null;
        } else {
            newCells[ind] = this;
        }
    }
    public void eat (Cell[] cells, Cell[] newCells,int width, int height, int x, int y) {
        int ind = x + y * width;
        int m_ind = 0;
        int[] forbidden_cells = new int[0];
        ArrayList<Integer> can_be_eaten = new ArrayList<>();

        //teste ob corner
        switch (test_for_corner(new int[] {0, width - 1, width * (height - 1), width * height}, ind)) {
            case 0:
                forbidden_cells = new int[] {0, 1, 2, 3, 4};
                break;
            case 1:
                forbidden_cells = new int[] {0, 1, 2, 5, 8};
                break;
            case 2:
                forbidden_cells = new int[] {0, 3, 6, 7, 8};
                break;
            case 3:
                forbidden_cells = new int[] {2, 5, 6, 7, 8};
                break;
            default:
                //teste für ränder
                switch (test_for_border(width, height, ind)) {
                    case 0:
                        forbidden_cells = new int[] {0, 3, 6};
                        break;
                    case 1:
                        forbidden_cells = new int[] {2, 5, 8};
                        break;
                    case 2:
                        forbidden_cells = new int[] {0, 1, 2};
                        break;
                    case 3:
                        forbidden_cells = new int[] {6, 7, 8};
                        break;
                }
        }

        //essbare zellen im umfeld berechnen
        for (int i = 0; i < 8; i++) {
            int ni = i;
            if (ni >= 4)
                ni++;
            switch(ni) {
                case 0:
                    m_ind = ind - width - 1;
                    break;
                case 1:
                    m_ind = ind - width;
                    break;
                case 2:
                    m_ind = ind - width + 1;
                    break;
                case 3:
                    m_ind = ind - 1;
                    break;
                case 5:
                    m_ind = ind + 1;
                    break;
                case 6:
                    m_ind = ind + width - 1;
                    break;
                case 7:
                    m_ind = ind + width;
                    break;
                case 8:
                    m_ind = ind + width + 1;
                    break;
            }
            if (!Arrays.asList(forbidden_cells).contains(ni) && canEat(cells[m_ind]))
                can_be_eaten.add(m_ind);
        }
        //essbare Zellen essen
        for (int i = 0; i < can_be_eaten.size(); i++) {
            newCells[can_be_eaten.get(i)] = null;
            food += consumedFood();
        }
    }
    public void tick(Cell[] cells, Cell[] newCells,int width, int height, int x, int y) {
        //TODO
        if (food >= reproductionCost()) {
            if (getNew().place(cells, newCells, width, height, x, y)) {
                food = initialFood();
            }
        }
        food -= foodConsumption();
        if (food >= 0)
            move(cells, newCells, width, height, x, y);
    }
    public int priority() { //implementiert
        return 1;
    }

    public int test_for_corner(int[] corners, int ind) {
        if (Arrays.asList(corners).contains(ind)) {
            int n_edge = Arrays.asList(corners).indexOf(ind);
            return n_edge;
        }
        return -1;
    }

    public int test_for_border(int width, int height, int ind) {
        if (ind % width == 0) {
            //linker Rand
            return 0;
        } else if ((ind + 1) % width == 0) {
            //rechter Rand
            return 1;
        } else if (ind < width) {
            //oberer Rand
            return 2;
        } else if (ind >= width * (height - 1) && ind < width + height) {
            //unterer Rand
            return 3;
        }
        return -1;
    }
}
