package pgdp.sim;

public class Wolf extends MovingCell{
    @Override
    public CellSymbol getSymbol() {
        return CellSymbol.WOLF;
    }

    @Override
    public boolean canEat(Cell other) {
        if (other != null && other.getSymbol() == CellSymbol.HAMSTER) {
            return true;
        }
        return false;
    }

    @Override
    public int foodConsumption() {
        return SimConfig.wolfFoodConsumption;
    }

    @Override
    public int consumedFood() {
        return SimConfig.wolfConsumedFood;
    }

    @Override
    public int reproductionCost() {
        return SimConfig.wolfReproductionCost;
    }

    @Override
    public int initialFood() {
        return SimConfig.wolfInitialFood;
    }

    @Override
    public Cell getNew() {
        return new Wolf();
    }
}
