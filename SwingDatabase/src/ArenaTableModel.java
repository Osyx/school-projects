import javax.swing.table.AbstractTableModel;
import java.util.List;

class ArenaTableModel extends AbstractTableModel {

    private static final int NAME_COL = 0;
    private static final int LOCATION_COL = 1;
    private static final int SIZE_COL = 2;
    private static final int BUILD_DATE_COL = 3;
    private static final int ACTIVE_COL = 4;

    private final String[] columnNames = {"Name", "Location", "Size", "Build Date", "Active"};
    private final List<Arena> arenas;

    public ArenaTableModel(List<Arena> theArenas) {
        arenas = theArenas;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public int getRowCount() {
        return arenas.size();
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public Object getValueAt(int row, int col) {

        Arena tempArena = arenas.get(row);

        switch (col) {
            case NAME_COL:
                return tempArena.getName();
            case LOCATION_COL:
                return tempArena.getPlace();
            case SIZE_COL:
                return tempArena.getSize();
            case BUILD_DATE_COL:
                return tempArena.getBuilddate();
            case ACTIVE_COL:
                return tempArena.isActive();
            default:
                return tempArena.getName();
        }
    }

    @Override
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }
}
