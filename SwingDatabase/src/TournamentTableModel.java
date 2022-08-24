import javax.swing.table.AbstractTableModel;
import java.util.List;

class TournamentTableModel extends AbstractTableModel {

    private static final int NAME_COL = 0;
    private static final int START_COL = 1;
    private static final int END_COL = 2;

    private final String[] columnNames = {"Name", "Start date", "End date"};
    private final List<Tournament> tournaments;

    public TournamentTableModel(List<Tournament> theTournaments) {
        tournaments = theTournaments;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public int getRowCount() {
        return tournaments.size();
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public Object getValueAt(int row, int col) {

        Tournament tempTournament = tournaments.get(row);

        switch (col) {
            case NAME_COL:
                return tempTournament.getName();
            case START_COL:
                return tempTournament.getStartdate();
            case END_COL:
                return tempTournament.getEnddate();
            default:
                return tempTournament.getName();
        }
    }

    @Override
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }
}
