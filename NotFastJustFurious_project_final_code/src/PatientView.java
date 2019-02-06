// We need to import the java.sql package to use JDBC
import java.sql.*;


// To create the interface
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
// import java.util.*;

public class PatientView implements ActionListener {
    // Connection to Oracle
    private Connection con;

    // Patient information
    private String patientID;
    private String address;
    private String phoneNumber;

    // Creates all class-wide interface panels
    private JLabel addressLabel;
    private JLabel phoneNumberLabel;
    private JComboBox<String> updateCategory;
    private JTextField updateInput;

    PatientView(Connection inputCon, String inputUsername) {
        con = inputCon;
        patientID = inputUsername;

        // Initializes patient information
        String name;
        String dateOfBirth;
        String bloodType;
        String sex;

        // Obtains patient information to display on the interface
        try {
            Statement getPatientInfo = con.createStatement();
            ResultSet resultPatientInfo = getPatientInfo.executeQuery("SELECT PatientName, DateOfBirth, "
                + "BloodType, Sex, Address, PhoneNumber FROM Patient WHERE PatientIDNumber = '" + patientID + "'");
            resultPatientInfo.next();
            name = resultPatientInfo.getString("PatientName");
            dateOfBirth = resultPatientInfo.getString("DateOfBirth");
            bloodType = resultPatientInfo.getString("BloodType");
            sex = resultPatientInfo.getString("Sex");
            address = resultPatientInfo.getString("Address");
            phoneNumber = resultPatientInfo.getString("phoneNumber");
        }
        catch(SQLException ex) {
            JOptionPane.showMessageDialog(null, "Message: " + ex.getMessage());
            name = "";
            dateOfBirth = "";
            bloodType = "";
            sex = "";
            address = "";
            phoneNumber = "";
        }

        /* CREATES THE INTERFACE */
        JFrame mainFrame = new JFrame("Patient View");
        JPanel patientViewPanel = new JPanel();

        // Creates user info labels
        JLabel patientIDLabel = new JLabel("Patient " + patientID.substring(1,9) + ": " + name);
        JLabel dateOfBirthLabel = new JLabel("Date of Birth: " + dateOfBirth.substring(0,10));
        JLabel sexLabel = new JLabel("Sex: " + sex);
        JLabel bloodTypeLabel = new JLabel("Blood type: " + bloodType);
        addressLabel = new JLabel("Address: " + address);
        phoneNumberLabel = new JLabel("Phone Number: " + phoneNumber);

        // Creates information update objects
        JLabel updateInfoLabel = new JLabel("UPDATE INFORMATION");
        String[] updateCategories = { "Address", "Phone Number", "Password" };
        updateCategory = new JComboBox<>(updateCategories);
        updateInput = new JTextField(40);
        JButton submitUpdateButton = new JButton("Submit");

        // Creates query buttons
        JButton medicalRecordsButton = new JButton("Medical Records");
        JButton paymentHistoryButton = new JButton("Payment History");


        /* DETERMINES THE LAYOUT FOR THE INTERFACE */
        // Sets the size of the window
        mainFrame.setSize(700, 360);
        mainFrame.setLocation(370, 270);
        mainFrame.setContentPane(patientViewPanel);
        patientViewPanel.setLayout(null);

        // User info layout
        patientIDLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 34));
        patientIDLabel.setBounds(20, 20, 700, 40);
        dateOfBirthLabel.setBounds(30, 70, 200, 20);
        sexLabel.setBounds(330, 70, 100, 20);
        bloodTypeLabel.setBounds(580, 70, 100, 20);
        addressLabel.setBounds(30, 90, 300, 20);
        phoneNumberLabel.setBounds(330, 90, 250, 20);

        // Information update layout
        updateInfoLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        updateInfoLabel.setBounds(30, 150, 300, 20);
        updateCategory.setBounds(30, 145, 150, 80);
        updateCategory.addActionListener(this);
        updateCategory.setActionCommand("categoryChanged");
        updateInput.setBounds(200, 170, 300, 30);
        submitUpdateButton.setBounds(520, 175, 80, 20);
        submitUpdateButton.addActionListener(this);
        submitUpdateButton.setActionCommand("submitUpdate");

        // Query button layout
        medicalRecordsButton.setBounds(410, 300,140,20);
        medicalRecordsButton.addActionListener(this);
        medicalRecordsButton.setActionCommand("getRecords");
        paymentHistoryButton.setBounds(560,300,120,20);
        paymentHistoryButton.addActionListener(this);
        paymentHistoryButton.setActionCommand("getPaymentHistory");

        /* ADDS THE DIFFERENT OBJECTS TO THE INTERFACE */
        // Adds the user info labels to the interface
        patientViewPanel.add(patientIDLabel);
        patientViewPanel.add(dateOfBirthLabel);
        patientViewPanel.add(sexLabel);
        patientViewPanel.add(bloodTypeLabel);
        patientViewPanel.add(addressLabel);
        patientViewPanel.add(phoneNumberLabel);

        // Adds the information update objects to the interface
        patientViewPanel.add(updateInfoLabel);
        patientViewPanel.add(submitUpdateButton);
        patientViewPanel.add(updateCategory);
        patientViewPanel.add(updateInput);

        // Adds the query buttons to the interface
        patientViewPanel.add(medicalRecordsButton);
        patientViewPanel.add(paymentHistoryButton);


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
            case "categoryChanged":
                updateInput.setText("");
                break;
            case "submitUpdate":
                submitUpdate((String) updateCategory.getSelectedItem(), updateInput.getText());
                break;
            case "getRecords":
                getRecords();
                break;
            default:
                getPaymentHistory();
                break;
        }
    }

    private void submitUpdate(String category, String input) {
        if (input.length() == 0) { return; }

        if (category.equals("Phone Number")) {
            category = "PhoneNumber";

            // Checks to see if the phone number is in the correct format
            try {
                if (input.length() != 12) {
                    throw new NumberFormatException();
                }
                String first3 = input.substring(0, 3);
                String firstDash = input.substring(3, 4);
                String second3 = input.substring(4, 7);
                String secondDash = input.substring(7, 8);
                String last4 = input.substring(8, 12);


                if (!firstDash.equals("-") || !secondDash.equals("-")) {
                    throw new NumberFormatException();
                }
                Integer.parseInt(first3);
                Integer.parseInt(second3);
                Integer.parseInt(last4);
            }
            catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid phone number! Try ###-###-####.");
                return;
            }
        }
        else if (category.equals("Password")) { category = "PatientPassword"; }

        // Makes the update
        try {
            PreparedStatement inputSQLStatement = con.prepareStatement("UPDATE Patient SET " + category + " = '" +
                    input + "' WHERE PatientIDNumber = '" + patientID + "'");
            int rowCount = inputSQLStatement.executeUpdate();
            if (rowCount == 0) {
                JOptionPane.showMessageDialog(null, "Patient " + patientID +
                        " does not exist!");
            }
            con.commit();
            inputSQLStatement.close();
            if (category.equals("Address")) {
                address = input;
                addressLabel.setText("Address: " + input);
            }
            else if (category.equals("PhoneNumber")) {
                phoneNumber = input;
                phoneNumberLabel.setText("Phone Number: " + input);
            }
            updateCategory.setSelectedIndex(0);
            updateInput.setText("");
        }
        catch(SQLException ex) {
            JOptionPane.showMessageDialog(null, "Message: " + ex.getMessage());
        }
    }

    private void getRecords() {
        try {
            Statement inputSQLStatement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            ResultSet medicalRecordResults = inputSQLStatement.executeQuery("SELECT * FROM MedicalRecord " +
                    "WHERE PatientIDNumber = '" + patientID + "'");

            // Create and set up the window.
            JFrame frame = new JFrame(patientID + " Medical Records");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            // Create and set up the content pane
            ResultTable resultTable = new ResultTable(medicalRecordResults);
            resultTable.setOpaque(true); // content panes must be opaque
            frame.setContentPane(resultTable);

            // Display the window.
            frame.pack();
            frame.setVisible(true);

            // Close the statement
            inputSQLStatement.close();
        }
        catch(SQLException ex) {
            JOptionPane.showMessageDialog(null, "Message: " + ex.getMessage());
        }
    }

    private void getPaymentHistory() {
        try {
            Statement joinSQLStatement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            ResultSet paymentHistoryResults = joinSQLStatement.executeQuery("SELECT p.ReceiptNumber, " +
                    "p.PaymentAmount, s.ReasonForVisit, p.PaymentDate, p.RoomNumber, p.DateOfIntake, " +
                    "s.DateOfDeparture FROM ServiceBooking s, PaymentForService p " +
                    "WHERE s.PatientIDNumber = '" + patientID + "' " +
                    "AND s.PatientIDNumber = p.PatientIDNumber " +
                    "AND s.RoomNumber = p.RoomNumber " +
                    "AND s.DateOfIntake = p.DateOfIntake ");

            // Create the content pane and the join table
            JFrame frame = new JFrame(patientID + " Payment History");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            JPanel resultsDisplay = new JPanel();
            ResultTable resultTable = new ResultTable(paymentHistoryResults);
            String totalCost;
            JLabel totalCostLabel;

            // Close the join statement
            joinSQLStatement.close();

            // Get the total cost and add it to the label
            Statement aggregationSQLStatement = con.createStatement();
            ResultSet aggregationPaymentHistoryResults = aggregationSQLStatement.executeQuery("SELECT SUM(Cost)" +
                    "FROM ServiceBooking WHERE PatientIDNumber = '" + patientID + "'");
            if (aggregationPaymentHistoryResults.next()) {
                totalCost = aggregationPaymentHistoryResults.getString(1);
            }
            else { totalCost = "0"; }

            totalCostLabel = new JLabel("Total service costs: $" + totalCost);

            // Close the aggregation statement
            aggregationSQLStatement.close();

            // Set up the layout
            totalCostLabel.setBounds(20, 20, 300, 20);
            resultTable.setBounds(20, 50, resultTable.getWidth(), resultTable.getHeight());

            // Set up the content pane
            resultsDisplay.add(totalCostLabel);
            resultsDisplay.add(resultTable);
            frame.setContentPane(resultsDisplay);

            // Display the window.
            frame.pack();
            frame.setSize(resultTable.getWidth() + 20, resultTable.getHeight() + 50);
            frame.setVisible(true);
        }
        catch(SQLException ex) {
            JOptionPane.showMessageDialog(null, "Message: " + ex.getMessage());
        }
    }
}
