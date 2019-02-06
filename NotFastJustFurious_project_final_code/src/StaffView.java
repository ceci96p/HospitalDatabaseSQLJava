// We need to import the java.sql package to use JDBC
import java.sql.*;


// To create the interface
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
// import java.util.*;

class StaffView implements ActionListener {
    // Connection to Oracle
    private Connection con;

    // Staff information
    private String staffID;

    /* ALL CLASS-WIDE INTERFACE PANELS */
    // Password update
    private JPasswordField updatePasswordInput;

    // Medical records query
    private JTextField medicalRecordsPatientIDInput;
    private JCheckBox showMedicalRecordNumberCheckbox;
    private JCheckBox showMedicalRecordTypeCheckbox;
    private JCheckBox showMedicalRecordDescriptionCheckbox;
    private JCheckBox showMedicalRecordDateCheckbox;
    private JCheckBox showMedicalRecordAllCheckbox;

    // Patient statistics
    JTextField countPatientsDateInput;

    StaffView(Connection inputCon, String inputUsername) {
        con = inputCon;
        staffID = inputUsername;

        // Initializes staff information
        String name;
        String employmentPosition;

        // Obtains staff information to display on the interface
        try {
            Statement getStaffInfo = con.createStatement();
            ResultSet resultStaffInfo = getStaffInfo.executeQuery("SELECT StaffName, EmploymentPosition " +
                    "FROM HospitalStaff WHERE StaffIDNumber = '" + staffID + "'");
            resultStaffInfo.next();
            name = resultStaffInfo.getString("StaffName");
            employmentPosition = resultStaffInfo.getString("EmploymentPosition");
        }
        catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Message: " + ex.getMessage());
            name = "";
            employmentPosition = "";
        }

        /* CREATES THE INTERFACE */
        JFrame mainFrame = new JFrame("Staff View");
        JPanel staffViewPanel = new JPanel();

        // Creates user info labels
        JLabel staffIDLabel = new JLabel("Staff " + staffID.substring(1,9) + ": " + name);
        JLabel employmentPositionLabel = new JLabel("Employment: " + employmentPosition);

        // Creates password update objects
        JLabel updatePasswordLabel = new JLabel("UPDATE PASSWORD");
        updatePasswordInput = new JPasswordField(15);
        JButton updatePasswordButton = new JButton("Update");

        // Creates the medical record query objects
        JLabel findMedicalRecordsLabel = new JLabel("FIND MEDICAL RECORDS");
        JLabel findMedicalRecordsPatientIDLabel = new JLabel("Patient ID:");
        medicalRecordsPatientIDInput= new JTextField(9);
        JLabel showMedicalRecordsCategoriesLabel = new JLabel("Show:");
        showMedicalRecordNumberCheckbox = new JCheckBox();
        JLabel showMedicalRecordNumberLabel = new JLabel("Record Number");
        showMedicalRecordTypeCheckbox = new JCheckBox();
        JLabel showMedicalRecordTypeLabel = new JLabel("Category");
        showMedicalRecordDescriptionCheckbox = new JCheckBox();
        JLabel showMedicalRecordDescriptionLabel = new JLabel("Description");
        showMedicalRecordDateCheckbox = new JCheckBox();
        JLabel showMedicalRecordDateLabel = new JLabel("Date");
        showMedicalRecordAllCheckbox = new JCheckBox();
        JLabel showMedicalRecordAllLabel = new JLabel("All");
        JButton searchMedicalRecordsButton = new JButton("Search");

        // Creates the patient statistics objects
        JLabel patientStatisticsLabel = new JLabel("PATIENT STATISTICS");
        JLabel countPatientsLabel = new JLabel("Number of patients that have services scheduled after ");
        countPatientsDateInput = new JTextField();
        JButton countPatientsSubmit = new JButton("Submit");
        JButton neediestPatients = new JButton("Patients scheduled for all services");


        /* DETERMINES THE LAYOUT FOR THE INTERFACE */
        // Sets the size of the window
        mainFrame.setSize(700, 500);
        mainFrame.setLocation(370, 280);
        mainFrame.setContentPane(staffViewPanel);
        staffViewPanel.setLayout(null);

