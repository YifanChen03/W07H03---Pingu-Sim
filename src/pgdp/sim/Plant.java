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
        int add_growth;
        newCells[x + y * width] = this;
        add_growth = RandomGenerator.nextInt(SimConfig.plantMinGrowth, SimConfig.plantMaxGrowth);
        growth += add_growth;
        while (growth >= SimConfig.plantReproductionCost) {
            if (new Plant().place(cells, newCells, width, height, x, y)) {
                growth -= SimConfig.plantReproductionCost;
            } else {
                break;
            }
        }
    }
}
