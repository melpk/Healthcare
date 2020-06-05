import java.awt.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;

import java.awt.event.*;

public class PatientHomepage extends JFrame {
	// To reference panels
	private JPanel northPanel, southPanel, eastPanel, westPanel, centerPanel;
	private JLabel welcomeLabel;
	private JButton scheduleButton, editButton, cancelButton, logoutButton;
	private String username, password;
	private PatientDB patient;
	private String[][] tableData, tempTableData;
	private String[] tableHeader = { "Doctor", "Date", "Time", "Comments", "Appointment ID" };
	private String[] tempTableHeader = { "Date", "Time", "Comments", "Appointment ID" };

	private JTable table, tempTable;
	private JScrollPane scrollPane, tempScrollPane;

	// Constructor
	public PatientHomepage() {
		super("Welcome");

	}

	public PatientHomepage(String username, String password) {

		this();
		this.username = username;
		this.password = password;
		patient = new PatientDB(username, password);
		setExtendedState(JFrame.MAXIMIZED_BOTH);

		tableData = patient.getAppointment();
		tempTableData = patient.getTempAppointment();

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

		JPanel innerCenterPanel = new JPanel();
		JPanel innerSouthPanel = new JPanel();

		innerSouthPanel.setBorder(BorderFactory.createEmptyBorder(5, 50, 75, 50));

		innerCenterPanel.setBackground(Color.white);
		innerSouthPanel.setBackground(Color.white);

		innerCenterPanel.setLayout(new GridLayout(2, 1, 0, 25));

		table = new JTable(tableData, tableHeader);
		scrollPane = new JScrollPane(table);
		scrollPane.setBorder(BorderFactory.createTitledBorder(null, "Confirmed Appointments", TitledBorder.CENTER,
				TitledBorder.TOP));

		tempTable = new JTable(tempTableData, tempTableHeader);
		tempScrollPane = new JScrollPane(tempTable);
		tempScrollPane.setBorder(
				BorderFactory.createTitledBorder(null, "Pending Appointments", TitledBorder.CENTER, TitledBorder.TOP));

		innerCenterPanel.add(scrollPane);
		innerCenterPanel.add(tempScrollPane);

		scheduleButton = new JButton("Schedule Appointment");
		editButton = new JButton("Edit Appointment");
		cancelButton = new JButton("Cancel Appointment");
		logoutButton = new JButton("Logout");

		scheduleButton.addActionListener(new ScheduleButtonListener());
		editButton.addActionListener(new EditButtonListener());
		cancelButton.addActionListener(new CancelButtonListener());
		logoutButton.addActionListener(new LogoutButtonListener());

		innerSouthPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 25, 25));

		innerSouthPanel.add(scheduleButton);
		innerSouthPanel.add(editButton);
		innerSouthPanel.add(cancelButton);
		innerSouthPanel.add(logoutButton);

		centerPanel.setLayout(new BorderLayout());
		centerPanel.add(innerCenterPanel, BorderLayout.CENTER);
		centerPanel.add(innerSouthPanel, BorderLayout.SOUTH);

	}

	private class ScheduleButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			dispose();
			patient.closeDB();
			new ScheduleAppointment(username, password);

		}
	}

	private class EditButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			String apptID, date, time, comments;
			apptID = JOptionPane.showInputDialog("Enter the ID for the appointment to edit");
			date = JOptionPane.showInputDialog("Enter the appointment date");
			time = JOptionPane.showInputDialog("Enter the appointment time");
			comments = JOptionPane.showInputDialog("Enter any comment for the doctor");
			int value, id;
			id = Integer.parseInt(apptID);
			value = JOptionPane.showConfirmDialog(null, "Are you sure you want o edit this appointment?");
			if (value == JOptionPane.YES_OPTION) {
				if (patient.checkEditAppt(id)) {
					patient.scheduleAppointment(date, time, comments);
					patient.cancelAppointment(apptID);
					JOptionPane.showMessageDialog(null, "Appointment Edit Confirmed");
					patient.closeDB();
					dispose();
					new PatientHomepage(username, password);
				} else {
					JOptionPane.showMessageDialog(null, "Enter correct appointment ID");
				}
			} else if (value == JOptionPane.NO_OPTION) {
				JOptionPane.showMessageDialog(null, "Appointment Edit Halted");
			} else if (value == JOptionPane.CANCEL_OPTION) {
				JOptionPane.showMessageDialog(null, "Appointment Edit Canceled");
			}
		}
	}

	private class CancelButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			String apptID = JOptionPane.showInputDialog("Enter the appointment ID");

			int value = JOptionPane.showConfirmDialog(null, "Are you sure you want to cancel this appointment?");
			if (value == JOptionPane.YES_OPTION) {
				patient.cancelAppointment(apptID);
				JOptionPane.showMessageDialog(null, "Appointment is canceled");
				patient.closeDB();
				dispose();
				new PatientHomepage(username, password);
			} else if (value == JOptionPane.NO_OPTION) {
				JOptionPane.showMessageDialog(null, "Appointment Cancelation Halted");
			} else if (value == JOptionPane.CANCEL_OPTION) {
				JOptionPane.showMessageDialog(null, "Appointment Not Canceled");
			}

		}
	}

	private class LogoutButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			patient.closeDB();
			dispose();
			new Homepage();
		}
	}

}
