import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

class AllSportTV_GUI extends JFrame {

    //private final JTextField arenaNameTextField;
    private final JTable table;
    private final ArenaDAO arenaDAO;
    private final TournamentDAO tournamentDAO;
    private JButton btnSearch;

    /**
     * Create the frame.
     */
    private AllSportTV_GUI() {

        this.arenaDAO = new ArenaDAO();
        this.tournamentDAO = new TournamentDAO();

        setTitle("AllSportTV");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(100, 100, 638, 306);

        JMenuBar menuBar = new JMenuBar();
        JMenu mnSearch = new JMenu("Search");
        JCheckBoxMenuItem chckbxmntmArenas = new JCheckBoxMenuItem("Tournaments at");
        JCheckBoxMenuItem chckbxmntmTournaments = new JCheckBoxMenuItem("Hosting arenas");
        JMenu mnCreate = new JMenu("Create");
        JMenuItem mntmCreateNewArena = new JMenuItem("Create new arena");
        JPanel panel = new JPanel();
        FlowLayout flowLayout = (FlowLayout) panel.getLayout();
        JLabel lblEnterText = new JLabel("Show tournaments taking place at:");
        JButton btnSearch = new JButton("Search");
        JScrollPane scrollPane = new JScrollPane();
        JComboBox<Object> comboBox = new JComboBox<>();
        JButton btnAddAArena = new JButton("Add a arena to this tournament");
        Component horizontalGlue = Box.createHorizontalGlue();
        JPanel contentPane = new JPanel();

        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        contentPane.add(scrollPane, BorderLayout.CENTER);
        contentPane.add(panel, BorderLayout.NORTH);
        table = new JTable();
        this.btnSearch = btnSearch;

        setContentPane(contentPane);
        setJMenuBar(menuBar);
        scrollPane.setViewportView(table);
        chckbxmntmArenas.setSelected(true);
        flowLayout.setAlignment(FlowLayout.LEFT);
        btnAddAArena.setVisible(false);

        panel.add(lblEnterText);
        panel.add(comboBox);
        panel.add(btnSearch);
        panel.add(horizontalGlue);
        panel.add(btnAddAArena);
        menuBar.add(mnSearch);
        mnSearch.add(chckbxmntmArenas);
        mnSearch.add(chckbxmntmTournaments);
        menuBar.add(mnCreate);
        mnCreate.add(mntmCreateNewArena);

        chckbxmntmArenas.addActionListener(e -> {
            if (!chckbxmntmArenas.getState()) {
                chckbxmntmTournaments.setState(true);
                lblEnterText.setText("Show arenas hosting:");
                btnAddAArena.setVisible(true);
                changeDropdown("Tournament", comboBox);
            } else {
                chckbxmntmTournaments.setState(false);
                lblEnterText.setText("Show tournaments taking place at:");
                btnAddAArena.setVisible(false);
                changeDropdown("Arena", comboBox);
            }
        });

        chckbxmntmTournaments.addActionListener(e -> {
            if (!chckbxmntmTournaments.getState()) {
                chckbxmntmArenas.setState(true);
                lblEnterText.setText("Show tournaments taking place at:");
                btnAddAArena.setVisible(false);
                changeDropdown("Arena", comboBox);
            } else {
                chckbxmntmArenas.setState(false);
                lblEnterText.setText("Show arenas hosting:");
                btnAddAArena.setVisible(true);
                changeDropdown("Tournament", comboBox);
            }
        });


        btnSearch.addActionListener(e -> {
            if (lblEnterText.getText().contains("arenas")) {
                List<Arena> tournamentArenas = tournamentDAO.searchTournamentArenas((Tournament) comboBox.getSelectedItem());
                ArenaTableModel arenaTableModel = new ArenaTableModel(tournamentArenas);
                table.setModel(arenaTableModel);

            } else {
                List<Tournament> searchArenaTournaments = arenaDAO.searchArenaTournaments((Arena) comboBox.getSelectedItem());
                TournamentTableModel tournamentTableModel = new TournamentTableModel(searchArenaTournaments);
                table.setModel(tournamentTableModel);
            }
        });

        btnAddAArena.addActionListener(e -> createNewAddArenaToTournament((Tournament) comboBox.getSelectedItem()));

        mntmCreateNewArena.addActionListener(e -> createNewCreateArena());

        List<Arena> allArenas = arenaDAO.getAllArenas();
        Arena[] arenaArr = new Arena[allArenas.size()];
        int i = 0;
        for (Arena a : allArenas) {
            arenaArr[i] = a;
            i++;
        }
        comboBox.setModel(new DefaultComboBoxModel<>(arenaArr));
    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        EventQueue.invokeLater(() -> {
            try {
                AllSportTV_GUI frame = new AllSportTV_GUI();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void createNewCreateArena() {
        EventQueue.invokeLater(() -> {
            try {
                CreateArena frame = new CreateArena(arenaDAO);
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void createNewAddArenaToTournament(Tournament tournament) {
        EventQueue.invokeLater(() -> {
            try {
                AddArenaToTournament frame = new AddArenaToTournament(tournamentDAO, tournament, this);
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void changeDropdown(String s, JComboBox<Object> comboBox) {
        if(s.contains("Tournament")) {
            List<Tournament> allTournaments = tournamentDAO.getAllTournaments();
            comboBox.setModel(new DefaultComboBoxModel<>(allTournaments.toArray(new Tournament[allTournaments.size()])));
        } else {
            List<Arena> allArenas = arenaDAO.getAllArenas();
            comboBox.setModel(new DefaultComboBoxModel<>(allArenas.toArray(new Arena[allArenas.size()])));
        }
    }

    void changeDropdown(Object object, JComboBox<Object> comboBox) {
        if(object instanceof Arena) {
            List<Tournament> allTournaments = tournamentDAO.getAllTournaments();
            comboBox.setModel(new DefaultComboBoxModel<>(allTournaments.toArray(new Tournament[allTournaments.size()])));
        } else {
            List<Arena> allArenas = arenaDAO.searchCurrentTournamentArenas((Tournament) object);
            comboBox.setModel(new DefaultComboBoxModel<>(allArenas.toArray(new Arena[allArenas.size()])));
        }
    }

    void refresh() {
        btnSearch.doClick();
    }

}
