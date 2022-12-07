package pgdp.sim;

public class Plant implements Cell{

    private long growth;
    public Plant() {
        growth = 0;
    }

    @Override
    public int priority() {
        return 0;
    }

    @Override
    public CellSymbol getSymbol() {
        return CellSymbol.PLANT;
    }

    @Override
    public void tick(Cell[] cells, Cell[] newCells, int width, int height, int x, int y) {
        newCells[x * y] = this;
        int add_growth = RandomGenerator.nextInt(SimConfig.plantMinGrowth, SimConfig.plantMaxGrowth);
        growth += add_growth;
        while (growth >= SimConfig.plantReproductionCost && (this.place(cells, newCells, width, height, x, y))) {
            growth -= SimConfig.plantReproductionCost;
        }
    }
}
