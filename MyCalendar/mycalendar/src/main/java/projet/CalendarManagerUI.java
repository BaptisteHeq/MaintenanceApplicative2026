package projet;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;

public final class CalendarManagerUI {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private final CalendarManager manager;

    private final JFrame frame;
    private final JComboBox<String> typeBox;
    private final JTextField ownerField;
    private final JTextField titleField;
    private final JTextField dateField;
    private final JTextField durationField;
    private final JTextField locationField;
    private final JTextField participantsField;
    private final JTextField frequencyField;
    private final JTextField deleteIdField;
    private final DefaultTableModel tableModel;

    private CalendarManagerUI() {
        this.manager = new CalendarManager();
        this.frame = new JFrame("MyCalendar - UI minimale");

        this.typeBox = new JComboBox<>(new String[] {
                TypeEvenement.RDV_PERSONNEL,
                TypeEvenement.REUNION,
                TypeEvenement.PERIODIQUE
        });
        this.ownerField = new JTextField("Alice");
        this.titleField = new JTextField();
        this.dateField = new JTextField("2026-03-20 10:00");
        this.durationField = new JTextField("30");
        this.locationField = new JTextField();
        this.participantsField = new JTextField();
        this.frequencyField = new JTextField("0");
        this.deleteIdField = new JTextField();

        this.tableModel = new DefaultTableModel(
                new String[] { "ID", "Type", "Titre", "Proprietaire", "Date", "Description" },
                0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        buildUi();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CalendarManagerUI().show());
    }

    private void show() {
        frame.setVisible(true);
        refreshTable();
    }

    private void buildUi() {
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(1050, 620);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridLayout(0, 2, 8, 8));
        formPanel.setBorder(BorderFactory.createTitledBorder("Nouvel evenement"));
        formPanel.add(new JLabel("Type"));
        formPanel.add(typeBox);
        formPanel.add(new JLabel("Proprietaire"));
        formPanel.add(ownerField);
        formPanel.add(new JLabel("Titre"));
        formPanel.add(titleField);
        formPanel.add(new JLabel("Date (yyyy-MM-dd HH:mm)"));
        formPanel.add(dateField);
        formPanel.add(new JLabel("Duree minutes"));
        formPanel.add(durationField);
        formPanel.add(new JLabel("Lieu (reunion)"));
        formPanel.add(locationField);
        formPanel.add(new JLabel("Participants (reunion)"));
        formPanel.add(participantsField);
        formPanel.add(new JLabel("Frequence jours (periodique)"));
        formPanel.add(frequencyField);

        JButton addButton = new JButton("Ajouter");
        addButton.addActionListener(e -> addEvent());

        JButton refreshButton = new JButton("Rafraichir");
        refreshButton.addActionListener(e -> refreshTable());

        JPanel actionsPanel = new JPanel(new GridLayout(1, 0, 8, 8));
        actionsPanel.add(addButton);
        actionsPanel.add(refreshButton);

        JPanel topPanel = new JPanel(new BorderLayout(8, 8));
        topPanel.add(formPanel, BorderLayout.CENTER);
        topPanel.add(actionsPanel, BorderLayout.SOUTH);

        JTable table = new JTable(tableModel);
        JScrollPane tableScroll = new JScrollPane(table);

        JPanel deletePanel = new JPanel(new GridLayout(1, 0, 8, 8));
        deletePanel.setBorder(BorderFactory.createTitledBorder("Suppression"));
        deletePanel.add(new JLabel("ID evenement"));
        deletePanel.add(deleteIdField);
        JButton deleteButton = new JButton("Supprimer");
        deleteButton.addActionListener(e -> deleteEvent());
        deletePanel.add(deleteButton);

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(tableScroll, BorderLayout.CENTER);
        frame.add(deletePanel, BorderLayout.SOUTH);
    }

    private void addEvent() {
        try {
            String type = String.valueOf(typeBox.getSelectedItem());
            LocalDateTime date = LocalDateTime.parse(dateField.getText().trim(), DATE_FORMAT);

            manager.ajouterEvent(
                    new TypeEvenement(type),
                    new TitreEvenement(titleField.getText().trim()),
                    new ProprietaireEvenement(ownerField.getText().trim()),
                    new DateHeureEvenement(date),
                    new DureeEvenement(parseIntOrDefault(durationField.getText(), 0)),
                    new LieuEvenement(locationField.getText().trim()),
                    new ParticipantsEvenement(participantsField.getText().trim()),
                    new FrequenceJours(parseIntOrDefault(frequencyField.getText(), 0)));

            refreshTable();
            clearAfterAdd();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Erreur de saisie: " + ex.getMessage(), "Ajout impossible",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteEvent() {
        try {
            boolean deleted = manager.supprimerEvent(new EventId(deleteIdField.getText().trim()));
            if (!deleted) {
                JOptionPane.showMessageDialog(frame, "Aucun evenement avec cet ID.", "Info",
                        JOptionPane.INFORMATION_MESSAGE);
            }
            refreshTable();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Suppression impossible: " + ex.getMessage(), "Erreur",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        List<Event> sorted = manager.events.stream()
                .sorted(Comparator.comparing(e -> e.dateDebut.valeur()))
                .toList();

        for (Event event : sorted) {
            tableModel.addRow(new Object[] {
                    event.id.valeur(),
                    event.type.valeur(),
                    event.title.valeur(),
                    event.proprietaire.valeur(),
                    event.dateDebut.valeur(),
                    event.description()
            });
        }
    }

    private void clearAfterAdd() {
        titleField.setText("");
        locationField.setText("");
        participantsField.setText("");
        deleteIdField.setText("");
    }

    private int parseIntOrDefault(String value, int defaultValue) {
        String trimmed = value == null ? "" : value.trim();
        return trimmed.isEmpty() ? defaultValue : Integer.parseInt(trimmed);
    }
}
