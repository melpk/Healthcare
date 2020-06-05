import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Register extends JFrame {

	// To reference panels
	private JPanel northPanel, southPanel, eastPanel, westPanel, centerPanel;
	private JLabel dateOfBirthLabel, genderLabel;
	private JTextField firstName, lastName, schoolID, dateOfBirth, username, password, cellNumber, email;
	private JButton confirmButton, cancelButton;
	private JRadioButton female, male, other;
	private PatientDB patient;

	// Constructor
	public Register() {

		// Set the title bar text
		super("Registration");

		patient = new PatientDB();

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

		buildCenterPanel();
		buildNorthPanel();

		northPanel.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));
		southPanel.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));
		centerPanel.setBorder(BorderFactory.createEmptyBorder(50, 750, 50, 750));

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

	public void buildCenterPanel() {

		firstName = new JTextField(25);
		lastName = new JTextField(25);
		schoolID = new JTextField(10);
		dateOfBirthLabel = new JLabel("Date of birth:");
		dateOfBirth = new JTextField(10);
		username = new JTextField(25);
		password = new JTextField(25);
		cellNumber = new JTextField(15);
		email = new JTextField(25);

		firstName.setText("First name");
		lastName.setText("Last name");
		schoolID.setText("School ID");
		dateOfBirth.setText("YYYY-MM-DD");
		username.setText("Username");
		password.setText("Password");
		cellNumber.setText("Cell number");
		email.setText("Email");

		genderLabel = new JLabel("Gender:");
		ButtonGroup genderGroup = new ButtonGroup();
		female = new JRadioButton("female", true);
		male = new JRadioButton("male");
		other = new JRadioButton("other");

		male.setBackground(Color.white);
		female.setBackground(Color.white);
		other.setBackground(Color.white);

		genderGroup.add(female);
		genderGroup.add(male);
		genderGroup.add(other);

		JPanel innerCenterPanel = new JPanel();
		JPanel innerSouthPanel = new JPanel();

		// Panel layouts
		centerPanel.setLayout(new BorderLayout());
		innerCenterPanel.setLayout(new GridLayout(15, 1, 0, 10));
		innerSouthPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 35, 25));

		innerCenterPanel.setBackground(Color.white);
		innerSouthPanel.setBackground(Color.white);

		innerCenterPanel.add(firstName);
		innerCenterPanel.add(lastName);
		innerCenterPanel.add(username);
		innerCenterPanel.add(password);
		innerCenterPanel.add(email);
		innerCenterPanel.add(dateOfBirthLabel);
		innerCenterPanel.add(dateOfBirth);
		innerCenterPanel.add(schoolID);
		innerCenterPanel.add(cellNumber);
		innerCenterPanel.add(genderLabel);
		innerCenterPanel.add(female);
		innerCenterPanel.add(male);
		innerCenterPanel.add(other);

		confirmButton = new JButton("Confirm");
		cancelButton = new JButton("Cancel");

		confirmButton.addActionListener(new ConfirmButtonListener());
		cancelButton.addActionListener(new CancelButtonListener());

		innerSouthPanel.add(confirmButton);
		innerSouthPanel.add(cancelButton);

		centerPanel.add(innerCenterPanel, BorderLayout.CENTER);
		centerPanel.add(innerSouthPanel, BorderLayout.SOUTH);

	}

	// Builds north panel
	private void buildNorthPanel() {

		JLabel welcomeLabel = new JLabel("Healthcare Registration");
		northPanel.add(welcomeLabel);
	}

	private class ConfirmButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			String getGender = "";
			String getFirstName = firstName.getText();
			String getLastName = lastName.getText();
			String fullName = getFirstName + " " + getLastName;
			String getUsername = username.getText();
			String getPassword = password.getText();
			String getEmail = email.getText();
			String getDateOfBirth = dateOfBirth.getText();
			String getSchoolID = schoolID.getText();
			String getCellNumber = cellNumber.getText();

			if (male.isSelected())
				getGender = "male";
			else if (female.isSelected())
				getGender = "female";
			else if (other.isSelected())
				getGender = "other";

			String[] patientData = { fullName, getUsername, getPassword, getEmail, getDateOfBirth, getSchoolID,
					getCellNumber, getGender };

			boolean done = patient.Register(patientData);

			if (done) {
				JOptionPane.showMessageDialog(null, "Registration Completed");
				patient.closeDB();
				dispose();
				new Homepage();
			} else {
				JOptionPane.showMessageDialog(null, "Registration Error. Username Already Taken!");
			}
		}
	}

	private class CancelButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			JOptionPane.showMessageDialog(null, "Registration Canceled");
			patient.closeDB();
			dispose();
			new Homepage();
		}
	}
}
