import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Main application window - KISS pattern
 * Role-based sidebar: ADMIN sees all, MEMBER sees limited
 */
public class MainFrame extends JFrame {

    // Colors
    private static final Color SIDEBAR_BG = new Color(47, 52, 65);
    private static final Color SIDEBAR_TEXT = new Color(220, 220, 230);
    private static final Color SIDEBAR_HOVER = new Color(68, 75, 90);
    private static final Color SIDEBAR_SELECTED = new Color(52, 152, 219);

    // Data lists
    private List<User> users = new ArrayList<>();
    private List<Club> clubs = new ArrayList<>();
    private List<Event> events = new ArrayList<>();
    private List<Achievement> achievements = new ArrayList<>();
    private List<Task> tasks = new ArrayList<>();
    private List<Notification> notifications = new ArrayList<>();
    private List<MembershipRequest> membershipRequests = new ArrayList<>();

    // Current user
    private User currentUser;
    private String userRole;

    // GUI
    private JPanel sidebarPanel;
    private JPanel contentPanel;
    private JButton activeButton;
    private CardLayout cardLayout;

    // Table models
    private DefaultTableModel clubTableModel;
    private DefaultTableModel eventTableModel;
    private DefaultTableModel achievementTableModel;
    private DefaultTableModel taskTableModel;
    private DefaultTableModel userTableModel;
    private JLabel totalClubsLabel, totalEventsLabel, totalMembersLabel, totalAchievementsLabel;

    public MainFrame(User user) {
        this.currentUser = user;
        this.userRole = user.getRole();

        addSampleData();

        setTitle("Club Management System - " + user.getName() + " (" + userRole + ")");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(900, 600));
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(Color.WHITE);

        // Sidebar
        sidebarPanel = createSidebar();
        add(sidebarPanel, BorderLayout.WEST);

        // Panels
        contentPanel.add(createDashboardPanel(), "dashboard");
        contentPanel.add(createClubsPanel(), "clubs");
        contentPanel.add(createEventsPanel(), "events");
        contentPanel.add(createAchievementsPanel(), "achievements");
        contentPanel.add(createTasksPanel(), "tasks");
        contentPanel.add(createNotificationsPanel(), "notifications");
        contentPanel.add(createUsersPanel(), "users");
        contentPanel.add(createProfilePanel(), "profile");

