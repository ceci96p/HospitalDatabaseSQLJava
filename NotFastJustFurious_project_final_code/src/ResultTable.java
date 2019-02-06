// We need to import the java.sql package to use JDBC
import java.sql.*;

// To create the interface
import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;

class ResultTable extends JPanel {
    ResultTable(ResultSet results) {
        super(new GridLayout(1,0));

        try {
            ResultSetMetaData resultsMetaData = results.getMetaData();
            int numCols = resultsMetaData.getColumnCount();
            String[] columnNames = new String[numCols];

            for (int i = 0; i < numCols; i++) {
                // get column name and add it to columnNames
                columnNames[i] = resultsMetaData.getColumnName(i+1);
            }

            int rowCount = 0;

            while (results.next()) {
                rowCount++;
            }

            int decRowCount = rowCount;

            Object[][] data = new Object[rowCount][numCols];
            while (results.previous()) {
                decRowCount--;
                for (int j = 0; j < numCols; j++) {
                    data[decRowCount][j] = results.getString(j+1);
                }
            }

            final JTable table = new JTable(data, columnNames);

            // Gets the preferred width for each column
            TableCellRenderer headerRenderer = table.getTableHeader().getDefaultRenderer();
            int preferredWidth = 0;

            for (int i = 0; i < numCols; i++) {
                TableColumn column = table.getColumnModel().getColumn(i);

                Component comp = headerRenderer.getTableCellRendererComponent(
                        null, column.getHeaderValue(), false, false, 0, 0);
                int headerWidth = comp.getPreferredSize().width;

                TableCellRenderer cellRenderer = table.getCellRenderer(1, i);
                comp = cellRenderer.getTableCellRendererComponent(table, table.getValueAt(0, i),
                        false, false, 0, i);

                int cellWidth = comp.getPreferredSize().width;

                column.setPreferredWidth(Math.max(headerWidth, cellWidth+10));
                preferredWidth += column.getPreferredWidth();
            }

            // Sets the table width to the sum of the preferred column sizes
            table.setPreferredScrollableViewportSize(new Dimension(preferredWidth, rowCount*15 + 15));
            table.setFillsViewportHeight(true);

            // Create the scroll pane and add the table to it.
            JScrollPane scrollPane = new JScrollPane(table);

            // Add the scroll pane to this panel.
            add(scrollPane);
            results.close();
        }
        catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Message: " + ex.getMessage());
        }
    }
}
