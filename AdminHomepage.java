import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class AdminHomepage extends JFrame {
	// To reference panels
	private JPanel northPanel, southPanel, eastPanel, westPanel, centerPanel;
	private JLabel welcomeLabel, infoLabel;
	private JButton createDoctorButton, createNurseButton, accountResetButton, logoutButton;
	private AdminDB admin;
	private String username;

	// Constructor
	public AdminHomepage() {
		super("Welcome");
	}

	// Constructor
	public AdminHomepage(String username, String password) {

		this.username = username;

		admin = new AdminDB(username, password);

		setExtendedState(JFrame.MAXIMIZED_BOTH);

		// Specify an action for the close button
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Add a BorderLayout manager to the content pane
		setLayout(new BorderLayout());

		// Create five panels
		northPanel = new JPanel();
		southPanel = new JPanel();
		eastPanel = new JPanel();
		westPanel = new JPanel();
		centerPanel = new JPanel();

		northPanel.setBackground(Color.cyan);
		centerPanel.setBackground(Color.white);
		southPanel.setBackground(Color.cyan);
		eastPanel.setBackground(Color.cyan);
		westPanel.setBackground(Color.cyan);

		buildNorthPanel();
		buildCenterPanel();

		northPanel.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));
		southPanel.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));
		centerPanel.setBorder(BorderFactory.createEmptyBorder(50, 500, 50, 500));

		// Add the five panels to the content pane
		add(northPanel, BorderLayout.NORTH);
		add(southPanel, BorderLayout.SOUTH);
		add(eastPanel, BorderLayout.EAST);
		add(westPanel, BorderLayout.WEST);
		add(centerPanel, BorderLayout.CENTER);

		// Pack and display the window
		pack();
		setVisible(true);
	}

	public void buildNorthPanel() {

		welcomeLabel = new JLabel("Welcome " + username);
		welcomeLabel.setFont(new Font("Serif", Font.BOLD, 40));
		northPanel.add(welcomeLabel);
	}

	public void buildCenterPanel() {

		infoLabel = new JLabel("Hi " + username + ", your assistance is required");
		infoLabel.setFont(new Font("Serif", Font.BOLD, 24));

		JPanel innerCenterPanel = new JPanel();
		JPanel innerSouthPanel = new JPanel();

		innerSouthPanel.setBorder(BorderFactory.createEmptyBorder(50, 100, 150, 100));

		innerCenterPanel.setBackground(Color.white);
		innerSouthPanel.setBackground(Color.white);

		innerCenterPanel.add(infoLabel);

		createDoctorButton = new JButton("Create Dr. Account");
		createNurseButton = new JButton("Create Nurse Account");
		accountResetButton = new JButton("Reset Account");
		logoutButton = new JButton("Logout");

		createDoctorButton.addActionListener(new CreateDoctorButtonListener());
		createNurseButton.addActionListener(new CreateNurseButtonListener());
		accountResetButton.addActionListener(new AccountResetButtonListener());
		logoutButton.addActionListener(new LogoutButtonListener());

		innerSouthPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 25, 25));

		innerSouthPanel.add(createDoctorButton);
		innerSouthPanel.add(createNurseButton);
		// innerSouthPanel.add(accountResetButton);
		innerSouthPanel.add(logoutButton);

		centerPanel.setLayout(new BorderLayout());
		centerPanel.add(innerCenterPanel, BorderLayout.CENTER);
		centerPanel.add(innerSouthPanel, BorderLayout.SOUTH);

	}

	private class CreateDoctorButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			new DoctorRegister();

		}
	}

	private class CreateNurseButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			new NurseRegister();

		}
	}

	private class AccountResetButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

		}
	}

	private class LogoutButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			admin.closeDB();
			dispose();
			new Homepage();
		}
	}

}