        add(contentPanel, BorderLayout.CENTER);
        showPanel("dashboard");
    }

    // ========== SAMPLE DATA ==========
    private void addSampleData() {
        users.add(new User("U001", "Ahmed Medhat", "ahmed@club.com", "pass123", "ADMIN"));
        users.add(new User("U002", "Lojain Mohammed", "lojain@club.com", "pass123", "CLUB_LEADER"));
        users.add(new User("U003", "Sereen Diab", "sereen@club.com", "pass123", "MEMBER"));
        users.add(new User("U004", "Sama Ibrahim", "sama@club.com", "pass123", "MEMBER"));

        Club tennisClub = new Club("C001", "Tennis Club", "For tennis enthusiasts", "U002");
        Club chessClub = new Club("C002", "Chess Club", "Strategic minds unite", "U002");
        tennisClub.addMember("U003");
        tennisClub.addMember("U004");
        chessClub.addMember("U003");
        clubs.add(tennisClub);
        clubs.add(chessClub);

        events.add(new Event("E001", "Tennis Tournament", new Date(), "C001"));
        events.add(new Event("E002", "Chess Championship", new Date(System.currentTimeMillis() + 86400000), "C002"));
        events.get(0).register("U003");
        events.get(0).register("U004");

        achievements.add(new Achievement("A001", "First Tournament Win", "U003", new Date(), 100));
        achievements.add(new Achievement("A002", "Club Leader Star", "U002", new Date(), 200));

        tasks.add(new Task("T001", "Organize tennis courts", "U003", new Date(System.currentTimeMillis() + 172800000)));
        tasks.add(new Task("T002", "Buy chess boards", "U004", new Date()));

        notifications.add(new Notification("N001", "System", "Welcome!", "Welcome to Club Management System"));
        notifications.add(new Notification("N002", "Admin", "New Event", "Tennis Tournament has been created"));

        membershipRequests.add(new MembershipRequest("M001", "C001", "U004", "Sama Ibrahim"));
        membershipRequests.add(new MembershipRequest("M002", "C002", "U004", "Sama Ibrahim"));

        // Add logged-in user if not already in list
        boolean exists = false;
        for (User u : users) {
            if (u.getUserId().equals(currentUser.getUserId())) exists = true;
        }
        if (!exists) users.add(currentUser);
    }

    // ========== SIDEBAR (role-based) ==========
    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(SIDEBAR_BG);
        sidebar.setPreferredSize(new Dimension(200, 0));
        sidebar.setBorder(new EmptyBorder(20, 0, 20, 0));

        JLabel logo = new JLabel("CLUB MS");
        logo.setForeground(Color.WHITE);
        logo.setFont(new Font("Arial", Font.BOLD, 18));
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);
        logo.setBorder(new EmptyBorder(0, 0, 30, 0));
        sidebar.add(logo);

        // All roles see these
        sidebar.add(createNavButton("Dashboard", "dashboard"));
        sidebar.add(createNavButton("Clubs", "clubs"));
        sidebar.add(createNavButton("Events", "events"));
        sidebar.add(createNavButton("Achievements", "achievements"));

        // ADMIN & CLUB_LEADER see these
        if (userRole.equals("ADMIN") || userRole.equals("CLUB_LEADER")) {
            sidebar.add(createNavButton("Tasks", "tasks"));
            sidebar.add(createNavButton("Notifications", "notifications"));
        }

        // Only ADMIN sees Users
        if (userRole.equals("ADMIN")) {
            sidebar.add(createNavButton("Users", "users"));
        }

        sidebar.add(Box.createVerticalGlue());
        sidebar.add(createNavButton("Profile", "profile"));
        sidebar.add(createNavButton("Logout", "logout"));

        return sidebar;
    }

    private JButton createNavButton(String text, String panelName) {
        JButton btn = new JButton(text);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(180, 40));
        btn.setBackground(SIDEBAR_BG);
        btn.setForeground(SIDEBAR_TEXT);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (btn != activeButton) btn.setBackground(SIDEBAR_HOVER);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (btn != activeButton) btn.setBackground(SIDEBAR_BG);
            }
        });

        btn.addActionListener(e -> {
            if ("logout".equals(panelName)) {
                handleLogout();
            } else {
                showPanel(panelName);
                setActiveButton(btn);
            }
        });

        return btn;
    }

    private void setActiveButton(JButton btn) {
        if (activeButton != null) {
            activeButton.setBackground(SIDEBAR_BG);
            activeButton.setForeground(SIDEBAR_TEXT);
        }
        activeButton = btn;
        activeButton.setBackground(SIDEBAR_SELECTED);
        activeButton.setForeground(Color.WHITE);
    }

    private void showPanel(String name) {
        cardLayout.show(contentPanel, name);
    }

    // ========== DASHBOARD ==========
    private JPanel createDashboardPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JLabel title = new JLabel("Welcome, " + currentUser.getName() + "!", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setBorder(new EmptyBorder(20, 0, 10, 0));
        panel.add(title, BorderLayout.NORTH);

        JPanel statsPanel = new JPanel(new GridLayout(1, 4, 15, 15));
        statsPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        statsPanel.setBackground(Color.WHITE);

        totalClubsLabel = new JLabel("0", SwingConstants.CENTER);
        totalEventsLabel = new JLabel("0", SwingConstants.CENTER);
        totalMembersLabel = new JLabel("0", SwingConstants.CENTER);
        totalAchievementsLabel = new JLabel("0", SwingConstants.CENTER);

        statsPanel.add(createStatCard("Total Clubs", totalClubsLabel, new Color(52, 152, 219)));
        statsPanel.add(createStatCard("Total Events", totalEventsLabel, new Color(46, 204, 113)));
        statsPanel.add(createStatCard("Members", totalMembersLabel, new Color(155, 89, 182)));
        statsPanel.add(createStatCard("Achievements", totalAchievementsLabel, new Color(241, 196, 15)));
        panel.add(statsPanel, BorderLayout.CENTER);

        updateDashboardStats();
        return panel;
    }

    private JPanel createStatCard(String title, JLabel valueLabel, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            new EmptyBorder(15, 15, 15, 15)));

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        titleLabel.setForeground(Color.GRAY);

        valueLabel.setFont(new Font("Arial", Font.BOLD, 28));
        valueLabel.setForeground(color);

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);
        return card;
    }

    // ========== CLUBS ==========
    private JPanel createClubsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setBorder(new EmptyBorder(10, 10, 10, 10));
        header.add(new JLabel("Club Management"), BorderLayout.WEST);

        // Only ADMIN/CLUB_LEADER can add clubs
        if (userRole.equals("ADMIN") || userRole.equals("CLUB_LEADER")) {
            JButton addBtn = new JButton("Add Club");
            addBtn.addActionListener(e -> showAddClubDialog());
            header.add(addBtn, BorderLayout.EAST);
        }
        panel.add(header, BorderLayout.NORTH);

        clubTableModel = new DefaultTableModel(
            new String[]{"Club ID", "Name", "Description", "Leader", "Members"}, 0);
        refreshClubTable();

        JTable table = new JTable(clubTableModel);
        table.setRowHeight(25);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        return panel;
    }

    private void refreshClubTable() {
        clubTableModel.setRowCount(0);
        for (Club c : clubs) {
            String leaderName = "Unknown";
            for (User u : users) {
                if (u.getUserId().equals(c.getLeaderId())) { leaderName = u.getName(); break; }
            }
            clubTableModel.addRow(new Object[]{c.getClubId(), c.getName(), c.getDescription(), leaderName, c.getMemberCount()});
        }
        updateDashboardStats();
    }

    private void showAddClubDialog() {
        JTextField nameField = new JTextField(15);
        JTextField descField = new JTextField(15);
        JComboBox<String> leaderCombo = new JComboBox<>();
        for (User u : users) {
            if (u.getRole().equals("CLUB_LEADER") || u.getRole().equals("ADMIN"))
                leaderCombo.addItem(u.getUserId() + " - " + u.getName());
        }

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.add(new JLabel("Club Name:")); panel.add(nameField);
        panel.add(new JLabel("Description:")); panel.add(descField);
        panel.add(new JLabel("Leader:")); panel.add(leaderCombo);

        if (JOptionPane.showConfirmDialog(this, panel, "Add Club", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            String id = "C" + String.format("%03d", clubs.size() + 1);
            String leaderId = ((String) leaderCombo.getSelectedItem()).split(" - ")[0];
            clubs.add(new Club(id, nameField.getText(), descField.getText(), leaderId));
            refreshClubTable();
        }
    }

    // ========== EVENTS ==========
    private JPanel createEventsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setBorder(new EmptyBorder(10, 10, 10, 10));
        header.add(new JLabel("Event Management"), BorderLayout.WEST);

        if (userRole.equals("ADMIN") || userRole.equals("CLUB_LEADER")) {
            JButton addBtn = new JButton("Add Event");
            addBtn.addActionListener(e -> showAddEventDialog());
            header.add(addBtn, BorderLayout.EAST);
        }
        panel.add(header, BorderLayout.NORTH);

        eventTableModel = new DefaultTableModel(
            new String[]{"Event ID", "Name", "Date", "Club", "Registered", "Status"}, 0);
        refreshEventTable();

        JTable table = new JTable(eventTableModel);
        table.setRowHeight(25);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        // Register button
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.setBackground(Color.WHITE);
        JButton regBtn = new JButton("Register to Event");
        regBtn.addActionListener(e -> showRegisterToEventDialog());
        bottom.add(regBtn);
        panel.add(bottom, BorderLayout.SOUTH);
        return panel;
    }

    private void refreshEventTable() {
        eventTableModel.setRowCount(0);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        for (Event e : events) {
            String clubName = "N/A";
            for (Club c : clubs) {
                if (c.getClubId().equals(e.getClubId())) { clubName = c.getName(); break; }
            }
            eventTableModel.addRow(new Object[]{e.getEventId(), e.getName(), sdf.format(e.getDate()),
                clubName, e.getRegisteredUserIds().size(), e.isCancelled() ? "Cancelled" : "Active"});
        }
        updateDashboardStats();
    }

    private void showAddEventDialog() {
        JTextField nameField = new JTextField(15);
        JTextField dateField = new JTextField("dd/MM/yyyy", 15);
        JComboBox<String> clubCombo = new JComboBox<>();
        for (Club c : clubs) clubCombo.addItem(c.getClubId() + " - " + c.getName());

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.add(new JLabel("Event Name:")); panel.add(nameField);
        panel.add(new JLabel("Date:")); panel.add(dateField);
        panel.add(new JLabel("Club:")); panel.add(clubCombo);

        if (JOptionPane.showConfirmDialog(this, panel, "Add Event", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            try {
                String id = "E" + String.format("%03d", events.size() + 1);
                String clubId = ((String) clubCombo.getSelectedItem()).split(" - ")[0];
                events.add(new Event(id, nameField.getText(), new SimpleDateFormat("dd/MM/yyyy").parse(dateField.getText()), clubId));
                refreshEventTable();
            } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Invalid date!"); }
        }
    }

    private void showRegisterToEventDialog() {
        JComboBox<String> eventCombo = new JComboBox<>();
        for (Event e : events) if (!e.isCancelled()) eventCombo.addItem(e.getEventId() + " - " + e.getName());
        JComboBox<String> userCombo = new JComboBox<>();
        for (User u : users) userCombo.addItem(u.getUserId() + " - " + u.getName());

        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        panel.add(new JLabel("Event:")); panel.add(eventCombo);
        panel.add(new JLabel("User:")); panel.add(userCombo);

        if (JOptionPane.showConfirmDialog(this, panel, "Register", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            String eventId = ((String) eventCombo.getSelectedItem()).split(" - ")[0];
            String userId = ((String) userCombo.getSelectedItem()).split(" - ")[0];
            for (Event e : events) if (e.getEventId().equals(eventId)) { e.register(userId); break; }
            refreshEventTable();
        }
    }

    // ========== ACHIEVEMENTS ==========
    private JPanel createAchievementsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setBorder(new EmptyBorder(10, 10, 10, 10));
        header.add(new JLabel("Achievements"), BorderLayout.WEST);
        if (userRole.equals("ADMIN") || userRole.equals("CLUB_LEADER")) {
            JButton addBtn = new JButton("Add Achievement");
            addBtn.addActionListener(e -> showAddAchievementDialog());
            header.add(addBtn, BorderLayout.EAST);
        }
        panel.add(header, BorderLayout.NORTH);

        achievementTableModel = new DefaultTableModel(
            new String[]{"ID", "Name", "Points", "Awarded To", "Date"}, 0);
        refreshAchievementTable();

        JTable table = new JTable(achievementTableModel);
        table.setRowHeight(25);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        return panel;
    }

    private void refreshAchievementTable() {
        achievementTableModel.setRowCount(0);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        for (Achievement a : achievements) {
            String userName = "N/A";
            for (User u : users) if (u.getUserId().equals(a.getUserId())) { userName = u.getName(); break; }
            achievementTableModel.addRow(new Object[]{a.getAchievementId(), a.getName(), a.getRequiredPoints(), userName, sdf.format(a.getDateEarned())});
        }
        updateDashboardStats();
    }

    private void showAddAchievementDialog() {
        JTextField nameField = new JTextField(15);
        JTextField pointsField = new JTextField("100", 15);
        JComboBox<String> userCombo = new JComboBox<>();
        for (User u : users) userCombo.addItem(u.getUserId() + " - " + u.getName());

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.add(new JLabel("Name:")); panel.add(nameField);
        panel.add(new JLabel("Points:")); panel.add(pointsField);
        panel.add(new JLabel("Award To:")); panel.add(userCombo);

        if (JOptionPane.showConfirmDialog(this, panel, "Add Achievement", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            String id = "A" + String.format("%03d", achievements.size() + 1);
            String userId = ((String) userCombo.getSelectedItem()).split(" - ")[0];
            achievements.add(new Achievement(id, nameField.getText(), userId, new Date(), Integer.parseInt(pointsField.getText())));
            refreshAchievementTable();
        }
    }

    // ========== TASKS (ADMIN/CLUB_LEADER only) ==========
    private JPanel createTasksPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setBorder(new EmptyBorder(10, 10, 10, 10));
        header.add(new JLabel("Task Management"), BorderLayout.WEST);
        JButton addBtn = new JButton("Add Task");
        addBtn.addActionListener(e -> showAddTaskDialog());
        header.add(addBtn, BorderLayout.EAST);
        panel.add(header, BorderLayout.NORTH);

        taskTableModel = new DefaultTableModel(new String[]{"ID", "Title", "Assigned To", "Due Date", "Status"}, 0);
        refreshTaskTable();

        JTable table = new JTable(taskTableModel);
        table.setRowHeight(25);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.setBackground(Color.WHITE);
        JButton completeBtn = new JButton("Mark Complete");
        completeBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                String id = (String) taskTableModel.getValueAt(row, 0);
                for (Task t : tasks) if (t.getTaskId().equals(id)) { t.complete(); break; }
                refreshTaskTable();
            }
        });
        bottom.add(completeBtn);
        panel.add(bottom, BorderLayout.SOUTH);
        return panel;
    }

    private void refreshTaskTable() {
        taskTableModel.setRowCount(0);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        for (Task t : tasks) {
            String userName = "N/A";
            for (User u : users) if (u.getUserId().equals(t.getAssignedTo())) { userName = u.getName(); break; }
            taskTableModel.addRow(new Object[]{t.getTaskId(), t.getTitle(), userName, sdf.format(t.getDueDate()), t.getStatus()});
        }
    }

    private void showAddTaskDialog() {
        JTextField titleField = new JTextField(15);
        JTextField dateField = new JTextField("dd/MM/yyyy", 15);
        JComboBox<String> userCombo = new JComboBox<>();
        for (User u : users) userCombo.addItem(u.getUserId() + " - " + u.getName());

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.add(new JLabel("Title:")); panel.add(titleField);
        panel.add(new JLabel("Due Date:")); panel.add(dateField);
        panel.add(new JLabel("Assign To:")); panel.add(userCombo);

        if (JOptionPane.showConfirmDialog(this, panel, "Add Task", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            try {
                String id = "T" + String.format("%03d", tasks.size() + 1);
                String userId = ((String) userCombo.getSelectedItem()).split(" - ")[0];
                tasks.add(new Task(id, titleField.getText(), userId, new SimpleDateFormat("dd/MM/yyyy").parse(dateField.getText())));
                refreshTaskTable();
            } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Invalid date!"); }
        }
    }

    // ========== NOTIFICATIONS (ADMIN/CLUB_LEADER only) ==========
    private JPanel createNotificationsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setBorder(new EmptyBorder(10, 10, 10, 10));
        header.add(new JLabel("Notifications"), BorderLayout.WEST);
        JButton addBtn = new JButton("Send Notification");
        addBtn.addActionListener(e -> showSendNotificationDialog());
        header.add(addBtn, BorderLayout.EAST);
        panel.add(header, BorderLayout.NORTH);

        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (Notification n : notifications)
            listModel.addElement("[" + n.getSend() + "] " + n.getMessage() + (n.isRead() ? " ✓" : " ●"));

        JList<String> list = new JList<>(listModel);
        list.setFont(new Font("Arial", Font.PLAIN, 13));
        panel.add(new JScrollPane(list), BorderLayout.CENTER);

        JButton markBtn = new JButton("Mark Read");
        markBtn.addActionListener(e -> {
            int idx = list.getSelectedIndex();
            if (idx >= 0 && idx < notifications.size()) {
                notifications.get(idx).markAsRead();
                listModel.set(idx, "[" + notifications.get(idx).getSend() + "] " + notifications.get(idx).getMessage() + " ✓");
            }
        });
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.setBackground(Color.WHITE);
        bottom.add(markBtn);
        panel.add(bottom, BorderLayout.SOUTH);
        return panel;
    }

    private void showSendNotificationDialog() {
        JTextField titleField = new JTextField(15);
        JTextField msgField = new JTextField(15);
        JComboBox<String> userCombo = new JComboBox<>();
        userCombo.addItem("ALL");
        for (User u : users) userCombo.addItem(u.getUserId() + " - " + u.getName());

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.add(new JLabel("Title:")); panel.add(titleField);
        panel.add(new JLabel("Message:")); panel.add(msgField);
        panel.add(new JLabel("Send To:")); panel.add(userCombo);

        if (JOptionPane.showConfirmDialog(this, panel, "Send Notification", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            String id = "N" + String.format("%03d", notifications.size() + 1);
            String sendTo = "ALL".equals(userCombo.getSelectedItem()) ? "ALL" : ((String) userCombo.getSelectedItem()).split(" - ")[0];
            notifications.add(new Notification(id, sendTo, titleField.getText(), msgField.getText()));
            contentPanel.remove(5);
            contentPanel.add(createNotificationsPanel(), "notifications", 5);
            contentPanel.revalidate();
        }
    }

    // ========== USERS (ADMIN only) ==========
    private JPanel createUsersPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setBorder(new EmptyBorder(10, 10, 10, 10));
        header.add(new JLabel("User Management"), BorderLayout.WEST);
        JButton addBtn = new JButton("Add User");
        addBtn.addActionListener(e -> showAddUserDialog());
        header.add(addBtn, BorderLayout.EAST);
        panel.add(header, BorderLayout.NORTH);

        userTableModel = new DefaultTableModel(new String[]{"User ID", "Name", "Email", "Role"}, 0);
        refreshUserTable();

        JTable table = new JTable(userTableModel);
        table.setRowHeight(25);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        return panel;
    }

    private void refreshUserTable() {
        userTableModel.setRowCount(0);
        for (User u : users)
            userTableModel.addRow(new Object[]{u.getUserId(), u.getName(), u.getEmail(), u.getRole()});
        updateDashboardStats();
    }

    private void showAddUserDialog() {
        JTextField nameField = new JTextField(15);
        JTextField emailField = new JTextField(15);
        JTextField passField = new JTextField(15);
        JComboBox<String> roleCombo = new JComboBox<>(new String[]{"MEMBER", "CLUB_LEADER", "ADMIN"});

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.add(new JLabel("Name:")); panel.add(nameField);
        panel.add(new JLabel("Email:")); panel.add(emailField);
        panel.add(new JLabel("Password:")); panel.add(passField);
        panel.add(new JLabel("Role:")); panel.add(roleCombo);

        if (JOptionPane.showConfirmDialog(this, panel, "Add User", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            String id = "U" + String.format("%03d", users.size() + 1);
            users.add(new User(id, nameField.getText(), emailField.getText(), passField.getText(), (String) roleCombo.getSelectedItem()));
            refreshUserTable();
        }
    }

    // ========== PROFILE ==========
    private JPanel createProfilePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel title = new JLabel("My Profile");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(title, gbc);

        String[][] data = {
            {"User ID:", currentUser.getUserId()},
            {"Name:", currentUser.getName()},
            {"Email:", currentUser.getEmail()},
            {"Role:", currentUser.getRole()}
        };
        gbc.gridwidth = 1;
        for (int i = 0; i < data.length; i++) {
            gbc.gridx = 0; gbc.gridy = i + 1;
            panel.add(new JLabel(data[i][0]), gbc);
            gbc.gridx = 1;
            panel.add(new JLabel(data[i][1]), gbc);
        }
        return panel;
    }

    // ========== HELPERS ==========
    private void updateDashboardStats() {
        totalClubsLabel.setText(String.valueOf(clubs.size()));
        totalEventsLabel.setText(String.valueOf(events.size()));
        int count = 0;
        for (Club c : clubs) count += c.getMemberCount();
        totalMembersLabel.setText(String.valueOf(count));
        totalAchievementsLabel.setText(String.valueOf(achievements.size()));
    }

    private void handleLogout() {
        int confirm = JOptionPane.showConfirmDialog(this, "Logout?", "Logout", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            dispose(); // Close main frame
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setVisible(true); // Show login again
        }
    }
}