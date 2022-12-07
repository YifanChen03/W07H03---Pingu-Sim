package pgdp.sim;

public class Hamster extends MovingCell {
    @Override
    public CellSymbol getSymbol() {
        return CellSymbol.HAMSTER;
    }

    @Override
    public boolean canEat(Cell other) {
        if (other.getSymbol() == CellSymbol.PLANT) {
            return true;
        }
        return false;
    }

    @Override
    public int foodConsumption() {
        return SimConfig.hamsterFoodConsumption;
    }

    @Override
    public int consumedFood() {
        return SimConfig.hamsterConsumedFood;
    }

    @Override
    public int reproductionCost() {
        return SimConfig.hamsterReproductionCost;
    }

    @Override
    public int initialFood() {
        return SimConfig.hamsterInitialFood;
    }

    @Override
    public Cell getNew() {
        return new Hamster();
    }
}
