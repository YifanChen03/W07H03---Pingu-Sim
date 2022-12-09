package pgdp.sim;

import java.util.*;

public class Simulation {
	private Cell[] cells;
	private int width;
	private int height;

	public Simulation(Cell[] cells, int width, int height) {
		this.width = width;
		this.height = height;
		this.cells = cells;
	}

	/** Simuliert einen Tick des Spiels:
	 *  Erst nehmen alle MovingCells Nahrung zu sich,
	 *  dann wird auf allen Cells die tick()-Methode aufgerufen.
	 */
	public void tick() {
		//kopie erstellen
		Cell[] copyOfCells = Arrays.copyOf(cells, cells.length);
		//rufe eat auf
		for (int i = 0; i < cells.length; i++) {
			if (cells[i] instanceof MovingCell) {
				((MovingCell) cells[i]).eat(cells, copyOfCells, width, height, i % width, (i - (i % width)) / width);
			}
		}
		//ursprüngliches array mit null füllen
		cells = Arrays.copyOf(new Cell[cells.length], cells.length);

		//auf jeder Zelle tick ausführen
		for (int i = 0; i < copyOfCells.length; i++) {
			if (copyOfCells[i] != null)
				copyOfCells[i].tick(copyOfCells, cells, width, height, i % width, (i - (i % width)) / width);
		}
	}

	//Getter und Setter für junit tests

	public Cell[] getCells() {
		return cells;
	}

	public void setCells(Cell[] cells) {
		this.cells = cells;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
}
