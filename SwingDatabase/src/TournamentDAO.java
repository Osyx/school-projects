import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class TournamentDAO {

    private static Connection conn;

    TournamentDAO() {
        String URL = "jdbc:ucanaccess://src/TheProject.accdb";
        String driver = "net.ucanaccess.jdbc.UcanaccessDriver";
        String userID = "";
        String password = "";

        try {
            Class.forName(driver);

            conn = DriverManager.getConnection(URL, userID, password);
            conn.setAutoCommit(false);

            System.out.println("TournamentDAO connected to " + URL + " using " + driver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Tournament convertRowToTournament(ResultSet resultSet) throws SQLException {

        int id = resultSet.getInt("turneringsID");
        String name = resultSet.getString("namn");
        Date startdate = resultSet.getDate("start");
        Date enddate = resultSet.getDate("slut");

        return new Tournament(id, name, startdate, enddate);
    }

    public List<Tournament> getAllTournaments() {
        List<Tournament> list = new ArrayList<>();

        Statement myStmt;
        ResultSet myRs;

        try {
            myStmt = conn.createStatement();
            myRs = myStmt.executeQuery("SELECT * FROM Turnering ORDER BY turneringsID ASC");

            while (myRs.next()) {
                list.add(convertRowToTournament(myRs));
            }

            myStmt.close();
            myRs.close();

            return list;

        } catch (SQLException e) {
            System.out.println("Something went wrong :(\nSQLException error code: " + e.getErrorCode());
        }
        return null;
    }

    public List<Arena> searchTournamentArenas(Tournament tournament) {
        List<Arena> list = new ArrayList<>();

        PreparedStatement myStmt;
        ResultSet myRs;

        try {
            myStmt = conn.prepareStatement("SELECT DISTINCT arenanamn AS namn, plats, storlek, byggdatum, aktiv FROM Turneringsarena, Turnering, Arena WHERE Turneringsarena.arenanamn = Arena.namn AND Turneringsarena.turneringsID = ? ORDER BY arenanamn ASC");
            myStmt.setInt(1, tournament.getID());
            myRs = myStmt.executeQuery();

            while (myRs.next()) {
                list.add(ArenaDAO.convertRowToArena(myRs));
            }

            myStmt.close();
            myRs.close();

            return list;

        } catch (SQLException e) {
            System.out.println("Something went wrong :(\nSQLException error code: " + e.getErrorCode());
        }
        return null;
    }

    public void linkArenaAndTournament(Arena arena, Tournament tournament) {

        PreparedStatement myStmt;

        try {
            myStmt = conn.prepareStatement("INSERT INTO Turneringsarena VALUES (?, ?)");
            myStmt.setInt(1, tournament.getID());
            myStmt.setString(2, arena.getName());
            myStmt.executeUpdate();

            myStmt.close();
            conn.commit();

        } catch (SQLException e) {
            System.out.println("Something went wrong :(\nSQLException error code: " + e.getErrorCode());
        }
    }
}
