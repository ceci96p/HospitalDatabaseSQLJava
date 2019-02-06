// We need to import the java.sql package to use JDBC
import java.sql.*;


// To create the interface
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
// import java.util.*;

class RecepView implements ActionListener {
    // Connection to Oracle
    private Connection con;

    // Staff information
    private String recepID;

    // Initializes all class-wide interface panels
    private JPasswordField updatePasswordInput;
    private JTextField medicalRecordsPatientIDInput;
    private JCheckBox showMedicalRecordNumberCheckbox;
    private JCheckBox showMedicalRecordTypeCheckbox;
    private JCheckBox showMedicalRecordDescriptionCheckbox;
    private JCheckBox showMedicalRecordDateCheckbox;
    private JCheckBox showMedicalRecordAllCheckbox;
    private ServiceBookingView serviceBookingView;

    RecepView(Connection inputCon, String inputUsername) {
        con = inputCon;
        recepID = inputUsername;

        // Initializes staff information
        String name;
        String employmentPosition;

        // Obtains staff information to display on the interface
        try {
            Statement getStaffInfo = con.createStatement();
            ResultSet resultStaffInfo = getStaffInfo.executeQuery("SELECT StaffName, EmploymentPosition " +
                    "FROM HospitalStaff WHERE StaffIDNumber = '" + recepID + "'");
            resultStaffInfo.next();
            name = resultStaffInfo.getString("StaffName");
            employmentPosition = resultStaffInfo.getString("EmploymentPosition");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Message: " + ex.getMessage());
            name = "";
            employmentPosition = "";
        }

        /* CREATES THE INTERFACE */
        JFrame mainFrame = new JFrame("Receptionist View");
        JPanel recepViewPanel = new JPanel();

        // Creates user info labels
        JLabel staffIDLabel = new JLabel("Staff " + recepID.substring(1, 9) + ": " + name);
        JLabel employmentPositionLabel = new JLabel("Employment: " + employmentPosition);

        // Creates password update objects
        JLabel updatePasswordLabel = new JLabel("UPDATE PASSWORD");
        updatePasswordInput = new JPasswordField(15);
        JButton updatePasswordButton = new JButton("Update");

        // Creates the medical record query objects
        JLabel findMedicalRecordsLabel = new JLabel("FIND MEDICAL RECORDS");
        JLabel findMedicalRecordsPatientIDLabel = new JLabel("Patient ID:");
        medicalRecordsPatientIDInput = new JTextField(9);
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

        // Creates the employment statistics objects
        JLabel employmentStatisticsLabel = new JLabel("EMPLOYMENT STATISTICS");
        JButton hospitalStaffCount = new JButton("Total Hospital Staff");
        JButton mostUnderstaffedPosition = new JButton("Most Understaffed Position Total");
        JButton mostOverstaffedPosition = new JButton("Most Overstaffed Position Total");
        JButton averageStaffNumberPerPosition = new JButton("Average Number of Staff Per Position");

        JButton serviceBooking = new JButton("Services");


        /* DETERMINES THE LAYOUT FOR THE INTERFACE */
        // Sets the size of the window
        mainFrame.setSize(700, 500);
        mainFrame.setLocation(370, 280);
        mainFrame.setContentPane(recepViewPanel);
        recepViewPanel.setLayout(null);

        // User info layout
        staffIDLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 34));
        staffIDLabel.setBounds(20, 20, 700, 40);
        employmentPositionLabel.setBounds(30, 70, 200, 20);

        // Password update layout
        updatePasswordLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        updatePasswordLabel.setBounds(30, 120, 300, 20);
        updatePasswordInput.setBounds(30, 140, 150, 30);
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

        // Employment Statistics
        employmentStatisticsLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        employmentStatisticsLabel.setBounds(30, 305, 300, 20);
        hospitalStaffCount.setBounds(32, 330, 140, 20);
        hospitalStaffCount.addActionListener(this);
        hospitalStaffCount.setActionCommand("getStaffCount");
        mostUnderstaffedPosition.setBounds(32, 355, 230, 20);
        mostUnderstaffedPosition.addActionListener(this);
        mostUnderstaffedPosition.setActionCommand("getUnderstaffed");
        mostOverstaffedPosition.setBounds(32, 380, 220, 20);
        mostOverstaffedPosition.addActionListener(this);
        mostOverstaffedPosition.setActionCommand("getOverstaffed");
        averageStaffNumberPerPosition.setBounds(32, 405, 250, 20);
        averageStaffNumberPerPosition.addActionListener(this);
        averageStaffNumberPerPosition.setActionCommand("getAverageStaff");

        serviceBooking.setBounds(600, 440, 80, 20);
        serviceBooking.addActionListener(this);
        serviceBooking.setActionCommand("openServiceBooking");


        /* ADDS THE DIFFERENT OBJECTS TO THE INTERFACE */
        // Adds the user info labels to the interface
        recepViewPanel.add(staffIDLabel);
        recepViewPanel.add(employmentPositionLabel);

        // Adds the password update objects to the interface
        recepViewPanel.add(updatePasswordLabel);
        recepViewPanel.add(updatePasswordButton);
        recepViewPanel.add(updatePasswordInput);

        // Adds the medical record query objects to the interface
        recepViewPanel.add(findMedicalRecordsLabel);
        recepViewPanel.add(findMedicalRecordsPatientIDLabel);
        recepViewPanel.add(medicalRecordsPatientIDInput);
        recepViewPanel.add(searchMedicalRecordsButton);
        recepViewPanel.add(showMedicalRecordsCategoriesLabel);
        recepViewPanel.add(showMedicalRecordNumberCheckbox);
        recepViewPanel.add(showMedicalRecordNumberLabel);
        recepViewPanel.add(showMedicalRecordTypeCheckbox);
        recepViewPanel.add(showMedicalRecordTypeLabel);
        recepViewPanel.add(showMedicalRecordDescriptionCheckbox);
        recepViewPanel.add(showMedicalRecordDescriptionLabel);
        recepViewPanel.add(showMedicalRecordDateCheckbox);
        recepViewPanel.add(showMedicalRecordDateLabel);
        recepViewPanel.add(showMedicalRecordAllCheckbox);
        recepViewPanel.add(showMedicalRecordAllLabel);

        // Adds the employment statistics objects to the interface
        recepViewPanel.add(employmentStatisticsLabel);
        recepViewPanel.add(hospitalStaffCount);
        recepViewPanel.add(mostUnderstaffedPosition);
        recepViewPanel.add(mostOverstaffedPosition);
        recepViewPanel.add(averageStaffNumberPerPosition);

        recepViewPanel.add(serviceBooking);

        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);

        // anonymous inner class for closing the window
        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                try {
                    con.close();
                } catch (SQLException ex) {
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

            case "getStaffCount":
                getStaffCount();
                break;

            case "getUnderstaffed":
                getUnderstaffed();
                break;

            case "getOverstaffed":
                getOverstaffed();
                break;

            case "getAverageStaff":
                getAverageStaff();
                break;

            case "openServiceBooking":
                if (serviceBookingView != null) { return; }
                else { serviceBookingView = new ServiceBookingView(); }
                break;
        }
    }

    private void updatePassword(String inputPassword) {
        if (inputPassword.length() == 0) { return; }

        try {
            PreparedStatement inputSQLStatement = con.prepareStatement("UPDATE HospitalStaff SET " +
                    "StaffPassword = '" + inputPassword + "' WHERE StaffIDNumber = '" + recepID + "'");
            int rowCount = inputSQLStatement.executeUpdate();
            if (rowCount == 0) {
                JOptionPane.showMessageDialog(null, "Staff member " + recepID +
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

    private void getStaffCount() {
        try {
            Statement getStaffStatement = con.createStatement();
            ResultSet staffCountResults = getStaffStatement.executeQuery("SELECT SUM(sumByPosition.total) " +
                    "FROM " +
                    "(SELECT COUNT(EmploymentPosition) as total " +
                    "FROM  HospitalStaff " +
                    "GROUP BY EmploymentPosition) sumByPosition");

            if (staffCountResults.next()) {
                JOptionPane.showMessageDialog(null, "Total staff: " +
                        staffCountResults.getString(1));
            }
            else { JOptionPane.showMessageDialog(null, "No results."); }

            getStaffStatement.close();
        }
        catch(SQLException ex) {
            JOptionPane.showMessageDialog(null, "Message: " + ex.getMessage());
        }
    }

    private void getUnderstaffed() {
        try {
            Statement getUnderstaffedStatement = con.createStatement();
            ResultSet understaffedResults = getUnderstaffedStatement.executeQuery("SELECT " +
                    "MIN(sumByPosition.total) " +
                    "FROM " +
                    "(SELECT COUNT(EmploymentPosition) as total " +
                    "FROM HospitalStaff " +
                    "GROUP BY EmploymentPosition) sumByPosition");

            if (understaffedResults.next()) {
                JOptionPane.showMessageDialog(null, "Most understaffed position total: " +
                        understaffedResults.getString(1));
            }
            else { JOptionPane.showMessageDialog(null, "No results."); }

            getUnderstaffedStatement.close();
        }
        catch(SQLException ex) {
            JOptionPane.showMessageDialog(null, "Message: " + ex.getMessage());
        }
    }

    private void getOverstaffed() {
        try {
            Statement getOverstaffedStatement = con.createStatement();
            ResultSet overstaffedResults = getOverstaffedStatement.executeQuery("SELECT " +
                    "MAX(sumByPosition.total) " +
                    "FROM " +
                    "(SELECT COUNT(EmploymentPosition) as total " +
                    "FROM HospitalStaff " +
                    "GROUP BY EmploymentPosition) sumByPosition");

            if (overstaffedResults.next()) {
                JOptionPane.showMessageDialog(null, "Most overstaffed position total: " +
                        overstaffedResults.getString(1));
            }
            else { JOptionPane.showMessageDialog(null, "No results."); }

            getOverstaffedStatement.close();
        }
        catch(SQLException ex) {
            JOptionPane.showMessageDialog(null, "Message: " + ex.getMessage());
        }
    }

    private void getAverageStaff() {
        try {
            Statement getAverageStaffStatement = con.createStatement();
            ResultSet averageStaffResults = getAverageStaffStatement.executeQuery("SELECT " +
                    "AVG(sumByPosition.total) " +
                    "FROM " +
                    "(SELECT COUNT(EmploymentPosition) as total " +
                    "FROM HospitalStaff " +
                    "GROUP BY EmploymentPosition) sumByPosition");

            if (averageStaffResults.next()) {
                JOptionPane.showMessageDialog(null, "Average number of staff per position: " +
                        averageStaffResults.getString(1));
            }
            else { JOptionPane.showMessageDialog(null, "No results."); }

            getAverageStaffStatement.close();
        }
        catch(SQLException ex) {
            JOptionPane.showMessageDialog(null, "Message: " + ex.getMessage());
        }
    }

    private class ServiceBookingView implements ActionListener {
        /* ALL CLASS-WIDE INTERFACE PANELS */
        // Book new service
        private JComboBox<String> createServiceType;
        private JTextField createServicePatientIDInput;
        private JTextField createServiceDateInput;
        private JComboBox<String> createServiceRoomNumberInput;
        private JTextField createServiceStaffIDInput;
        private JTextField createServiceDepartureDateInput;
        private JTextField createServiceCostInput;

        // Cancel service
        private JTextField cancelServicePatientIDInput;
        private JTextField cancelServiceDateInput;


        ServiceBookingView() {
            /* CREATES THE INTERFACE */
            JFrame serviceBookingFrame = new JFrame("Service Booking");
            JPanel serviceBookingPanel = new JPanel();

            // Title
            JLabel serviceBookingLabel = new JLabel("Service Booking");

            // Book new service
            JLabel bookNewServiceLabel = new JLabel("BOOK NEW SERVICE");
            String[] serviceCategories = { "Service Category", "--------", "Appointment", "Maternity", "ER",
                    "Surgery" };
            createServiceType = new JComboBox<>(serviceCategories);
            JLabel createServicePatientIDLabel = new JLabel("Patient ID:");
            createServicePatientIDInput = new JTextField(9);
            JLabel createServiceDateLabel = new JLabel("Date:");
            createServiceDateInput = new JTextField(19);
            String[] roomNumbers = { "Room #", "--------", "A-001", "A-002", "A-101", "A-107", "A-108", "B-102",
                    "F-023", "G-302", "G-333" };
            createServiceRoomNumberInput = new JComboBox<>(roomNumbers);
            JLabel createServiceStaffIDLabel = new JLabel("Staff ID:");
            createServiceStaffIDInput = new JTextField(9);
            JLabel createServiceDepartureDateLabel = new JLabel("Departure Date:");
            createServiceDepartureDateInput = new JTextField(19);
            JLabel createServiceCostLabel = new JLabel("Cost:");
            createServiceCostInput = new JTextField();
            JButton createServiceButton = new JButton("Submit");

            // Cancel service
            JLabel cancelServiceLabel = new JLabel("CANCEL SERVICE");
            JLabel cancelServicePatientIDLabel = new JLabel("Patient ID:");
            cancelServicePatientIDInput = new JTextField();
            JLabel cancelServiceDateLabel = new JLabel("Date:");
            cancelServiceDateInput = new JTextField();
            JButton cancelServiceButton = new JButton("Cancel Service");

            // Service statistics
            JLabel serviceStatisticsLabel = new JLabel("SERVICE STATISTICS");
            JButton maxAvgCost = new JButton("Average cost of the most expensive service type");
            JButton minAvgCost = new JButton("Average cost of the cheapest service type");


            /* DETERMINES THE LAYOUT FOR THE INTERFACE */
            // Sets the size of the window
            serviceBookingFrame.setSize(700, 400);
            serviceBookingFrame.setLocation(450, 200);
            serviceBookingFrame.setContentPane(serviceBookingPanel);
            serviceBookingPanel.setLayout(null);

            // Title
            serviceBookingLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 34));
            serviceBookingLabel.setBounds(20, 20, 700, 40);

            // Book new service
            bookNewServiceLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
            bookNewServiceLabel.setBounds(30, 70, 150, 20);
            createServiceType.setBounds(32, 90, 160, 30);
            createServicePatientIDLabel.setBounds(197, 95, 80, 20);
            createServicePatientIDInput.setBounds(265, 90, 120, 30);
            createServiceDateLabel.setBounds(395, 95, 45, 20);
            createServiceDateInput.setBounds(430, 90, 150, 30);
            createServiceRoomNumberInput.setBounds(580, 90, 100, 30);
            createServiceStaffIDLabel.setBounds(35 ,123, 55,20);
            createServiceStaffIDInput.setBounds(90, 118, 120, 30);
            createServiceDepartureDateLabel.setBounds(220, 123, 105, 20);
            createServiceDepartureDateInput.setBounds(325, 118, 150, 30);
            createServiceCostLabel.setBounds(490, 123, 35, 20);
            createServiceCostInput.setBounds(525, 118, 80, 30);
            createServiceButton.setBounds(32, 160, 80, 20);
            createServiceButton.addActionListener(this);
            createServiceButton.setActionCommand("createService");

            // Cancel service
            cancelServiceLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
            cancelServiceLabel.setBounds(30, 210, 150, 20);
            cancelServicePatientIDLabel.setBounds(32, 235, 70, 20);
            cancelServicePatientIDInput.setBounds(100, 230, 150, 30);
            cancelServiceDateLabel.setBounds(270, 235, 45, 20);
            cancelServiceDateInput.setBounds(313, 230, 150, 30);
            cancelServiceButton.setBounds(480, 235, 100, 20);
            cancelServiceButton.addActionListener(this);
            cancelServiceButton.setActionCommand("cancelService");

            // Service statistics
            serviceStatisticsLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
            serviceStatisticsLabel.setBounds(30, 290, 150, 20);
            maxAvgCost.setBounds(32, 315, 330, 20);
            maxAvgCost.addActionListener(this);
            maxAvgCost.setActionCommand("maxAvgCost");
            minAvgCost.setBounds(32, 340, 290, 20);
            minAvgCost.addActionListener(this);
            minAvgCost.setActionCommand("minAvgCost");


            /* ADDS THE DIFFERENT OBJECTS TO THE INTERFACE */
            // Title
            serviceBookingFrame.add(serviceBookingLabel);

            // Book new service
            serviceBookingFrame.add(bookNewServiceLabel);
            serviceBookingFrame.add(createServiceType);
            serviceBookingFrame.add(createServicePatientIDLabel);
            serviceBookingFrame.add(createServicePatientIDInput);
            serviceBookingFrame.add(createServiceDateLabel);
            serviceBookingFrame.add(createServiceDateInput);
            serviceBookingFrame.add(createServiceRoomNumberInput);
            serviceBookingFrame.add(createServiceStaffIDLabel);
            serviceBookingFrame.add(createServiceStaffIDInput);
            serviceBookingFrame.add(createServiceDepartureDateLabel);
            serviceBookingFrame.add(createServiceDepartureDateInput);
            serviceBookingFrame.add(createServiceCostLabel);
            serviceBookingFrame.add(createServiceCostInput);
            serviceBookingFrame.add(createServiceButton);

            // Cancel service
            serviceBookingFrame.add(cancelServiceLabel);
            serviceBookingFrame.add(cancelServicePatientIDLabel);
            serviceBookingFrame.add(cancelServicePatientIDInput);
            serviceBookingFrame.add(cancelServiceDateLabel);
            serviceBookingFrame.add(cancelServiceDateInput);
            serviceBookingFrame.add(cancelServiceButton);

            // Service statistics
            serviceBookingFrame.add(serviceStatisticsLabel);
            serviceBookingFrame.add(maxAvgCost);
            serviceBookingFrame.add(minAvgCost);

            serviceBookingFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            serviceBookingFrame.setVisible(true);

            // anonymous inner class for closing the window
            serviceBookingFrame.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    serviceBookingView = null;
                }
            });
        }

        public void actionPerformed(ActionEvent e) {
            String action = e.getActionCommand();
            switch (action) {
                case "createService":
                    createService();
                    break;
                case "cancelService":
                    cancelService();
                    break;
                case "maxAvgCost":
                    maxAvgCost();
                    break;
                case "minAvgCost":
                    minAvgCost();
                    break;
            }
        }

        private void createService() {
            if (createServicePatientIDInput.getText().equals("") ||
                    createServiceDateInput.getText().equals("")) { return; }

            String reasonForVisit;
            String roomNumber;

            if (createServiceType.getSelectedItem().toString().equals("Service Category") ||
                    createServiceType.getSelectedItem().toString().equals("--------")) {
                reasonForVisit = "";
            }
            else { reasonForVisit = createServiceType.getSelectedItem().toString(); }

            if (createServiceRoomNumberInput.getSelectedItem().toString().equals("Room #") ||
                    createServiceRoomNumberInput.getSelectedItem().toString().equals("--------")) {
                roomNumber = "";
            }
            else { roomNumber = createServiceRoomNumberInput.getSelectedItem().toString(); }

            try {
                Statement checkStaffID = con.createStatement();
                ResultSet staffIDCheckResults = checkStaffID.executeQuery("SELECT StaffName FROM HospitalStaff " +
                        "WHERE StaffIDNumber = '" + createServiceStaffIDInput.getText() + "'");
                if (!staffIDCheckResults.next()) {
                    JOptionPane.showMessageDialog(null, "StaffID does not exist!");
                    return;
                }

                checkStaffID.close();

                PreparedStatement createServiceStatement = con.prepareStatement("INSERT INTO ServiceBooking " +
                        "VALUES ('" + roomNumber + "', TO_DATE('" + createServiceDateInput.getText() + "', " +
                        "'YYYY-MM-DD HH24:MI:SS'), '" + createServicePatientIDInput.getText() + "', TO_DATE('" +
                        createServiceDepartureDateInput.getText() + "', 'YYYY-MM-DD HH24:MI:SS'), '" +
                        reasonForVisit + "', " + createServiceCostInput.getText() + ")");
                int rowCount = createServiceStatement.executeUpdate();
                if (rowCount == 0) {
                    JOptionPane.showMessageDialog(null, "Patient ID does not exist! " +
                            "Try P########.");
                    return;
                }

                // commit work
                con.commit();

                createServiceStatement.close();

                Statement getServicesForPatient = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY);
                ResultSet servicesForPatientResults = getServicesForPatient.executeQuery("SELECT * FROM " +
                        "ServiceBooking WHERE PatientIDNumber = '" + createServicePatientIDInput.getText() + "'");

                // Create and set up the window.
                JFrame frame = new JFrame(createServicePatientIDInput.getText() + " Service Bookings");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                // Create and set up the content pane
                ResultTable resultTable = new ResultTable(servicesForPatientResults);
                resultTable.setOpaque(true); // content panes must be opaque
                frame.setContentPane(resultTable);
                // Display the window.
                frame.pack();
                frame.setVisible(true);

                // Close the statement
                getServicesForPatient.close();

                if (!createServiceStaffIDInput.getText().equals("")) {
                    PreparedStatement createStaffProvideServiceStatement = con.prepareStatement("INSERT INTO " +
                            "StaffProvideService VALUES ('" + createServiceStaffIDInput.getText() + "', '" +
                            roomNumber + "', TO_DATE('" + createServiceDateInput.getText() + "', " +
                            "'YYYY-MM-DD HH24:MI:SS'))");
                    int rowCount2 = createStaffProvideServiceStatement.executeUpdate();
                    if (rowCount2 == 0) {
                        JOptionPane.showMessageDialog(null, "Staff ID does not exist! " +
                                "Try S########.");
                        return;
                    }

                    // commit work
                    con.commit();

                    createStaffProvideServiceStatement.close();

                    Statement getStaffProvideService = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_READ_ONLY);
                    ResultSet staffProvideServiceResults = getStaffProvideService.executeQuery("SELECT * FROM " +
                            "StaffProvideService WHERE StaffIDNumber = '" + createServiceStaffIDInput.getText() + "'");

                    // Create and set up the window.
                    JFrame frame2 = new JFrame(createServiceStaffIDInput.getText() + " Service Bookings");
                    frame2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                    // Create and set up the content pane
                    ResultTable staffProvideServiceResultTable = new ResultTable(staffProvideServiceResults);
                    staffProvideServiceResultTable.setOpaque(true); // content panes must be opaque
                    frame2.setContentPane(staffProvideServiceResultTable);
                    // Display the window.
                    frame2.pack();
                    frame2.setVisible(true);

                    // Close the statement
                    getStaffProvideService.close();
                }
            }
            catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Message: " + ex.getMessage());
            }
        }

        private void cancelService() {
            try {
                PreparedStatement cancelServiceSQLStatement = con.prepareStatement("DELETE FROM ServiceBooking " +
                        "WHERE PatientIDNumber = '" + cancelServicePatientIDInput.getText() + "' " +
                        "AND DateOfIntake = TO_DATE('" + cancelServiceDateInput.getText() + "', " +
                        "'YYYY-MM-DD HH24:MI:SS')");
                int rowCount = cancelServiceSQLStatement.executeUpdate();

                if (rowCount == 0) {
                    JOptionPane.showMessageDialog(null, "Booking does not exist!");
                    cancelServiceSQLStatement.close();
                    return;
                }

                con.commit();

                cancelServiceSQLStatement.close();

                Statement getPatientServicesStatement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY);
                ResultSet patientServicesResults = getPatientServicesStatement.executeQuery("SELECT * FROM " +
                        "ServiceBooking WHERE PatientIDNumber = '" + cancelServicePatientIDInput.getText() + "'");

                // Create and set up the window.
                JFrame frame = new JFrame(cancelServicePatientIDInput.getText() + " Service Bookings");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                // Create and set up the content pane
                ResultTable resultTable = new ResultTable(patientServicesResults);
                resultTable.setOpaque(true); // content panes must be opaque
                frame.setContentPane(resultTable);
                // Display the window.
                frame.pack();
                frame.setVisible(true);

                // Close the statement
                getPatientServicesStatement.close();

                Statement getAllStaffProvideServicesStatement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY);
                ResultSet staffProvideServicesResults =
                        getAllStaffProvideServicesStatement.executeQuery("SELECT * FROM StaffProvideService");

                //Create and set up the window
                JFrame frame2 = new JFrame("Staff Service Bookings");
                frame2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                // Create and set up the content pane
                ResultTable resultTable2 = new ResultTable(staffProvideServicesResults);
                resultTable2.setOpaque(true); // content panes must be opaque
                frame2.setContentPane(resultTable2);
                // Display the window.
                frame2.pack();
                frame2.setVisible(true);

                // Close the statement
                getAllStaffProvideServicesStatement.close();
            }
            catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Message: " + ex.getMessage());
            }
        }

        private void maxAvgCost() {
            try {
                Statement getMaxAvgCostStatement = con.createStatement();
                ResultSet maxAvgCostResults = getMaxAvgCostStatement.executeQuery("SELECT " +
                        "MAX(avgByService.avg) " +
                        "FROM " +
                        "(SELECT AVG(cost) as avg " +
                        "FROM ServiceBooking " +
                        "GROUP BY ReasonForVisit) avgByService");

                if (maxAvgCostResults.next()) {
                    double result = maxAvgCostResults.getDouble(1);
                    double roundedResult = Math.round(result * 100.0) / 100.0;

                    JOptionPane.showMessageDialog(null, "Average cost of the most " +
                            "expensive service type: $" + roundedResult);
                }
                else { JOptionPane.showMessageDialog(null, "No results."); }

                getMaxAvgCostStatement.close();
            }
            catch(SQLException ex) {
                JOptionPane.showMessageDialog(null, "Message: " + ex.getMessage());
            }
        }

        private void minAvgCost() {
            try {
                Statement getMinAvgCostStatement = con.createStatement();
                ResultSet minAvgCostResults = getMinAvgCostStatement.executeQuery("SELECT " +
                        "MIN(avgByService.avg) " +
                        "FROM " +
                        "(SELECT AVG(cost) as avg " +
                        "FROM ServiceBooking " +
                        "GROUP BY ReasonForVisit) avgByService");

                if (minAvgCostResults.next()) {
                    float result = minAvgCostResults.getFloat(1);
                    double roundedResult = Math.round(result * 100.0) / 100.0;

                    JOptionPane.showMessageDialog(null, "Average cost of the cheapest " +
                            "service type: $" + roundedResult);
                }
                else { JOptionPane.showMessageDialog(null, "No results."); }

                getMinAvgCostStatement.close();
            }
            catch(SQLException ex) {
                JOptionPane.showMessageDialog(null, "Message: " + ex.getMessage());
            }
        }
    }
}