import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExpenseTracker extends JFrame {
    private JComboBox<String> categoryComboBox;
    private JTextField amountField;
    private JTextField dateField;
    private JTextArea descriptionArea;

    public ExpenseTracker() {
        setTitle("Expense Tracker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(5, 2, 5, 5));

        JLabel categoryLabel = new JLabel("Category:");
        categoryComboBox = new JComboBox<>(new String[]{"Food", "Home", "Clothes", "Other"});
        mainPanel.add(categoryLabel);
        mainPanel.add(categoryComboBox);

        JLabel amountLabel = new JLabel("Amount:");
        amountField = new JTextField(10);
        mainPanel.add(amountLabel);
        mainPanel.add(amountField);

        JLabel dateLabel = new JLabel("Date (dd/MM/yyyy):");
        dateField = new JTextField(10);
        mainPanel.add(dateLabel);
        mainPanel.add(dateField);

        JLabel descriptionLabel = new JLabel("Description:");
        descriptionArea = new JTextArea(4, 20);
        descriptionArea.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(descriptionArea);
        mainPanel.add(descriptionLabel);
        mainPanel.add(scrollPane);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new SubmitButtonListener());
        mainPanel.add(submitButton);

        add(mainPanel);
        setVisible(true);
    }

    private class SubmitButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String category = (String) categoryComboBox.getSelectedItem();
            String amountText = amountField.getText();
            String dateText = dateField.getText();
            String description = descriptionArea.getText();

            // Validate inputs (you can add more validation as needed)
            if (amountText.isEmpty() || dateText.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter amount and date.");
                return;
            }

            double amount = Double.parseDouble(amountText);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date;
            try {
                date = dateFormat.parse(dateText);
            } catch (java.text.ParseException ex) {
                JOptionPane.showMessageDialog(null, "Invalid date format. Please use dd/MM/yyyy.");
                return;
            }

            // Create an object of Expenses
            Expenses expense = new Expenses((int) amount, category, dateText, description);

            // Process the expense data (you can add your own logic here)
            // For now, just display the entered data
            String expenseInfo = "Category: " + expense.getCategory() + "\nAmount: $" + expense.getAmount() +
                    "\nDate: " + expense.getDate() + "\nDescription: " + expense.getDescription();
            JOptionPane.showMessageDialog(null, "Expense Recorded:\n" + expenseInfo);

            // Clear input fields after submission
            amountField.setText("");
            dateField.setText("");
            descriptionArea.setText("");
        }
    }
        public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ExpenseTracker());
    }
}