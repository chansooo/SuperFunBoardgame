package Model;

public class MapCellModel implements Cloneable{
    public int index;
    public char cellState;
    public char preDirection;
    public char nextDirection;

    public MapCellModel() {

    }

    public MapCellModel(MapCellModel cell) {
        this.index = cell.index;
        this.cellState = cell.cellState;
        this.preDirection = cell.preDirection;
        this.nextDirection = cell.nextDirection;
    }
}
