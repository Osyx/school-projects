import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

class AddArenaToTournament extends JFrame {

    private final TournamentDAO tournamentDAO;

    /**
     * Create the frame.
     */
    AddArenaToTournament(TournamentDAO newTournamentDAO, Tournament tournament, AllSportTV_GUI allSportTV_gui) {
        this.tournamentDAO = newTournamentDAO;

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 300, 143);

        JPanel panel = new JPanel();
        JLabel lblArena = new JLabel();
        JLabel lblAddAArena = new JLabel("Add an arena to " + tournament.getName() + " (" + tournament.getStartdate() + ").");
        JPanel panel_1 = new JPanel();
        JButton btnCreate = new JButton("Create");
        JPanel contentPane = new JPanel();
        JComboBox<Object> arenaField = new JComboBox<>();

        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));

        lblArena.setHorizontalAlignment(SwingConstants.CENTER);
        lblAddAArena.setHorizontalAlignment(SwingConstants.CENTER);

        contentPane.add(panel, BorderLayout.CENTER);
        contentPane.add(lblAddAArena, BorderLayout.NORTH);
        contentPane.add(panel_1, BorderLayout.SOUTH);

        setContentPane(contentPane);
        panel.add(lblArena);
        panel.add(arenaField);
        panel_1.add(btnCreate);
        panel.setLayout(new GridLayout(2, 2, 0, 0));

        allSportTV_gui.changeDropdown(tournament, arenaField);

        btnCreate.addActionListener(e -> {
            tournamentDAO.linkArenaAndTournament((Arena) arenaField.getSelectedItem(), tournament);
            JOptionPane.showMessageDialog(panel, "Added!\n" + ((Arena) arenaField.getSelectedItem()).getName() + " was added to " + tournament + ".");
            allSportTV_gui.refresh();
        });
    }

}