        // User info layout
        staffIDLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 34));
        staffIDLabel.setBounds(20, 20, 700, 40);
        employmentPositionLabel.setBounds(30, 70, 200, 20);

        // Password update layout
        updatePasswordLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        updatePasswordLabel.setBounds(30, 120, 300, 20);
        updatePasswordInput.setBounds(30,140,150,30);
        updatePasswordInput.setEchoChar('*');
        updatePasswordButton.setBounds(200, 145, 80, 20);
        updatePasswordButton.addActionListener(this);
        updatePasswordButton.setActionCommand("updatePassword");

        // Medical record query layout
        findMedicalRecordsLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        findMedicalRecordsLabel.setBounds(30, 200, 300, 20);
        findMedicalRecordsPatientIDLabel.setBounds(32, 225, 80, 20);
        medicalRecordsPatientIDInput.setBounds(100, 220, 150, 30);
        searchMedicalRecordsButton.setBounds(270, 225, 80, 20);
        searchMedicalRecordsButton.addActionListener(this);
        searchMedicalRecordsButton.setActionCommand("searchRecords");
        showMedicalRecordsCategoriesLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));
        showMedicalRecordsCategoriesLabel.setBounds(32, 250, 30, 20);
        Font checkBoxLabelFont = new Font(Font.SANS_SERIF, Font.PLAIN, 8);
        showMedicalRecordNumberCheckbox.setBounds(62, 250, 30, 20);
        showMedicalRecordNumberCheckbox.addActionListener(this);
        showMedicalRecordNumberCheckbox.setActionCommand("medicalRecordOptionPressed");
        showMedicalRecordNumberLabel.setFont(checkBoxLabelFont);
        showMedicalRecordNumberLabel.setBounds(87, 250, 60, 20);
        showMedicalRecordTypeCheckbox.setBounds(150, 250, 30, 20);
        showMedicalRecordTypeCheckbox.addActionListener(this);
        showMedicalRecordTypeCheckbox.setActionCommand("medicalRecordOptionPressed");
        showMedicalRecordTypeLabel.setFont(checkBoxLabelFont);
        showMedicalRecordTypeLabel.setBounds(175, 250, 40, 20);
        showMedicalRecordDescriptionCheckbox.setBounds(215, 250, 30, 20);
        showMedicalRecordDescriptionCheckbox.addActionListener(this);
        showMedicalRecordDescriptionCheckbox.setActionCommand("medicalRecordOptionPressed");
        showMedicalRecordDescriptionLabel.setFont(checkBoxLabelFont);
        showMedicalRecordDescriptionLabel.setBounds(240, 250, 50, 20);
        showMedicalRecordDateCheckbox.setBounds(290, 250, 30, 20);
        showMedicalRecordDateCheckbox.addActionListener(this);
        showMedicalRecordDateCheckbox.setActionCommand("medicalRecordOptionPressed");
        showMedicalRecordDateLabel.setFont(checkBoxLabelFont);
        showMedicalRecordDateLabel.setBounds(315, 250, 25, 20);
        showMedicalRecordAllCheckbox.setBounds(340, 250, 30, 20);
        showMedicalRecordAllCheckbox.addActionListener(this);
        showMedicalRecordAllCheckbox.setActionCommand("showAllPress");
        showMedicalRecordAllLabel.setFont(checkBoxLabelFont);
        showMedicalRecordAllLabel.setBounds(365, 250, 20, 20);

        // Patient statistics layout
        patientStatisticsLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        patientStatisticsLabel.setBounds(30, 300, 300, 20);
        countPatientsLabel.setBounds(32, 325, 345, 20);
        countPatientsDateInput.setBounds(377, 320, 150, 30);
        countPatientsSubmit.setBounds(547, 325, 80, 20);
        countPatientsSubmit.addActionListener(this);
        countPatientsSubmit.setActionCommand("countPatients");
        neediestPatients.setBounds(32, 360, 240, 20);
        neediestPatients.addActionListener(this);
        neediestPatients.setActionCommand("neediestPatients");


        /* ADDS THE DIFFERENT OBJECTS TO THE INTERFACE */
        // Adds the user info labels to the interface
        staffViewPanel.add(staffIDLabel);
        staffViewPanel.add(employmentPositionLabel);

        // Adds the password update objects to the interface
        staffViewPanel.add(updatePasswordLabel);
        staffViewPanel.add(updatePasswordButton);
        staffViewPanel.add(updatePasswordInput);

        // Adds the medical record query objects to the interface
        staffViewPanel.add(findMedicalRecordsLabel);
        staffViewPanel.add(findMedicalRecordsPatientIDLabel);
        staffViewPanel.add(medicalRecordsPatientIDInput);
        staffViewPanel.add(searchMedicalRecordsButton);
        staffViewPanel.add(showMedicalRecordsCategoriesLabel);
        staffViewPanel.add(showMedicalRecordNumberCheckbox);
        staffViewPanel.add(showMedicalRecordNumberLabel);
        staffViewPanel.add(showMedicalRecordTypeCheckbox);
        staffViewPanel.add(showMedicalRecordTypeLabel);
        staffViewPanel.add(showMedicalRecordDescriptionCheckbox);
        staffViewPanel.add(showMedicalRecordDescriptionLabel);
        staffViewPanel.add(showMedicalRecordDateCheckbox);
        staffViewPanel.add(showMedicalRecordDateLabel);
        staffViewPanel.add(showMedicalRecordAllCheckbox);
        staffViewPanel.add(showMedicalRecordAllLabel);

        // Adds the patient statistics objects to the interface
        staffViewPanel.add(patientStatisticsLabel);
        staffViewPanel.add(countPatientsLabel);
        staffViewPanel.add(countPatientsDateInput);
        staffViewPanel.add(countPatientsSubmit);
        staffViewPanel.add(neediestPatients);

        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);

        // anonymous inner class for closing the window
        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                try {
                    con.close();
                }
                catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Message: " + ex.getMessage());
                }
            }
        });
    }

    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        switch (action) {
            case "showAllPress":
                if (showMedicalRecordAllCheckbox.isSelected()) {
                    showMedicalRecordNumberCheckbox.setSelected(true);
                    showMedicalRecordTypeCheckbox.setSelected(true);
                    showMedicalRecordDescriptionCheckbox.setSelected(true);
                    showMedicalRecordDateCheckbox.setSelected(true);
                }
                else {
                    showMedicalRecordNumberCheckbox.setSelected(false);
                    showMedicalRecordTypeCheckbox.setSelected(false);
                    showMedicalRecordDescriptionCheckbox.setSelected(false);
                    showMedicalRecordDateCheckbox.setSelected(false);
                }
                break;

            case "medicalRecordOptionPressed":
                if (!showMedicalRecordNumberCheckbox.isSelected()
                        || !showMedicalRecordTypeCheckbox.isSelected()
                        || !showMedicalRecordDescriptionCheckbox.isSelected()
                        || !showMedicalRecordDateCheckbox.isSelected()) {
                    showMedicalRecordAllCheckbox.setSelected(false);
                }
                else { showMedicalRecordAllCheckbox.setSelected(true); }
                break;

            case "updatePassword":
                updatePassword(String.valueOf(updatePasswordInput.getPassword()));
                break;

            case "searchRecords":
                searchRecords(medicalRecordsPatientIDInput.getText());
                break;

            case "countPatients":
                countPatients(countPatientsDateInput.getText());
                break;

            case "neediestPatients":
                neediestPatients();
                break;
        }
    }

    private void updatePassword(String inputPassword) {
        if (inputPassword.length() == 0) { return; }

        try {
            PreparedStatement inputSQLStatement = con.prepareStatement("UPDATE HospitalStaff SET " +
                    "StaffPassword = '" + inputPassword + "' WHERE StaffIDNumber = '" + staffID + "'");
            int rowCount = inputSQLStatement.executeUpdate();
            if (rowCount == 0) {
                JOptionPane.showMessageDialog(null, "Staff member " + staffID +
                        " does not exist!");
            }
            con.commit();
            inputSQLStatement.close();
            updatePasswordInput.setText("");
        }
        catch(SQLException ex) {
            JOptionPane.showMessageDialog(null, "Message: " + ex.getMessage());
        }
    }

    private void searchRecords(String inputPatientID) {
        if (inputPatientID.length() == 0) { return; }
        String recordNumber = "";
        String recordCategory = "";
        String recordDescription = "";
        String recordDate = "";

        if (showMedicalRecordNumberCheckbox.isSelected()) { recordNumber = "RecordNumber"; }
        if (showMedicalRecordTypeCheckbox.isSelected()) { recordCategory = ", Category"; }
        if (showMedicalRecordDescriptionCheckbox.isSelected()) { recordDescription = ", Description"; }
        if (showMedicalRecordDateCheckbox.isSelected()) { recordDate = ", DateOfRecord"; }

        String fetchAll = recordNumber + recordCategory + recordDescription + recordDate;

        if (fetchAll.length() == 6) {
            JOptionPane.showMessageDialog(null, "No options have been selected!");
            return;
        }

        // Fixes the "SELECT _______" string if the first category has not been selected
        if (fetchAll.substring(0,2).equals(", ")) {
            fetchAll = fetchAll.substring(2, fetchAll.length());
        }

        try {
            Statement getMedicalRecordsSQLStatement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            ResultSet medicalRecordResults = getMedicalRecordsSQLStatement.executeQuery(("SELECT " + fetchAll +
                    " FROM MedicalRecord WHERE PatientIDNumber = '" + inputPatientID + "'"));

            if (medicalRecordResults.next()) {
                medicalRecordResults.previous();

                // Create and set up the window.
                JFrame frame = new JFrame(inputPatientID + " Medical Records");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                // Create and set up the content pane
                ResultTable resultTable = new ResultTable(medicalRecordResults);
                resultTable.setOpaque(true); // content panes must be opaque
                frame.setContentPane(resultTable);
                // Display the window.
                frame.pack();
                frame.setVisible(true);

                // Close the statement
                getMedicalRecordsSQLStatement.close();
            }

            else {
                JOptionPane.showMessageDialog(null, "Patient ID does not exist! " +
                        "Try P########.");
            }
        }
        catch(SQLException ex) {
            JOptionPane.showMessageDialog(null, "Message: " + ex.getMessage());
        }
    }

    private void countPatients(String dateInput) {
        try {
            Statement countPatientsStatement = con.createStatement();
            ResultSet countPatientsResults = countPatientsStatement.executeQuery("SELECT " +
                    "COUNT(PatientIDNumber) FROM ServiceBooking WHERE DateOfIntake >= " +
                    "TO_DATE('" + dateInput + "', " +
                    "'YYYY-MM-DD HH24:MI:SS')");

            if (countPatientsResults.next()) {
                JOptionPane.showMessageDialog(null,
                        countPatientsResults.getString(1) + " Patients");
            }
            else { JOptionPane.showMessageDialog(null, "No patients."); }

            countPatientsStatement.close();
        }
        catch(SQLException ex) {
            JOptionPane.showMessageDialog(null, "Message: " + ex.getMessage());
        }
    }

    private void neediestPatients() {
        try {
            Statement neediestPatientsStatement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            ResultSet neediestPatientResults = neediestPatientsStatement.executeQuery("SELECT " +
                    "p.PatientIDNumber, p.PatientName FROM Patient p WHERE NOT EXISTS " +
                    "((SELECT v.ReasonForVisit FROM ServiceBooking v) MINUS " +
                    "(SELECT s.ReasonForVisit FROM ServiceBooking s " +
                    "WHERE s.PatientIDNumber = p.PatientIDNumber))");

            if (neediestPatientResults.next()) {
                neediestPatientResults.previous();

                // Create and set up the window
                JFrame frame = new JFrame("Patients scheduled for all services");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                // Create and set up the content pane
                ResultTable resultTable = new ResultTable(neediestPatientResults);
                resultTable.setOpaque(true); // content panes must be opaque
                frame.setContentPane(resultTable);

                // Display the window
                frame.pack();
                frame.setVisible(true);

                // Close the statement
                neediestPatientsStatement.close();
            }
        }
        catch(SQLException ex) {
            JOptionPane.showMessageDialog(null, "Message: " + ex.getMessage());
        }
    }
}